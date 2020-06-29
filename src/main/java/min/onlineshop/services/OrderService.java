package min.onlineshop.services;

import min.onlineshop.domains.BillingAddress;
import min.onlineshop.domains.Order;
import min.onlineshop.domains.Payment;
import min.onlineshop.dtos.ProductDto;

import java.util.List;

public interface OrderService {
    void saveAddress(BillingAddress address);

    void saveOrder(Payment payment);

    List<Order> getAllOrder();

    Order getOrder(Long orderId);

    List<ProductDto> getOrderDetail(Long orderId);
}
