package shop.mtcoding.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.mtcoding.bank.domain.user.UserEnum;

//@Slf4j // JUnit 테스트 할때 문제가 생겨서 어노테이션으로 사용하지 않음
@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean // IoC 컨테이너에 BCryptPasswrdEncoder() 객체가 등록됨 (@Configuration이 붙어 있는 클래스에서만 작동)
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록이 필요함

    // JWT 서버를 만들 예정!! Session 사용안함
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록됨");

        http.headers(hc -> hc.frameOptions(foc -> foc.disable())); // iframe 허용안함
        http.csrf(hscc -> hscc.disable()); // enable이면 post맨 작동안함 (메타코딩 유튜브 시큐리티 강의)
        http.cors(hscc -> hscc.configurationSource(configurationSource()));

        // jSessionId를 서버쪽에서 관리 안하겠다는 뜻!!
        http.sessionManagement(hssmc -> hssmc.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // react, 앱으로 요청할 예정 (서버에서 제공하는 웹페이지 로그인 방식을 안쓰겠다는 뜻)
        http.formLogin(hsflc -> hsflc.disable());

        // httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다
        http.httpBasic(hshbc -> hshbc.disable());

        http.authorizeHttpRequests(ahrc ->
                ahrc.requestMatchers(new AntPathRequestMatcher("/api/s/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole("" + UserEnum.ADMIN) // 최근 공식문서에서는 ROLE_ 안붙여도 됨
                        .anyRequest().permitAll()
        );

        return http.build();
    }

    private CorsConfigurationSource configurationSource() {
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");// GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트엔드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
