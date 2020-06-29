package min.onlineshop.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Data
@EntityListeners(CommonEntityListener.class)
@EqualsAndHashCode(exclude = {"user"}, callSuper= false)
@Entity
public class Payment extends Auditable<String> {

    private String cardNo;

    @Enumerated(value = EnumType.STRING)
    private CardType cardType;
    private Boolean active = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Override
    public String toString() {
        return "Payment{" +
                "cardNo='" + cardNo + '\'' +
                ", cardType=" + cardType +
                '}';
    }
}

