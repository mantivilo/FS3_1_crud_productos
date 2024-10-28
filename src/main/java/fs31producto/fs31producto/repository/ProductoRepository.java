package fs31producto.fs31producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fs31producto.fs31producto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>  {
    
}
