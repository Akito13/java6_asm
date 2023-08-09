package sof3021.ca4.nhom1.asm.qls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sof3021.ca4.nhom1.asm.qls.filter.AdminInterceptor;
import sof3021.ca4.nhom1.asm.qls.filter.MyFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration
//        implements WebMvcConfigurer
{

    @Bean
    public PasswordEncoder passwordEncoder() {return NoOpPasswordEncoder.getInstance();}
//    @Autowired
//    private AdminInterceptor adminInterceptor;
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    public RequestContextFilter requestContextFilter(){
        return new RequestContextFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/cart/**").authenticated()
                        .requestMatchers("/books/**", "/", "/home", "account/**", "/api/**").permitAll()
                        .anyRequest().permitAll())
                .formLogin(form -> form.loginPage("/account/login"))
                .logout(logout -> logout.clearAuthentication(true)
                        .invalidateHttpSession(true))
//                .addFilterAfter(new MyFilter(), BasicAuthenticationFilter.class)
                ;

        return http.build();
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(adminInterceptor)
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/account/**");
//    }
}
