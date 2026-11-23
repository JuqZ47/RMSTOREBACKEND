package cl.rmstore.products.controller;

import cl.rmstore.products.dto.*;
import cl.rmstore.products.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService service;
    public ProductoController(ProductoService service) { this.service = service; }

    @GetMapping
    public List<ProductoResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable String id) { return service.obtener(id); }

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest producto) {
        ProductoResponse creado = service.crear(producto);
        return ResponseEntity.created(URI.create("/api/productos/" + creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable String id, @Valid @RequestBody ProductoRequest producto) {
        return service.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) { service.eliminar(id); }

    @PostMapping("/descontar-stock")
    public List<ProductoResponse> descontarStock(@Valid @RequestBody DescuentoStockRequest request) {
        return service.descontarStock(request);
    }
}
