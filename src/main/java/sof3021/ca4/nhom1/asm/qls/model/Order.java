package sof3021.ca4.nhom1.asm.qls.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "donhang")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Order implements Serializable {
    @Id
    @Column(name = "madh")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_generator")
    @SequenceGenerator(name = "order_id_generator", sequenceName = "donhang_madh_seq", allocationSize = 1)
    private Integer maDH;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "makh")
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "ngayxuat")
    private Date ngayXuat;

    @Column(name = "tennguoinhan")
    private String tenNguoiNhan;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "diachinhan")
    private String diaChiNhan;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<OrderDetails> orderDetails;
}
