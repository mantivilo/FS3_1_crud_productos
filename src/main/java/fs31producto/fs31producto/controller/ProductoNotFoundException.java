package fs31producto.fs31producto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNotFoundException extends RuntimeException {
   
    public ProductoNotFoundException(String message) {
        super(message);
    }
}
