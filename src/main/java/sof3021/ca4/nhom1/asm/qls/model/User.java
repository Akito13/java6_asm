package sof3021.ca4.nhom1.asm.qls.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "`USER`")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User implements Serializable {
    @Id
    @Column(name = "MAKH")
    private int maKH;

    @Column(name = "TENKH")
    private String tenKH;

    @Column(name = "DIACHI")
    private String diaChi;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "`ADMIN`")
    private boolean isAdmin;

    @Column(name = "PASSWORD")
    private String password;
}
