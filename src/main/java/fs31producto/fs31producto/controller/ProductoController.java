package fs31producto.fs31producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fs31producto.fs31producto.model.Producto;
import fs31producto.fs31producto.service.ProductoService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

        //http://localhost:8080/productos
        @GetMapping
        public CollectionModel<EntityModel<Producto>> getAllProductos() {
            List<Producto> productos = productoService.getAllProductos();
            log.info("GET /productos");
            log.info("Retornando todas los productos");
            List<EntityModel<Producto>> productoResources = productos.stream()
                .map( producto -> EntityModel.of(producto,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(producto.getId())).withSelfRel()
                ))
                .collect(Collectors.toList());
    
            WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProductos());
            CollectionModel<EntityModel<Producto>> resources = CollectionModel.of(productoResources, linkTo.withRel("productos"));
    
            return resources;
        }

        @GetMapping("/{id}")
        public EntityModel<Producto> getProductoById(@PathVariable Long id) {
            Optional<Producto> producto = productoService.getProductoById(id);
    
            if (producto.isPresent()) {
                return EntityModel.of(producto.get(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(id)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProductos()).withRel("all-productos"));
            } else {
                throw new ProductoNotFoundException("Producto not found with id: " + id);
            }
        }

        @PostMapping
        public EntityModel<Producto> createProducto(@Validated @RequestBody Producto producto) {
            Producto createdProducto = productoService.createProducto(producto);
                return EntityModel.of(createdProducto,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(createdProducto.getId())).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProductos()).withRel("all-productos"));
        }

        @PutMapping("/{id}")
        public EntityModel<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
            Producto updatedProducto = productoService.updateProducto(id, producto);
            return EntityModel.of(updatedProducto,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(id)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProductos()).withRel("all-productos"));
        }

        @DeleteMapping("/{id}")
        public void deleteProducto(@PathVariable Long id){
            productoService.deleteProducto(id);
        }

        static class ErrorResponse {
            private final String message;
    
            public ErrorResponse(String message) {
                this.message = message;
            }
    
            public String getMessage() {
                return message;
            }
        }
}
