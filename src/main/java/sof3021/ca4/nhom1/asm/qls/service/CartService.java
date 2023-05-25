package sof3021.ca4.nhom1.asm.qls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import sof3021.ca4.nhom1.asm.qls.model.Book;
import sof3021.ca4.nhom1.asm.qls.model.Cart;
import sof3021.ca4.nhom1.asm.qls.repository.BookRepository;
import sof3021.ca4.nhom1.asm.qls.utils.SessionService;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@SessionScope
public class CartService implements Cartable<Cart, Integer> {

    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private SessionService sessionService;

    @Override
    public Cart addProduct(Integer id, int quantity, Cart cart) throws IllegalArgumentException{
        Book existingBook = cart.getOrders().get(id);
        Optional<Book> result = bookRepo.findById(id);
        result.ifPresent(book -> {
            if(quantity > book.getSoLuong()) {
                throw new IllegalArgumentException("Quantity is too high");
            }
            if(quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative");
            }
            if(existingBook == null) {
                System.out.println("Inside if of addProduct, before adding: " + book.getSoLuongMua());
                book.setSoLuongMua(book.getSoLuongMua() + quantity);
                cart.getOrders().put(book.getMaSach(), book);
                System.out.println("Inside if of addProduct, current quantity: " + book.getSoLuongMua());
            } else {
                existingBook.setSoLuongMua(existingBook.getSoLuongMua() + quantity);
                cart.getOrders().replace(id, existingBook);
                System.out.println("Inside else of addProduct, current quantity: " + existingBook.getSoLuongMua());
            }
        });
        return cart;
    }

    @Override
    public Cart removeProduct(Integer id, Cart cart, int quantity) {
        Book book = cart.getOrders().get(id);
        if(book != null) {
            if(quantity >= book.getSoLuongMua()) {
                cart.getOrders().remove(id);
            }
            if(quantity > 0 && quantity < book.getSoLuongMua()) {
                book.setSoLuongMua(book.getSoLuongMua() - quantity);
                cart.getOrders().replace(id, book);
            }
        }
        return cart;
    }

    @Override
    public Cart updateProduct(Integer id, int quantity, Cart cart) {
        return null;
    }

    @Override
    public Cart clear(Cart cart) {
        return null;
    }

//    @Override
//    public Cart getProducts() {
//        cart =
//        return cart;
//    }

    @Override
    public int getCount(Cart cart) {
        Map<Integer, Book> orders = cart.getOrders();
        int totalCount = 0;
        for(var entry: orders.entrySet()) {
            totalCount += entry.getValue().getSoLuongMua();
        }
        return totalCount;
    }

    @Override
    public double getAmount(Cart cart) {
        Map<Integer, Book> orders = cart.getOrders();
        double totalAmount = 0;
        for(var entry: orders.entrySet()) {
            totalAmount += entry.getValue().getGia() * entry.getValue().getSoLuongMua();
        }
        return totalAmount;
    }
}
