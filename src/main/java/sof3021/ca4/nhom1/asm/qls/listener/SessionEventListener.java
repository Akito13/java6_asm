package sof3021.ca4.nhom1.asm.qls.listener;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sof3021.ca4.nhom1.asm.qls.utils.CookieService;


@Component
public class SessionEventListener implements HttpSessionListener {

    @Autowired
    private CookieService cookieService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
//
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
//        User currentUser = (User)se.getSession().getAttribute("user");
//        if (currentUser == null) return;
//        Cart cart = (Cart) se.getSession().getAttribute("cart");
//        String cartData = Base64Encoder.toString(cart);
//        System.out.println("Cart data is: " + cartData);
//        System.out.println(cartData.length());
//
//        cookieService.add("cart", cartData, -1);
    }
}
