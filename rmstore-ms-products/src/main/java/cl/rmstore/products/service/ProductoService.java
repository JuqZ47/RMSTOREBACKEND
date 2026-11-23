package cl.rmstore.products.service;

import cl.rmstore.products.dto.*;
import cl.rmstore.products.entity.Producto;
import cl.rmstore.products.exception.ApiException;
import cl.rmstore.products.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ProductoService {
    private final ProductoRepository repository;
    public ProductoService(ProductoRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public List<ProductoResponse> listar() {
        return repository.findAll().stream().sorted(Comparator.comparing(Producto::getId))
                .map(ProductoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtener(String id) { return ProductoResponse.from(buscar(id)); }

    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        String id = request.id().trim().toUpperCase(Locale.ROOT);
        if (repository.existsById(id)) throw new ApiException(HttpStatus.CONFLICT, "Código de producto duplicado: " + id);
        Producto p = new Producto(id, request.nombre().trim(), request.categoria().trim(), request.precio(),
            request.stock(), request.stockCritico(), request.descripcion(), imagenPrincipal(request), imagenes(request));
        return ProductoResponse.from(repository.save(p));
    }

    @Transactional
    public ProductoResponse actualizar(String id, ProductoRequest request) {
        Producto p = buscar(id);
        if (!id.equalsIgnoreCase(request.id().trim())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No se permite cambiar el código del producto");
        }
        p.actualizar(request.nombre().trim(), request.categoria().trim(), request.precio(), request.stock(),
                request.stockCritico(), request.descripcion(), imagenPrincipal(request), imagenes(request));
        return ProductoResponse.from(p);
    }

    @Transactional
    public void eliminar(String id) {
        Producto p = buscar(id);
        repository.delete(p);
    }

    // Una sola transacción. Las filas se bloquean en orden fijo para evitar sobreventa y deadlocks.
    @Transactional
    public List<ProductoResponse> descontarStock(DescuentoStockRequest request) {
        Map<String, Integer> cantidades = new TreeMap<>();
        for (StockItem item : request.items()) {
            String id = item.id().trim().toUpperCase(Locale.ROOT);
            if (item.cantidad() < 1) throw new ApiException(HttpStatus.BAD_REQUEST, "Cantidad inválida para " + id);
            cantidades.merge(id, item.cantidad(), (a, b) -> Math.addExact(a, b));
        }
        List<Producto> bloqueados = new ArrayList<>();
        for (var entrada : cantidades.entrySet()) {
            Producto p = repository.bloquearPorId(entrada.getKey()).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + entrada.getKey()));
            if (p.getStock() < entrada.getValue()) {
                throw new ApiException(HttpStatus.CONFLICT, "Stock insuficiente para " + p.getNombre() +
                    ". Disponible: " + p.getStock());
            }
            bloqueados.add(p);
        }
        for (Producto p : bloqueados) p.descontarStock(cantidades.get(p.getId()));
        return bloqueados.stream().map(ProductoResponse::from).toList();
    }

    private Producto buscar(String id) {
        return repository.findById(id.toUpperCase(Locale.ROOT)).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + id));
    }
    private static String imagenPrincipal(ProductoRequest r) {
        if (r.imagen() != null && !r.imagen().isBlank()) return r.imagen().trim();
        return r.imagenes() != null && !r.imagenes().isEmpty() ? r.imagenes().get(0) : "/img/lego-default.png";
    }
    private static List<String> imagenes(ProductoRequest r) {
        if (r.imagenes() != null && !r.imagenes().isEmpty()) return r.imagenes();
        return List.of(imagenPrincipal(r));
    }
}
