package sof3021.ca4.nhom1.asm.qls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.RequestContextFilter;

@Configuration
public class BeanConfig {
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    public RequestContextFilter requestContextFilter(){
        return new RequestContextFilter();
    }
}
