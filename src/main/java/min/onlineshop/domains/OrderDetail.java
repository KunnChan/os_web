package min.onlineshop.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
@EqualsAndHashCode(exclude = {"order"}, callSuper= false)
@Entity
public class OrderDetail extends Auditable<String> {

    private Long productId;
    private int qty;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}

