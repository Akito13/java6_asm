package sof3021.ca4.nhom1.asm.qls.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import sof3021.ca4.nhom1.asm.qls.model.User;
import sof3021.ca4.nhom1.asm.qls.utils.SessionService;
@Service
public class AdminInterceptor
//        implements HandlerInterceptor
{

//    @Autowired
//    private SessionService sessionService;
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        User user = sessionService.getAttribute("user");
//        String uri = request.getRequestURI();
//
//        if(user == null || (uri.contains("admin") && !user.isAdmin())){
//            System.out.println("GDIIIII");
//            response.sendRedirect(request.getServletContext().getContextPath() + "/account/login");
//            return false;
//        }
//        return true;
//    }
}
