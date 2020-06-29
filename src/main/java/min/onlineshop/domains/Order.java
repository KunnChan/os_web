package min.onlineshop.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@EntityListeners(CommonEntityListener.class)
@EqualsAndHashCode(exclude = {"user"}, callSuper= false)
@Table(name="order_tb")
public class Order extends Auditable<String> {

    private int qty;
    private Double orderTotal;

    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne
    private Payment payment;

    @OneToOne
    private BillingAddress billingAddress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @Override
    public String toString() {
        return "Order{" +
                "qty=" + qty +
                ", orderTotal=" + orderTotal +
                ", orderState=" + orderState +
                '}';
    }
}

