package board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController{

    @RequestMapping({"/index", "/"})
    public String tempfunction() {
        System.out.println("main Page");
        return "index";
    }
}
