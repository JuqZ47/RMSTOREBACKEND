package cl.duoc.rmstore_backend.controller;

import cl.duoc.rmstore_backend.model.Producto;
import cl.duoc.rmstore_backend.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/buscar")
    public Producto getProductoById(@RequestParam Long id) {
        return productoService.getProductoById(id);
    }

    @PostMapping("/add")
    public Producto saveProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
    }
}