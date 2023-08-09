package sof3021.ca4.nhom1.asm.qls.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "donhangchitiet")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDetails implements Serializable {
    @Id
    @Column(name = "madhct")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "od_id_generator")
    @SequenceGenerator(name = "od_id_generator", sequenceName = "donhangchitiet_madhct_seq", allocationSize = 1)
    private Integer maDhct;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "masach")
    private Book book;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "tongtien")
    private Double tongTien;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "madh")
    private Order order;
}
