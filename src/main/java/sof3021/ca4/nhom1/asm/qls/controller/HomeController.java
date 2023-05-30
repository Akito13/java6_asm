package sof3021.ca4.nhom1.asm.qls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sof3021.ca4.nhom1.asm.qls.repository.BookRepository;
import sof3021.ca4.nhom1.asm.qls.service.CartService;
import sof3021.ca4.nhom1.asm.qls.utils.CookieService;

@Controller
public class HomeController {
    @Autowired
    BookRepository bookRepo;
    @Autowired
    CartService cartService;
    @Autowired
    CookieService cookieService;

    @GetMapping(path = {"/", "/home"})
    public String home(Model model){
        model.addAttribute("books", bookRepo.findAll());
//        cookieService.
        model.addAttribute("view", "pages/main.jsp");
        return "index";
    }

//    @GetMapping("/home")
//    public String toHome() {
//        return "forward:/" + req.getServletContext().getContextPath();
//    }
}
