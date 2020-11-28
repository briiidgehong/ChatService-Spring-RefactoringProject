package index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController{

    @RequestMapping({"/index", "/"})
    public String indexfunction() {
        System.out.println("main Page 메인 페이지");
        return "index";
    }

    @RequestMapping("/login")
    public String loginfunction() {
        System.out.println("login 페이지");
        return "login";
    }

    @RequestMapping("/chat")
    public String chatfunction() {
        System.out.println("chat 페이지");
        return "chat";
    }
}
