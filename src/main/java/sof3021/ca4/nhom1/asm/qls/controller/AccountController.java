package sof3021.ca4.nhom1.asm.qls.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sof3021.ca4.nhom1.asm.qls.model.User;
import sof3021.ca4.nhom1.asm.qls.repository.OrderDetailsRepository;
import sof3021.ca4.nhom1.asm.qls.repository.UserRepository;
import sof3021.ca4.nhom1.asm.qls.service.MailService;
import sof3021.ca4.nhom1.asm.qls.utils.Randomizer;
import sof3021.ca4.nhom1.asm.qls.utils.SessionService;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private OrderDetailsRepository odRepo;
    @Autowired
    private MailService mailer;
    @Autowired
    private HttpSession session;

    @InitBinder(value = {"loginUser", "signupUser"})
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, editor);
    }

    @GetMapping({"/login", "/signup"})
    public String showLogin(HttpServletRequest req, Model model) {
//        String url = req.getRequestURI();
        User user = (User) model.getAttribute("signupUser");
        Integer step = (Integer) model.getAttribute("step");
        String error = String.valueOf(model.getAttribute("signupError"));
        if(user == null) {
            user = new User();
        }
        if(step == null) step = 1;
        if(!error.equals("null")) model.addAttribute("signupError", error);
        model.addAttribute("signupUser", user);
        model.addAttribute("user", new User());
        model.addAttribute("active", req.getRequestURI().contains("login") ? "":"active");
        model.addAttribute("step", step);
        model.addAttribute("msg", model.getAttribute("msg"));
        return "pages/sign-in-up";
    }

    @PostMapping("/signup/1")
    public String signUpFirstStep(@Validated(User.LoginInfo.class) @ModelAttribute("signupUser") User user,
                                  BindingResult result,
                                  RedirectAttributes params,
                                  @RequestParam("confirmPassword")Optional<String> cfp) {
        userRepo.findByEmail(user.getEmail()).ifPresent(user1 -> result.rejectValue("email", "dup.user.email", "Email already existed"));
        cfp.ifPresentOrElse(
                s -> {
                    if(!s.equals(user.getPassword())) result.reject("user.confirm.password", "Password did not match");
                },
                () -> result.reject("user.confirm.password", "Cannot be empty"));
        if(result.hasErrors()) {
            params.addFlashAttribute("signupError", "Please fix invalid fields");
        } else {
            params.addFlashAttribute("step", 2);
        }
        params.addFlashAttribute("org.springframework.validation.BindingResult.signupUser", result);
        params.addFlashAttribute("signupUser", user);
        return "redirect:/account/signup";
    }

    @PostMapping("/signup/2")
    public String signUpSecondStep(@Validated({User.BasicInfo.class, User.LoginInfo.class}) @ModelAttribute("signupUser") User user,
                                  BindingResult result,
                                  RedirectAttributes params) {

        String goToPage = "/account/signup/confirm";
        userRepo.findBySdt(user.getSdt()).ifPresent(user1 -> result.rejectValue("sdt", "dup.user.sdt", "Phone number already existed"));
        if(result.hasErrors()) {
            params.addFlashAttribute("signupError", "Please fix invalid fields");
            params.addFlashAttribute("step", 2);
            goToPage = "/account/signup";
        } else {
            try {
                String randomCode = Randomizer.random();
                mailer.send(user.getEmail(),
                        "Signup email confirmation",
                        "Your confirmation code is: <strong>" + randomCode + "</strong>");
                sessionService.setAttribute("tempUser", user);
                sessionService.setAttribute("randomCode", randomCode);
            } catch (Exception exception) {
                exception.printStackTrace();
                params.addFlashAttribute("signupError", "Failed to send confirmation code.");
                params.addFlashAttribute("step", 2);
                goToPage = "/account/signup";
            }
        }
        params.addFlashAttribute("org.springframework.validation.BindingResult.signupUser", result);
        params.addFlashAttribute("signupUser", user);
        return "redirect:"+goToPage;
    }

    @GetMapping("/signup/confirm")
    public String getConfirm(Model model){
        User user = (User)model.getAttribute("signupUser");
        String error = (String) model.getAttribute("error");
        if(user != null) {
            model.addAttribute("signupUser", user);
        }
        if (error != null)
            model.addAttribute("error", error);
        session.setMaxInactiveInterval(60);
        return "pages/sign-up-confirm";
    }

    @PostMapping("/signup/confirm")
    public String confirmSignup(@RequestParam("code") Optional<String> enteredCode,
                                RedirectAttributes params) {
        User user = sessionService.getAttribute("tempUser");
        String code = sessionService.getAttribute("randomCode");
        if(user == null || code == null)
            return "redirect:/account/signup";
        if(enteredCode.isEmpty() || !enteredCode.get().equals(code))
        {
            params.addFlashAttribute("signupUser", user);
            params.addFlashAttribute("error", "Incorrect code");
            return "redirect:/account/signup/confirm";
        }
        userRepo.save(user);
        params.addFlashAttribute("msg", "Success!");
        session.invalidate();
        return "redirect:/account/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") Optional<String> email,
                        @RequestParam("password") Optional<String> password,
                        Model model){
        if(email.isEmpty() || password.isEmpty())
            return "redirect:/account/login";

        Optional<User> user = userRepo.findByEmail(email.get());
        if(user.isEmpty())
        {
            System.out.println("Empty user");
            return "redirect:/account/login";
        }
        if(!user.get().getPassword().equals(password.get())){
            System.out.println("Wrong password");
            return "redirect:/account/login";
        }
        sessionService.setAttribute("user", user.get());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        req.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        User user = (User) sessionService.getAttribute("user");
        model.addAttribute("view", "pages/account-orders.jsp");
        model.addAttribute("orders", odRepo.findAllByUserId(user.getMaKH()));
        return "index";
    }

    private void invalidateSession(long seconds){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(session != null) {
                    session.invalidate();
                }
                t.cancel();
            }
        }, seconds * 1000);
    }
}
