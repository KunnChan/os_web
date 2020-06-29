package min.onlineshop.services.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import min.onlineshop.domains.Cart;
import min.onlineshop.domains.Product;
import min.onlineshop.domains.User;
import min.onlineshop.dtos.MyCommon;
import min.onlineshop.dtos.ProductDto;
import min.onlineshop.exceptions.NotFoundException;
import min.onlineshop.filter.QProduct;
import min.onlineshop.repository.CartRepository;
import min.onlineshop.repository.ProductRepository;
import min.onlineshop.repository.UserRepository;
import min.onlineshop.requests.ProductRequest;
import min.onlineshop.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static min.onlineshop.dtos.MyCommon.getCurrentUser;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Product> getProductInCart(ProductRequest productRequest) {
        PageRequest pageable = MyCommon.getPageable(productRequest.getPage());
        List<BooleanExpression> filters = getQuery(productRequest);

        if(filters.isEmpty()){
            return productRepository.findAll(pageable);
        }
        BooleanExpression filterExpression = filters.get(0);
        for (int i = 1; i <= filters.size() - 1; ++i) {
            filterExpression = filterExpression.and(filters.get(i));
        }
        return productRepository.findAll(filterExpression, pageable);

    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No product found for id "+ id));
    }

    @Transactional
    @Override
    public Cart addToCart(ProductDto productDto) {
        User user = getCurrentUser();
        Product product = this.getProduct(productDto.getProductId());
        Cart cart = new Cart();
        cart.setProductId(product.getId());
        cart.setQty(productDto.getQty());
        cart.setPrice(product.getPrice() * productDto.getQty());
        cart.setUserId(user.getId());
        return cartRepository.save(cart);
    }

    @Override
    public List<ProductDto> getProductInCart() {
        User user = getCurrentUser();
        List<Cart> carts = cartRepository.findByUserId(user.getId());
        List<Long> ids = new ArrayList<>();
        carts.stream().forEach(cart -> ids.add(cart.getProductId()));
        ArrayList<Product> products = Lists.newArrayList(productRepository.findAllById(ids));
        return toProductDto(products, carts);
    }

    @Transactional
    @Override
    public void deleteFromCart(Long productId) {
        User user = getCurrentUser();
        cartRepository.deleteAllByUserIdAndProductId(user.getId(), productId);
    }

    @Override
    public void updateQty(Long productId, Integer qty) {
        User user = getCurrentUser();
        List<Cart> carts = cartRepository.findAllByUserIdAndProductId(user.getId(), productId);
        carts.forEach(cart -> cart.setQty(qty));
        cartRepository.saveAll(carts);
    }

    private List<BooleanExpression> getQuery(ProductRequest request) {

        QProduct productEntity = QProduct.productEntity;
        List<BooleanExpression> filters = new ArrayList<>();
        Long id = request.getId();
        if(id != null && !"".equals(id)){
            filters.add(productEntity.id.eq(id));
        }
        if(null != request.getCategory()){
            filters.add(productEntity.category.eq(request.getCategory()));
        }

        if(MyCommon.isNotNull(request.getTitle())){
            filters.add(productEntity.title.containsIgnoreCase(request.getTitle()));
        }

        return filters;
    }

    private List<ProductDto> toProductDto(List<Product> products, List<Cart> carts){
        List<ProductDto> list = new ArrayList<>();
        ProductDto dto;
        for (Product product: products) {
            dto = new ProductDto();
            dto.setProductId(product.getId());
            dto.setId(product.getId());
            dto.setCategory(product.getCategory());
            dto.setDescription(product.getDescription());
            dto.setImageUrl(product.getImageUrl());
            dto.setTitle(product.getTitle());
            Optional<Cart> cart = carts.stream().filter(c -> c.getProductId().equals(product.getId())).findFirst();
            if(cart.isPresent()){
                int qty = cart.get().getQty();
                dto.setQty(qty);
                dto.setPrice(product.getPrice() * qty);
            }
            list.add(dto);
        }
        return list;
    }
}
