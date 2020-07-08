package min.onlineshop.controllers;

import min.onlineshop.domains.BillingAddress;
import min.onlineshop.domains.Order;
import min.onlineshop.dtos.ProductDto;
import min.onlineshop.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService orderService;

    @Mock
    Model model;

    @InjectMocks
    OrderController controller;

    List<Order> orderList = new ArrayList<>();
    List<ProductDto> products = new ArrayList<>();
    Order order;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        orderList.add(new Order());
        products.add(new ProductDto());
        order = new Order();
        order.setBillingAddress(new BillingAddress());

        lenient().when(orderService.getAllOrder()).thenReturn(orderList);
        lenient().when(orderService.getOrder(anyLong())).thenReturn(order);
        lenient().when(orderService.getOrderDetail(anyLong())).thenReturn(products);


        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver).build();
    }

    @Test
    void goOrderMvcTest() throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(view().name("order"))
                .andExpect(status().isOk());
    }

    @Test
    void saveAddressTest() throws Exception {
        mockMvc.perform(post("/address")
                .requestAttr("address", new BillingAddress())
        ).andExpect(status().is3xxRedirection());
    }

    @Test
    void goOrderTest() {
        //when
        String viewName = controller.goOrder(model);

        //then
        then(orderService).should().getAllOrder();
        then(model).should().addAttribute("orders", orderList);
        assertThat("order").isEqualToIgnoringCase(viewName);
    }

    @Test
    void goOrderDetailTest() {

        //when
        String viewName = controller.goOrderDetail(anyLong(), model);

        //then
        then(orderService).should().getOrderDetail(anyLong());
        then(model).should().addAttribute("order", order);
        then(model).should().addAttribute("products", products);

        assertThat("orderDetail").isEqualTo(viewName);

    }
}