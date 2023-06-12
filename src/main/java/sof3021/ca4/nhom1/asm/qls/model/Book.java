package sof3021.ca4.nhom1.asm.qls.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SACH")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Book implements Serializable {
    @Id
    @Column(name = "MASACH")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @SequenceGenerator(name = "book_id_generator", sequenceName = "sach_masach_seq", allocationSize = 1)
    private Integer maSach;

    @ManyToOne()
    @JoinColumn(name = "MALOAI")
    @NotNull(message = "Category not selected")
    private Category loai;

    @Column(name = "TENSACH")
    @NotEmpty(message = "Name cannot be empty")
    private String tenSach;

    @Column(name = "TACGIA")
    @NotEmpty(message = "Author cannot be empty")
    private String tacGia;

    @Column(name = "NXB")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Date cannot be empty")
    private Date nxb;

    @Column(name = "GIA")
    @Min(message = "Price cannot be lower than 0", value = 0)
    @NotNull(message = "Price cannot be empty")
    private Double gia;

    @Column(name = "IMG")
    @NotEmpty(message = "Image cannot be empty")
    private String img;

    @Column(name = "SOLUONG")
    @Min(message = "Quantity cannot be negative", value = 0)
    @NotNull(message = "Quantity cannot be empty")
    private Integer soLuong;

    @OneToMany(mappedBy = "book")
    private List<OrderDetails> orderDetails;

    @Transient
    private int soLuongMua;
}
