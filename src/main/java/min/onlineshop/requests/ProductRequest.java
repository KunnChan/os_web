package min.onlineshop.requests;

import lombok.Data;
import lombok.ToString;
import min.onlineshop.domains.ProductCategory;

@Data
@ToString
public class ProductRequest {

    private Long id;
    private ProductCategory category;
    private String title;
    private PageInfo page;
}
