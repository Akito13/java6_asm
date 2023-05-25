package sof3021.ca4.nhom1.asm.qls.model;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Cart implements Serializable {
    private Map<Integer, Book> orders;
    private User user;
}
