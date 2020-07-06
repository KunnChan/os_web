package min.onlineshop.services.impl;

import min.onlineshop.domains.Order;
import min.onlineshop.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BillingAddressRepository addressRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void getOrderTest() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order foundOrder = orderService.getOrder(1l);
        verify(orderRepository).findById(1L);
        assertThat(foundOrder).isNotNull();
    }

    @Test
    void getAllOrderTest() {
        Order order = new Order();
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findAll()).thenReturn(orders);
        List<Order> listOfOrder = orderService.getAllOrder();
        verify(orderRepository).findAll();
        assertThat(listOfOrder).hasSize(1);
    }

    @Test
    void deleteOrderTest() {
        orderService.deleteOrder(1L);
        orderService.deleteOrder(1L);

        verify(orderRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteOrderTestAtLeastOnce() {
        orderService.deleteOrder(1L);
        orderService.deleteOrder(1L);

        verify(orderRepository, atLeastOnce()).deleteById(1L);
        verify(orderRepository, atMost(5)).deleteById(1L);
    }
    @Test
    void deleteOrderTestNever() {
        orderService.deleteOrder(1L);
        orderService.deleteOrder(1L);

        verify(orderRepository, never()).deleteById(2L);

    }

    @Test
    void deleteOrderTestAnyObject() {
        Order order = new Order();
        orderService.deleteOrderByOrder(order);

        verify(orderRepository).delete(any(Order.class));
    }
}