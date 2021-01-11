package com.example.admin.center.response.handler;

@SuppressWarnings("serial")
public class ProductNotExistException extends Exception {
    public ProductNotExistException(String message) {
        super(message);
    }
}