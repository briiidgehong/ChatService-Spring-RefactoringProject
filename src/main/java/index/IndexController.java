package index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController{

    @RequestMapping({"/index", "/"})
    public String indexforwarding() {
        System.out.println("main Page 메인 페이지");
        return "index";
    }

    @RequestMapping("/login")
    public String loginforwarding() {
        System.out.println("login 페이지");
        return "login";
    }

    @RequestMapping("/join")
    public String joinforwarding() {
        System.out.println("join 페이지");
        return "join";
    }

    @RequestMapping("/chat")
    public String chatforwarding() {
        System.out.println("chat 페이지");
        return "chat";
    }

    @RequestMapping("/find")
    public String findforwarding() {
        System.out.println("find 페이지");
        return "find";
    }

    @RequestMapping("/box")
    public String boxfunction() {
        System.out.println("box 페이지");
        return "box";
    }
}
