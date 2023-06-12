package sof3021.ca4.nhom1.asm.qls.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sof3021.ca4.nhom1.asm.qls.model.Book;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Override
    Page<Book> findAll(Pageable pageable);
    
    Page<Book> findAllByTenSachLike(String keyword, Pageable pageable);
    @Query("select b from Book b where b.loai.maLoai = (select s.loai.maLoai from Book s where s.maSach = ?1)")
    List<Book> findAllWithSameCategory(Integer maSach);
    
    default Map<Integer, Book> findAllWithSameCategoryMap(Integer maSach) {
        return findAllWithSameCategory(maSach).stream().collect(Collectors.toMap(Book::getMaSach, book -> book));
    }
}
