인프런 > 최주호 > 스프링부트 JUnit 테스트 - 시큐리티를 활용한 Bank 애플리케이션

# Junit Bank App

### Jpa LocalDateTime 자동으로 생성하는 법

- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
```java
    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createAt;
    
    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;
```