package min.onlineshop.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Data
@EqualsAndHashCode(exclude = {"cart"})
@Entity
@ToString(exclude = {"cart"})
public class Product extends Auditable<String> {

    @Enumerated(value = EnumType.STRING)
    private ProductCategory category;

    private String title;

    @Lob
    private String description;
    private Double price = 0D;
    private String imageUrl;

    @ManyToOne
    private Cart cart;

}

