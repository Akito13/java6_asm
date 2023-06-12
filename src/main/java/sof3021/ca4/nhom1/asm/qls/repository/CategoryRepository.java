package sof3021.ca4.nhom1.asm.qls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sof3021.ca4.nhom1.asm.qls.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
