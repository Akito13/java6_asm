package sof3021.ca4.nhom1.asm.qls.service;

import sof3021.ca4.nhom1.asm.qls.model.Cart;

import java.util.Map;

public interface Cartable<E, T> {
    E addProduct(T id, int quantity, E cart) throws IllegalArgumentException;
    E removeProduct(T id, E cart, int quantity);
    E updateProduct(T id, int quantity, E cart);
    E clear(E cart);
//    Cart getProducts();
    int getCount(E cart);
    double getAmount(E cart);
}
