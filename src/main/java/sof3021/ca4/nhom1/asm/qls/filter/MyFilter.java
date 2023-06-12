package sof3021.ca4.nhom1.asm.qls.filter;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.GenericFilterBean;
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

@Component
@WebFilter("/*")
public class MyFilter extends GenericFilterBean {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CookieService cookieService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SessionService sessionService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CartService cartService;

    @PostConstruct
    public void init(){
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void mainFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        User tempUser = (User) req.getSession().getAttribute("tempUser");
        String uri = req.getRequestURI();
//        cartFilter(req, res, cart, user);
        req = cartFilter(req, res, cart, user, uri);
        req = userFilter(req, tempUser, user, uri);
        chain.doFilter(req, res);
    }

    protected HttpServletRequest userFilter(HttpServletRequest req, User tempUser,
                                            User user, String uri)
        throws IOException, ServletException
    {
        if(user == null) {
            if(uri.contains("account/logout")
                    || uri.contains("account/orders") )
            {
                req = route(req, Page.ACCOUNT_LOGIN);
            }
        } else {
            if(uri.contains("account/signup") && uri.contains("account/login"))
            {
                req = route(req, Page.ACCOUNT_LOGIN);
                System.out.println("Inside userFilter else()");
                System.out.println("Current request: " + req.getRequestURI());
            }
        }
        if(tempUser == null && (uri.contains("signup/confirm"))) {
            req = route(req, Page.ACCOUNT_LOGIN);
        }
        return req;
    }

    protected HttpServletRequest cartFilter(HttpServletRequest req, HttpServletResponse res,
                                            Cart cart, User user, String uri) {
        if(user==null) {
            if(uri.contains("cart")) {
                req = route(req, Page.ACCOUNT_LOGIN);
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
        try {
            if(cart != null && (!uri.contains("cart/add") || !uri.contains("cart/remove"))) {
                req.getSession().setAttribute("cart", cart);
                req.getSession().setAttribute("totalAmount", cartService.getAmount(cart));
                req.getSession().setAttribute("totalCount", cartService.getCount(cart));
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        mainFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }
}
