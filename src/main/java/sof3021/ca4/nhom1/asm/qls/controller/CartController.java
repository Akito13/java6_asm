package sof3021.ca4.nhom1.asm.qls.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sof3021.ca4.nhom1.asm.qls.model.*;
import sof3021.ca4.nhom1.asm.qls.repository.BookRepository;
import sof3021.ca4.nhom1.asm.qls.repository.OrderDetailsRepository;
import sof3021.ca4.nhom1.asm.qls.repository.OrderRepository;
import sof3021.ca4.nhom1.asm.qls.service.CartService;
import sof3021.ca4.nhom1.asm.qls.service.MailService;
import sof3021.ca4.nhom1.asm.qls.utils.Base64Encoder;
import sof3021.ca4.nhom1.asm.qls.utils.CookieService;
import sof3021.ca4.nhom1.asm.qls.utils.SessionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private CartService cartService;
    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private MailService mailService;


    @GetMapping("/show")
    public String showCart(Model model, HttpServletRequest req){
        String error = (String) model.getAttribute("error");
        String cartId = "cart" + ((User) sessionService.getAttribute("user")).getMaKH();
        if(error != null)
            model.addAttribute("error", error);
        model.addAttribute("cartId", cartId);
        model.addAttribute("view", "pages/cart.jsp");
        return "index";
    }

    @GetMapping("/add/{id}")
    public String addToCart(
            @PathVariable int id,
            @RequestParam("quantity") Optional<Integer> quantity,
            RedirectAttributes params,
            HttpServletRequest req)
    {
        User user = sessionService.getAttribute("user");
        Cart cart = (Cart) req.getSession().getAttribute("cart"+user.getMaKH());
        Integer slm = quantity.orElse(1);
        try{
            if(cart != null)
            {
                cart = cartService.addProduct(id, slm, cart);
                String cartData = Base64Encoder.toString(cart);
                cookieService.add("cart"+user.getMaKH(), cartData, -1);
                System.out.println("Cart is not null");
//                sessionService.setAttribute("cart", cart);
            }
        } catch (Exception ex) {
            params.addAttribute("error", ex.getMessage());
        }
        req.getSession().setAttribute("cart"+user.getMaKH(), cart);
        req.getSession().setAttribute("totalAmount", cartService.getAmount(cart));
        req.getSession().setAttribute("totalCount", cartService.getCount(cart));

        return "redirect:"+req.getParameter("from");
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(
            @PathVariable int id,
            @RequestParam("quantity") int quantity,
            HttpServletRequest req,
            RedirectAttributes params) {
        User user = sessionService.getAttribute("user");
        Cart cart = (Cart) req.getSession().getAttribute("cart"+user.getMaKH());
        try {
            if(cart != null) {
                cart = cartService.removeProduct(id, cart, quantity);
                String cartData = Base64Encoder.toString(cart);
                cookieService.add("cart"+user.getMaKH(), cartData, -1);
            }
        } catch (Exception ex) {
            params.addAttribute("removeFromCartError", ex.getMessage());
            ex.printStackTrace();
        }
        req.getSession().setAttribute("cart"+user.getMaKH(), cart);
        req.getSession().setAttribute("totalAmount", cartService.getAmount(cart));
        req.getSession().setAttribute("totalCount", cartService.getCount(cart));
        return "redirect:/cart/show";
    }

    @GetMapping("/clear")
    public String removeAll(HttpServletRequest req){
        User user = sessionService.getAttribute("user");
        Cart cart = (Cart) req.getSession().getAttribute("cart"+user.getMaKH());
        if(cart != null) {
            cart = cartService.clear(cart);
        }
        sessionService.setAttribute("cart"+user.getMaKH(), cart);
        return "redirect:/cart/show";
    }

    @GetMapping("/checkout")
    public String checkout(RedirectAttributes params){
        User user = (User) sessionService.getAttribute("user");
        Cart cart = (Cart)sessionService.getAttribute("cart"+user.getMaKH());
        try {
            if(cart != null && user != null) {
                Order order = new Order();
                String error = null;
                order.setUser(user);
                order.setNgayXuat(new Date());
                List<OrderDetails> allOrders = order.getOrderDetails();
                if(allOrders == null) allOrders = new ArrayList<>();
                List<Integer> allRemaining = bookRepo.findAllRemaining(cart.getOrders().values());
                int i = 0;
                for(var item: cart.getOrders().entrySet()){
                    if(item.getValue().getSoLuongMua() > allRemaining.get(i)) {
                        error = item.getValue().getTenSach() + " only has " + allRemaining.get(i)
                                + " copies left";
                        break;
                    }
                    double totalAmount = item.getValue().getGia() * item.getValue().getSoLuongMua();
                    OrderDetails details = new OrderDetails();
                    Book book = item.getValue();
                    book.setSoLuong(allRemaining.get(i)-book.getSoLuongMua());
                    details.setBook(book);
                    bookRepo.save(book);
                    details.setOrder(order);
                    details.setSoLuong(book.getSoLuongMua());
                    details.setTongTien(totalAmount);
                    allOrders.add(details);
                    i++;
                }
                if(error == null) {
                    order.setOrderDetails(allOrders);
                    user.getOrders().add(order);
                    orderRepo.save(order);
                    sendMail(user, cart);
                    cartService.clear(cart);
                } else {
                    params.addFlashAttribute("error", error);
                    return "redirect:/cart/show";
                }
            }
            return "redirect:/account/orders";
        } catch (Exception e) {
            e.printStackTrace();
            params.addFlashAttribute("error", "Something went wrong...");
            return "redirect:/cart/show";
        }
    }

    private boolean sendMail(User user, Cart cart){
        StringBuilder builder = new StringBuilder();
        builder.append("<p>Dear customer, " + user.getTenKH() + "</p>");
        builder.append("<p>Thank you for purchasing books at our BookShop.</p>");
        builder.append("<p>Below is your order list:</p>");
        builder.append("<ul>");
        int i = 1;
        for(var order: cart.getOrders().entrySet()){
            builder.append("<li>");
            builder.append("<strong>");
            builder.append(order.getValue().getTenSach());
            builder.append("</strong>");
            builder.append("   x" + order.getValue().getSoLuongMua());
            builder.append("</li>");
        }
        builder.append("</ul>");
        builder.append("Total amount: $" + cartService.getAmount(cart));

        try {
            mailService.send(user.getEmail(), "Order Complete Notice", builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
