package sof3021.ca4.nhom1.asm.qls.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CookieService {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    public void add(String name, String value, int hours) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge((60 * 60) * (hours >= 0 ? hours : 24 * 365 * 10));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public Optional<String> getValue(String name, boolean onlyValue) {
        Cookie[] cookies = request.getCookies();
        if(cookies==null) return Optional.empty();
        Stream<Cookie> cookieStream = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(name));
        return cookieStream.map(Cookie::getValue).findAny();
//                         : cookieStream.findAny();
//                .findAny();
//        for(var cookie: cookies)
//            if (cookie.getName().equalsIgnoreCase(name)){
//                return onlyValue ? cookie.getValue() : cookie;
//            }
//        return null;
    }

    public void remove(String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies==null) return;
        for(var cookie: cookies)
            if(cookie.getName().equalsIgnoreCase(name)) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
    }
}
