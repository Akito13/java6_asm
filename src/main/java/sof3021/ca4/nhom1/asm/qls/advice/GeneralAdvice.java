package sof3021.ca4.nhom1.asm.qls.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GeneralAdvice {
    @ModelAttribute
    public void addContext(Model model, HttpServletRequest req, HttpSession session) {
        model.addAttribute("request", req);
        model.addAttribute("session", session);
    }
}
