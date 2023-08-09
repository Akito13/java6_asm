package sof3021.ca4.nhom1.asm.qls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

@SpringBootApplication
@ServletComponentScan
@EnableWebSecurity
public class QlsApplication {

	public static void main(String[] args) {
		SpringApplication.run(QlsApplication.class, args);
	}

}
