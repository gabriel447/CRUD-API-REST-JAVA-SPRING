package me.fullstackgabe.domain.controller.exception;

public class ProductNotFoundException extends RuntimeException {
    ProductNotFoundException(Long id) {
        super("Could not find product " + id);
    }
}
