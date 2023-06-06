package sof3021.ca4.nhom1.asm.qls.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sof3021.ca4.nhom1.asm.qls.model.User;
import sof3021.ca4.nhom1.asm.qls.repository.UserRepository;
import sof3021.ca4.nhom1.asm.qls.utils.SessionService;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SessionService sessionService;

    @GetMapping({"/login", "/signup"})
    public String showLogin(HttpServletRequest req, Model model) {
//        User user = userRepo.findById(1).get();
//        sessionService.setAttribute("user", user);
//        return "redirect:/";
        String url = req.getRequestURI();
        model.addAttribute("user", new User());
        model.addAttribute("active", url.contains("login") ? "":"active");
        return "pages/sign-in-up";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        req.getSession().invalidate();
        return "redirect:/";
    }
}
