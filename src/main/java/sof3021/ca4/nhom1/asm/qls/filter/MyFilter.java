package sof3021.ca4.nhom1.asm.qls.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import sof3021.ca4.nhom1.asm.qls.model.Cart;
import sof3021.ca4.nhom1.asm.qls.model.User;
import sof3021.ca4.nhom1.asm.qls.service.CartService;
import sof3021.ca4.nhom1.asm.qls.utils.Base64Encoder;
import sof3021.ca4.nhom1.asm.qls.utils.CookieService;
import sof3021.ca4.nhom1.asm.qls.utils.Page;
import sof3021.ca4.nhom1.asm.qls.utils.SessionService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@WebFilter("/*")
public class MyFilter extends HttpFilter {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CookieService cookieService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SessionService sessionService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CartService cartService;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        Cart cart = (Cart) req.getSession().getAttribute("cart");
//        cartFilter(req, res, cart, user);
        req = cartFilter(req, res, cart, user);
        req = userFilter(req, res, user);
        chain.doFilter(req, res);
    }

    protected HttpServletRequest userFilter(HttpServletRequest req, HttpServletResponse res, User user)
        throws IOException, ServletException
    {
        String url = req.getRequestURI();
        if(user == null) {
            if(url.contains("account/logout")
                    || url.contains("account/info"))
            {
                req = route(req, Page.HOME);
                System.out.println("Inside userFilter if()");
                System.out.println("Current request: " + req.getRequestURI());
            }
        } else {
            if(url.contains("account/signup"))
            {
                req = route(req, Page.HOME);
                System.out.println("Inside userFilter else()");
                System.out.println("Current request: " + req.getRequestURI());
            }
        }
        return req;
    }

    protected HttpServletRequest cartFilter(HttpServletRequest req, HttpServletResponse res, Cart cart, User user) {
        String url = req.getRequestURI();
        if(user==null) {
            if(url.contains("cart")) {
                req = route(req, Page.HOME);
                System.out.println("Yes");
                return req;
            }
        }
        if(user != null && cart == null) {
            Optional<Object> result = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("cart"))
                    .findAny()
                    .map(cookie -> Base64Encoder.fromString(cookie.getValue()));
//            Optional<Object> result = cookieService.getValue("cart", true)
//                    .map(Base64Encoder::fromString);
            if(result.isPresent()) {
                cart = (Cart) result.get();
            } else {
                cart = new Cart();
                cart.setOrders(new HashMap<>());
            }
            cart.setUser(user);
            System.out.println(cart.getUser().getTenKH());
            req.getSession().setAttribute("cart", cart);
        }
        if(cart != null && (!url.contains("cart/add") || !url.contains("cart/remove"))) {
            req.getSession().setAttribute("cart", cart);
            req.getSession().setAttribute("totalAmount", cartService.getAmount(cart));
            req.getSession().setAttribute("totalCount", cartService.getCount(cart));
        }
//        Optional<Object> result = cookieService.getValue("cart", true)
//                .map(Base64Encoder::fromString);
        return req;
    }

    private HttpServletRequest route(HttpServletRequest req, String to) {
        return new HttpServletRequestWrapper(req) {
            @Override
            public String getRequestURI() {
                return to;
            }
        };
    }
}
