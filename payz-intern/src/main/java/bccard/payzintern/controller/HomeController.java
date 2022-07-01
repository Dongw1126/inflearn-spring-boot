package bccard.payzintern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {

        // 템플릿의 home.html로 연결
        return "home";
    }
}
