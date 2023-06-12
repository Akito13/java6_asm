package sof3021.ca4.nhom1.asm.qls.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sof3021.ca4.nhom1.asm.qls.validation.AddressConstraint;
import sof3021.ca4.nhom1.asm.qls.validation.PasswordConstraint;
import sof3021.ca4.nhom1.asm.qls.validation.PhoneConstraint;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "`USER`")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User implements Serializable {
    @Id
    @Column(name = "MAKH")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKH;
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
//    @SequenceGenerator(name = "user_id_generator", sequenceName = "USER_makh_seq", allocationSize = 1)

    @Column(name = "TENKH")
    @NotNull(message = "Invalid username", groups = {BasicInfo.class})
    @NotEmpty(message = "Invalid username", groups = {BasicInfo.class})
    private String tenKH;

    @Column(name = "DIACHI")
    @AddressConstraint(groups = {BasicInfo.class})
    private String diaChi;

    @Column(name = "EMAIL")
    @NotNull(groups = LoginInfo.class, message = "Invalid email address")
//    @NotEmpty(groups = LoginInfo.class, message = "Invalid email address")
    @Email(groups = {LoginInfo.class}, message = "Invalid email address")
    private String email;

    @Column(name = "SDT")
    @PhoneConstraint(groups = {BasicInfo.class})
    private String sdt;

    @Column(name = "`ADMIN`")
    private boolean admin;

    @Column(name = "PASSWORD")
    @PasswordConstraint(groups = {LoginInfo.class})
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public interface LoginInfo {}
    public interface BasicInfo{}
}
