package min.onlineshop.services;

import min.onlineshop.domains.Cart;
import min.onlineshop.domains.Product;
import min.onlineshop.dtos.ProductDto;
import min.onlineshop.requests.ProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<Product> getProductInCart(ProductRequest productRequest);

    Product getProduct(Long id);

    Cart addToCart(ProductDto product);

    List<ProductDto> getProductInCart();

    void deleteFromCart(Long productId);

    void updateQty(Long productId, Integer qty);
}
