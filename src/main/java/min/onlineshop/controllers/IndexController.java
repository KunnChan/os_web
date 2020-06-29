package min.onlineshop.controllers;

import min.onlineshop.domains.BillingAddress;
import min.onlineshop.domains.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping({"/login" })
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String goHome() {
        return "home";
    }

    @GetMapping("/checkout")
    public String goCheckout(Model model) {
        model.addAttribute("address", new BillingAddress());
        return "checkout";
    }

    @GetMapping("/billing")
    public String goBilling(Model model) {
        model.addAttribute("payment", new Payment());
        return "billing";
    }

}
