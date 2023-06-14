package sof3021.ca4.nhom1.asm.qls.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    @InitBinder(value = {"loginUser", "signupUser", "user"})
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, editor);
    }

    @GetMapping({"/login", "/signup"})
    public String showLogin(HttpServletRequest req, Model model) {
//        String url = req.getRequestURI();
        User user = (User) model.getAttribute("signupUser");
        User loginUser = (User) model.getAttribute("user");
        Integer step = (Integer) model.getAttribute("step");
        String error = String.valueOf(model.getAttribute("signupError"));
        if(user == null) {
            user = new User();
        }
        if(loginUser == null) {
            loginUser = new User();
        }
        if(step == null) step = 1;
        if(!error.equals("null")) model.addAttribute("signupError", error);
        model.addAttribute("signupUser", user);
        model.addAttribute("user", loginUser);
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
    public String login(@Validated(User.LoginInfo.class) @ModelAttribute("user") User user,
                        BindingResult result,
                        RedirectAttributes params){
        if(result.hasErrors()) {
            params.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            params.addFlashAttribute("user", user);
            return "redirect:/account/login";
        }
        Optional<User> resultUser = userRepo.findByEmail(user.getEmail());
        resultUser.ifPresentOrElse(user1 -> {
            if(user1.getPassword().equals(user.getPassword())) {
                sessionService.setAttribute("user", user1);
            } else {
                result.rejectValue("password", "user.password.invalid", "Incorrect password");
            }
        }, () -> result.rejectValue("email", "user.email.notfound", "Email not existed"));
        if(result.hasErrors()) {
            params.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            params.addFlashAttribute("user", user);
            return "redirect:/account/login";
        }

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

    @GetMapping("/forgot")
    public String showForgotForm(Model model){
        String to = (String) model.getAttribute("to");
        String error = (String) model.getAttribute("error");
        if(to == null) {
            to = "forgot";
        } else {
            User user = (User) model.getAttribute("forgotUser");
            if(user == null) user = new User();
            User forgotUser = sessionService.getAttribute("forgotUser");
            user.setEmail(forgotUser.getEmail());
            model.addAttribute("forgotUser", user);
        }
        if(error != null)
            model.addAttribute("error", error);
        model.addAttribute("to", to);
        return "pages/forgot-pass";
    }

    @PostMapping("/forgot")
    public String processForgot(@RequestParam("email") Optional<String> email,
                                RedirectAttributes params)
    {
        if(email.isEmpty() || email.get().isEmpty()) {
            params.addFlashAttribute("error", "Please enter a valid email");
            params.addFlashAttribute("to", null);
            return "redirect:/account/forgot";
        }
        Optional<User> user = userRepo.findByEmail(email.get());
        String code = Randomizer.random();
        if(user.isPresent()) {
            try {
                mailer.send(user.get().getEmail(),
                        "Email confirmation",
                        "Here is your code to regain your password: " + code);
                sessionService.setAttribute("code", code);
                sessionService.setAttribute("forgotUser", user.get());
                params.addFlashAttribute("to", "forgot/confirm");
//                params.addFlashAttribute("forgotUser", user.get());
            } catch (Exception e) {
                params.addFlashAttribute("to", null);
                params.addFlashAttribute("error", "Something went wrong...");
            }
        } else {
            params.addFlashAttribute("to", null);
            params.addFlashAttribute("error", "Email not existed");
        }
        return "redirect:/account/forgot";
    }

    @PostMapping("/forgot/confirm")
    public String confirmForgot(@Validated({User.LoginInfo.class})
                                    @ModelAttribute("forgotUser") User user,
                                BindingResult result,
                                @RequestParam("confirmPassword") Optional<String> cf,
                                @RequestParam("code") Optional<String> cfc,
                                RedirectAttributes params)
    {
        cf.ifPresentOrElse(
            s -> {
                if(!s.equals(user.getPassword())) result.reject("user.confirm.password", "Password did not match");
            },
            () -> result.reject("user.confirm.password", "Cannot be empty"));
        cfc.ifPresentOrElse(s -> {
            String randomCode = sessionService.getAttribute("code");
            if(!s.equals(randomCode)) {
                result.reject("user.confirm.code", "Incorrect code");
            }
        }, () -> result.reject("user.confirm.password", "Cannot be empty"));
        if(result.hasErrors()) {
            params.addFlashAttribute("org.springframework.validation.BindingResult.forgotUser", result);
            params.addFlashAttribute("forgotUser", user);
            params.addFlashAttribute("to", "forgot/confirm");
            return "redirect:/account/forgot";
        }
        User forgotUser = sessionService.getAttribute("forgotUser");
        forgotUser.setPassword(user.getPassword());
        userRepo.save(forgotUser);
        params.addFlashAttribute("msg", "Changed password successfully!");
        session.invalidate();
        return "redirect:/account/login";
    }
}
