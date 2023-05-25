package sof3021.ca4.nhom1.asm.qls.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sof3021.ca4.nhom1.asm.qls.model.Book;
import sof3021.ca4.nhom1.asm.qls.model.Cart;
import sof3021.ca4.nhom1.asm.qls.repository.BookRepository;
import sof3021.ca4.nhom1.asm.qls.service.CartService;
import sof3021.ca4.nhom1.asm.qls.utils.Base64Encoder;
import sof3021.ca4.nhom1.asm.qls.utils.CookieService;
import sof3021.ca4.nhom1.asm.qls.utils.SessionService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private CartService cartService;
    @Autowired
    BookRepository bookRepo;
    @Autowired
    CookieService cookieService;


    @GetMapping("/show")
    public String showCart(ModelMap model, HttpServletRequest req){
        model.addAttribute("view", "pages/cart.jsp");
        return "index";
    }

    @GetMapping("/add/{id}")
    public String addToCart(
            @PathVariable int id,
            @RequestParam("quantity") int quantity,
            RedirectAttributes params,
            HttpServletRequest req)
    {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        try{
            if(cart != null)
            {
                cart = cartService.addProduct(id, quantity, cart);
                String cartData = Base64Encoder.toString(cart);
                cookieService.add("cart", cartData, -1);
                System.out.println("Cart is not null");
//                sessionService.setAttribute("cart", cart);
            }
        } catch (Exception ex) {
            params.addAttribute("error", ex.getMessage());
            ex.printStackTrace();
        }
//        for(Map.Entry<Integer, Book> entry: cart.getOrders().entrySet()){
//            System.out.println("The book id is: " + entry.getKey() + " with name: " + entry.getValue().getTenSach());
//        }
        req.getSession().setAttribute("cart", cart);
        req.getSession().setAttribute("totalAmount", cartService.getAmount(cart));
        req.getSession().setAttribute("totalCount", cartService.getCount(cart));
        return "redirect:/home";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(
            @PathVariable int id,
            @RequestParam("quantity") int quantity,
            HttpServletRequest req,
            RedirectAttributes params) {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        try {
            if(cart != null) {
                cart = cartService.removeProduct(id, cart, quantity);
                String cartData = Base64Encoder.toString(cart);
                cookieService.add("cart", cartData, -1);
            }
        } catch (Exception ex) {
            params.addAttribute("removeFromCartError", ex.getMessage());
            ex.printStackTrace();
        }
        req.getSession().setAttribute("cart", cart);
        req.getSession().setAttribute("totalAmount", cartService.getAmount(cart));
        req.getSession().setAttribute("totalCount", cartService.getCount(cart));
        return "redirect:/cart/show";
    }
}
