package bccard.payzintern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Controller 로 쓰려면 어노테이션 붙여줘야함
@Controller
public class HelloController {

    // GetMapping 어노테이션 젹용
    // "/hello" 로 들어오면 아래 hello 메소드를 호출해줌
    @GetMapping("hello")
    public String hello(Model model) {
        // 모델에 attribute를 넣어줘서 View에 key-value 쌍으로 데이터 전달
        model.addAttribute("data", "hello!!");
        return "hello";
    }
}
