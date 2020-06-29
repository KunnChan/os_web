package min.onlineshop.domains;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;


@Data
@EntityListeners(CommonEntityListener.class)
@Entity
public class Cart extends Auditable<String> {

    private int qty;
    private Double price;
    private Long userId;
    private Long productId;

}

