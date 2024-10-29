package fs31producto.fs31producto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fs31producto.fs31producto.model.Producto;
import fs31producto.fs31producto.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto createProducto(Producto producto){
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Long id, Producto producto){
        if(productoRepository.existsById(id)){
            producto.setId(id);
            return productoRepository.save(producto);
        }else{
            return null;
        }

    }

    @Override
    public void deleteProducto(Long Id){
        productoRepository.deleteById(Id);
    }
    
}
