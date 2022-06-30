package bccard.payzintern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("hello-mvc")
    // @RequestParam 을 이용해서 url 파라미터 받아옴
    // @RequestParam("name") 만 넣고 hello-mvc로 들어가면 에러 -> 파라미터가 없음
    public String helloMvc(@RequestParam(value="name") String name, Model model) {
        model.addAttribute("name", name);

        return "hello-template";
    }

    @GetMapping("hello-string")
    // @ResponseBody -> http 바디에 return의 내용을 넣어주겠다는 의미
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
