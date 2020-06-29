package min.onlineshop.controllers;

import lombok.extern.slf4j.Slf4j;
import min.onlineshop.domains.BillingAddress;
import min.onlineshop.domains.Order;
import min.onlineshop.domains.Payment;
import min.onlineshop.dtos.ProductDto;
import min.onlineshop.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String goOrder(Model model) {
        List<Order> orderList = orderService.getAllOrder();
        model.addAttribute("orders", orderList);
        return "order";
    }

    @GetMapping("/order-detail/{orderId}")
    public String goOrderDetail(@PathVariable("orderId") Long orderId, Model model) {

        log.info("order Id => {} ", orderId);
        Order order = orderService.getOrder(orderId);
        List<ProductDto> products = orderService.getOrderDetail(orderId);
        model.addAttribute("order", order);
        model.addAttribute("products", products);
        model.addAttribute("address", order.getBillingAddress().toString());
        return "orderDetail";
    }


    @PostMapping("/address")
    public String saveAddress(@ModelAttribute("address") BillingAddress address){

        orderService.saveAddress(address);
        return "redirect:/billing";
    }

    @PostMapping("/order/save")
    public String savePayment(@ModelAttribute("payment") Payment payment){
        log.info("save order param => {} ", payment);
        orderService.saveOrder(payment);
        return "redirect:/order";
    }
}
