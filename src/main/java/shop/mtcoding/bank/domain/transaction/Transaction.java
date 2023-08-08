package shop.mtcoding.bank.domain.transaction;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.mtcoding.bank.domain.account.Account;

import java.time.LocalDateTime;

@NoArgsConstructor // 스프링이 User 객체 생성할 때 빈 생성자로 new를 하기 때문!!
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction_tb")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account withdrawAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account depositAccount;

    private Long amount;

    private Long withdrawAccountBalance; // 1111 계좌 -> 1000원 -> 500원 -> 200원

    private Long depositAccountBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionEnum gobun; // WITHDRAW, DEPOSIT, TRANSFER, ALL

    // 계좌가 사라져도 로그는 남아야 한다.
    private String sender;

    private String receiver;

    private String tel;

    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Transaction(
            Long id,
            Account withdrawAccount,
            Account depositAccount,
            Long amount,
            Long withdrawAccountBalance,
            Long depositAccountBalance,
            TransactionEnum gobun,
            String sender,
            String receiver,
            String tel,
            LocalDateTime createAt,
            LocalDateTime updateAt
    ) {
        this.id = id;
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.gobun = gobun;
        this.sender = sender;
        this.receiver = receiver;
        this.tel = tel;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
