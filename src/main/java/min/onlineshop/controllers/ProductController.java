package min.onlineshop.controllers;

import lombok.extern.slf4j.Slf4j;
import min.onlineshop.domains.Cart;
import min.onlineshop.domains.Product;
import min.onlineshop.dtos.ProductDto;
import min.onlineshop.requests.ProductRequest;
import min.onlineshop.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/product/{id}")
    public String goProductDetail(@PathVariable Long id, Model model) {
        System.out.println("Id "+ id);
        model.addAttribute("product", service.getProduct(id));
        return "product";
    }

    @GetMapping("/cart")
    public String goCart(Model model) {
        List<ProductDto> products = service.getProductInCart();
        model.addAttribute("products", products);
        Double totalPrice = products.stream().mapToDouble(o -> o.getPrice()).sum();
        Integer totalQty = products.stream().mapToInt(o -> o.getQty()).sum();
        model.addAttribute("subTotal", totalPrice);
        model.addAttribute("count", totalQty);
        return "cart";
    }

    @DeleteMapping("/cart/delete/{productId}")
    public @ResponseBody String deleteCart(@PathVariable("productId") Long productId) {
        service.deleteFromCart(productId);
        return "success";
    }

    @PutMapping("/cart/update/{productId}/qty/{qty}")
    public @ResponseBody String updateQty(@PathVariable("productId") Long productId,
                                       @PathVariable("qty") Integer qty) {
        service.updateQty(productId, qty);
        return "success";
    }

    @PostMapping("/product/filter")
    public @ResponseBody Page<Product> getProducts(@RequestBody ProductRequest productRequest){
        log.info("getProducts params => {}", productRequest);
        return service.getProductInCart(productRequest);
    }

    @PostMapping("/product/add-to-cart")
    public @ResponseBody
    Cart addToCart(@RequestBody ProductDto product){
        log.info("addToCart params => {}", product);
        return service.addToCart(product);
    }
}
