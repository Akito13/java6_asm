package sof3021.ca4.nhom1.asm.qls.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sof3021.ca4.nhom1.asm.qls.model.*;
import sof3021.ca4.nhom1.asm.qls.repository.BookRepository;
import sof3021.ca4.nhom1.asm.qls.repository.OrderRepository;
import sof3021.ca4.nhom1.asm.qls.repository.UserRepository;
import sof3021.ca4.nhom1.asm.qls.service.CartService;
import sof3021.ca4.nhom1.asm.qls.service.MailService;
import sof3021.ca4.nhom1.asm.qls.utils.Base64Encoder;
import sof3021.ca4.nhom1.asm.qls.utils.CookieService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {
    @Autowired
    HttpSession session;
    @Autowired
    private CartService cartService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private MailService mailService;

    @GetMapping("/user")
    public Integer getUserId(){
        User user = (User) session.getAttribute("user");
        if(user == null) return -1;
        return user.getMaKH();
    }
    @GetMapping(path = {"", "/"})
    public Cart getCart(){
        User user = (User) session.getAttribute("user");
        if(user != null) {
            Cart cart = (Cart) session.getAttribute("cart"+user.getMaKH());
            for(Map.Entry<Integer, Book> order: cart.getOrders().entrySet()){
                System.out.println(order.getKey() + " " + order.getValue().getTenSach() + " is in " + order.getValue().getLoai().getTenLoai() + " category");
            }
//            System.out.println("INSIDE GETCART(): " + cart);
//            Hibernate.initialize(cart.getOrders());
            return cart;
        }
        return null;
    }
    @GetMapping("/remove/{id}")
    public Cart removeFromCart(@PathVariable int id,
                                 @RequestParam("quantity") int quantity,
                                 HttpServletRequest req,
                                 RedirectAttributes params){
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) req.getSession().getAttribute("cart"+user.getMaKH());
        try {
            if(cart != null) {
                cart = cartService.removeProduct(id, cart, quantity);
                int totalCount = cartService.getCount(cart);
                if(totalCount == 0) {
                    session.setAttribute("cart"+user.getMaKH(), null);
                    cookieService.remove("cart"+user.getMaKH());
                } else {
                    String cartData = Base64Encoder.toString(cart);
                    cookieService.add("cart"+user.getMaKH(), cartData, -1);
                }
            }
        } catch (Exception ex) {
            params.addAttribute("removeFromCartError", ex.getMessage());
            ex.printStackTrace();
        }
        req.getSession().setAttribute("cart"+user.getMaKH(), cart);
        req.getSession().setAttribute("totalAmount", cartService.getAmount(cart));
        req.getSession().setAttribute("totalCount", cartService.getCount(cart));
        return cart;
    }

    @GetMapping("/clear")
    public Cart removeAll(HttpServletRequest req){
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) req.getSession().getAttribute("cart"+user.getMaKH());
        if(cart != null) {
            cart = cartService.clear(cart);
        }
        session.setAttribute("cart"+user.getMaKH(), null);
        cookieService.remove("cart"+user.getMaKH());
        return cart;
    }

    @GetMapping("/checkout")
    public Cart checkout(RedirectAttributes params){
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart)session.getAttribute("cart"+user.getMaKH());
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
                    session.setAttribute("cart"+user.getMaKH(), null);
                    cookieService.remove("cart"+user.getMaKH());
                } else {
                    params.addFlashAttribute("error", error);
                    return cart;
                }
            }
            return cart;
        } catch (Exception e) {
            e.printStackTrace();
            params.addFlashAttribute("error", "Something went wrong...");
            return cart;
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
