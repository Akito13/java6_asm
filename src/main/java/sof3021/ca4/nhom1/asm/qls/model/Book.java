package sof3021.ca4.nhom1.asm.qls.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SACH")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Book implements Serializable {
    @Id
    @Column(name = "MASACH")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maSach;

    @ManyToOne()
    @JoinColumn(name = "MALOAI")
    private Category loai;

    @Column(name = "TENSACH")
    private String tenSach;

    @Column(name = "TACGIA")
    private String tacGia;

    @Column(name = "NXB")
    @Temporal(TemporalType.DATE)
    private Date nxb;

    @Column(name = "GIA")
    private double gia;

    @Column(name = "IMG")
    private String img;

    @Column(name = "SOLUONG")
    private int soLuong;
    @Transient
    private int soLuongMua;
}
