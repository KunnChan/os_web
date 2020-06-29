package min.onlineshop.dtos;

import lombok.Data;
import lombok.ToString;
import min.onlineshop.domains.ProductCategory;

@Data
@ToString
public class ProductDto {

    private Long productId;
    private int qty;
    private Long id;
    private ProductCategory category;
    private String title;
    private String description;
    private Double price = 0D;
    private String imageUrl;
}
