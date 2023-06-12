package sof3021.ca4.nhom1.asm.qls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sof3021.ca4.nhom1.asm.qls.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.email = ?1")
    Optional<User> findByEmail(String email);
    Optional<User> findBySdt(String sdt);


}
