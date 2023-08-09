package sof3021.ca4.nhom1.asm.qls.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sof3021.ca4.nhom1.asm.qls.repository.BookRepository;
import sof3021.ca4.nhom1.asm.qls.service.CartService;
import sof3021.ca4.nhom1.asm.qls.utils.CookieService;

@Controller
@EnableWebSecurity
public class HomeController {
    @Autowired
    BookRepository bookRepo;
    @Autowired
    CartService cartService;
    @Autowired
    CookieService cookieService;
    @Autowired
    HttpServletRequest req;
    @GetMapping(path = {"/", "/home"})
    public String home(Model model, Authentication auth){
        model.addAttribute("books", bookRepo.findAll());
        System.out.println("INSIDE HOMECONTROLLER, AUTH IS: " + auth);
//        model.addAttribute("view", "pages/main.jsp");
        model.addAttribute("from", "/");
        return "pages/main";
    }

//    @GetMapping("/home")
//    public String toHome() {
//        return "forward:/" + req.getServletContext().getContextPath();
//    }
}
