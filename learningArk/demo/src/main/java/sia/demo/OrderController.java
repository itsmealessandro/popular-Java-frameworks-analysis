package sia.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/current")
    public String getOrder(Model model) {

        model.addAttribute("order", new Order());

        return "order";
    }

    @PostMapping("/current")
    public String postMethodName(@Valid Order order, Errors errors) {
        if (errors.hasErrors()) {
            return "order";
        }
        return "redirect: /";
    }

}
