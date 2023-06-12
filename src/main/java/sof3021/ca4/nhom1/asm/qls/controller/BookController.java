package sof3021.ca4.nhom1.asm.qls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sof3021.ca4.nhom1.asm.qls.model.Book;
import sof3021.ca4.nhom1.asm.qls.repository.BookRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    @GetMapping(path = {"", "/"})
    public String getBooks(Model model,
                           @RequestParam("sort") Optional<String> sortBy,
                           @RequestParam("order") Optional<String> orderBy,
                           @RequestParam("page") Optional<Integer> currentPage,
                           @RequestParam("bookName") Optional<String> bookName){
        String sb = sortBy.orElse("n");
        String ob = orderBy.orElse("h");
        String kw = bookName.orElse("");
        Sort sort = null;
        sort = createSort(sort, ob, sb);
        Pageable pageable = PageRequest.of(currentPage.orElse(0), 6, sort);
        Page<Book> page = bookRepo.findAllByTenSachLike("%"+kw+"%", pageable);
        setAttributes(model,
                new String[] {"view", "order", "sort", "keywords"},
                new String[] {"pages/show-room.jsp", ob, sb, kw});
        model.addAttribute("books", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("from", "/books");
        return "index";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable Optional<Integer> id, Model model) {
        if(id.isPresent()) {
            List<Book> books = bookRepo.findAllWithSameCategory(id.get());
            Book foundBook = null;
            for(int i = 0; i < books.size(); i++) {
                if(books.get(i).getMaSach() == id.get()) {
                    foundBook = books.remove(i);
                }
            }
            if(!books.isEmpty()) {
                model.addAttribute("book", foundBook);
                model.addAttribute("books", books);
                model.addAttribute("from", "/books/"+id.get());
            }
            else model.addAttribute("books", null);
        }
        setAttributes(model, new String[] {"view"}, new String[] {"pages/book-details.jsp"});
        return "index";
    }

    private void setAttributes(Model model, String[] names, String[] values) {
        for(int i = 0; i < names.length; i++){
            model.addAttribute(names[i], values[i]);
        }
    }

    private Sort createSort(Sort sort, String orderBy, String sortBy){
        switch (sortBy) {
            case "n" -> sort = Sort.by(orderBy.equals("h") ? Sort.Direction.DESC : Sort.Direction.ASC, "tenSach");
            case "p" -> sort = Sort.by(orderBy.equals("h") ? Sort.Direction.DESC : Sort.Direction.ASC, "gia");
            case "d" -> sort = Sort.by(orderBy.equals("h") ? Sort.Direction.DESC : Sort.Direction.ASC, "nxb");
            case "a" -> sort = Sort.by(orderBy.equals("h") ? Sort.Direction.DESC : Sort.Direction.ASC, "soLuong");
            default -> sort = Sort.by(Sort.Direction.ASC, "tenSach");
        }
        return sort;
    }
}
