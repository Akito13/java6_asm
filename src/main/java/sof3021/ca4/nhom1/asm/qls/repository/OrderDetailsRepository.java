package sof3021.ca4.nhom1.asm.qls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sof3021.ca4.nhom1.asm.qls.model.OrderDetails;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    @Query("SELECT od FROM OrderDetails od WHERE od.order.maDH IN " +
            "(SELECT o.maDH FROM Order o WHERE o.user.maKH = ?1)")
    List<OrderDetails> findAllByUserId(Integer userId);
}
