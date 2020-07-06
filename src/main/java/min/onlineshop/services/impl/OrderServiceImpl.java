package min.onlineshop.services.impl;

import com.google.common.collect.Lists;
import min.onlineshop.domains.*;
import min.onlineshop.dtos.MyCommon;
import min.onlineshop.dtos.ProductDto;
import min.onlineshop.repository.*;
import min.onlineshop.services.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BillingAddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, BillingAddressRepository addressRepository, PaymentRepository paymentRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.paymentRepository = paymentRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public void saveAddress(BillingAddress address) {

        address.setActive(Boolean.TRUE);
        User user = MyCommon.getCurrentUser();
        user.addAddress(address);
        addressRepository.save(address);
    }

    @Transactional
    @Override
    public void saveOrder(Payment payment) {
        List<BillingAddress> billingAddresses = addressRepository.findByActive(Boolean.TRUE);

        payment.setActive(Boolean.TRUE);
        User user = MyCommon.getCurrentUser();
        user.addPayment(payment);
        Payment savedPayment = paymentRepository.save(payment);

        Order order = new Order();

        // get first record for temporary
        // later will be updated
        order.setBillingAddress(billingAddresses != null ? billingAddresses.get(0) : null);

        order.setOrderState(OrderState.ORDER_CREATED);
        order.setPayment(savedPayment);
        user.addOrder(order);

        List<Cart> carts = cartRepository.findByUserId(user.getId());
        int qty = 0;
        Double orderTotal = 0D;
        Set<OrderDetail> orderDetails = new HashSet<>();
        OrderDetail orderDetail;
        for (Cart cart: carts) {
            orderDetail = new OrderDetail();
            orderDetail.setProductId(cart.getProductId());
            orderDetail.setQty(cart.getQty());
            orderDetail.setOrder(order);
            orderDetails.add(orderDetail);
            qty +=cart.getQty();
            orderTotal += cart.getPrice() * cart.getQty();
        }
        order.setQty(qty);
        order.setOrderTotal(orderTotal);
        order.setOrderDetails(orderDetails);

        orderRepository.save(order);
        cartRepository.deleteAll(carts);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElse(new Order());
    }

    @Override
    public List<ProductDto> getOrderDetail(Long orderId) {
        Order order = this.getOrder(orderId);
        List<Long> ids = new ArrayList<>();
        order.getOrderDetails().stream().forEach(cart -> ids.add(cart.getProductId()));
        ArrayList<Product> products = Lists.newArrayList(productRepository.findAllById(ids));
        return toProductDto(products, order.getOrderDetails());
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public void deleteOrderByOrder(Order order) {
        orderRepository.delete(order);
    }

    private List<ProductDto> toProductDto(List<Product> products, Set<OrderDetail> orderDetails){
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
            Optional<OrderDetail> orderDetail = orderDetails.stream().filter(c -> c.getProductId().equals(product.getId())).findFirst();
            if(orderDetail.isPresent()){
                int qty = orderDetail.get().getQty();
                dto.setQty(qty);
                dto.setPrice(product.getPrice() * qty);
            }
            list.add(dto);
        }
        return list;
    }
}
