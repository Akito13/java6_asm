package sof3021.ca4.nhom1.asm.qls.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "donhangchitiet")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDetails {
    @Id
    @Column(name = "madhct")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "od_id_generator")
    @SequenceGenerator(name = "od_id_generator", sequenceName = "donhangchitiet_madhct_seq", allocationSize = 1)
    private Integer maDhct;

    @ManyToOne
    @JoinColumn(name = "masach")
    private Book book;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "tongtien")
    private Double tongTien;

    @ManyToOne
    @JoinColumn(name = "madh")
    private Order order;
}
