package cl.rmstore.products.service;

import cl.rmstore.products.dto.*;
import cl.rmstore.products.entity.Producto;
import cl.rmstore.products.exception.ApiException;
import cl.rmstore.products.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {
    @Mock ProductoRepository repository;
    @InjectMocks ProductoService service;

    private Producto producto(String id, int stock) {
        return new Producto(id, "Set " + id, "City", 10000, stock, 3, "", "img", List.of("img"));
    }

    @Test
    void descuentoValidoActualizaStock() {
        Producto p = producto("LEG-001", 10);
        when(repository.bloquearPorId("LEG-001")).thenReturn(Optional.of(p));
        var resultado = service.descontarStock(new DescuentoStockRequest(List.of(new StockItem("LEG-001", 3))));
        assertEquals(7, p.getStock());
        assertEquals(7, resultado.get(0).stock());
    }

    @Test
    void ventaConStockInsuficienteNoDescuentaNinguno() {
        Producto primero = producto("LEG-001", 10);
        Producto segundo = producto("LEG-002", 1);
        when(repository.bloquearPorId("LEG-001")).thenReturn(Optional.of(primero));
        when(repository.bloquearPorId("LEG-002")).thenReturn(Optional.of(segundo));
        assertThrows(ApiException.class, () -> service.descontarStock(new DescuentoStockRequest(
                List.of(new StockItem("LEG-001", 2), new StockItem("LEG-002", 3)))));
        assertEquals(10, primero.getStock());
        assertEquals(1, segundo.getStock());
    }

    @Test
    void permiteAgruparCantidadesDuplicadasDelCarrito() {
        Producto p = producto("LEG-001", 4);
        when(repository.bloquearPorId("LEG-001")).thenReturn(Optional.of(p));
        service.descontarStock(new DescuentoStockRequest(
                List.of(new StockItem("LEG-001", 1), new StockItem("LEG-001", 2))));
        assertEquals(1, p.getStock());
    }

    @Test
    void impideCrearProductoConIdDuplicado() {
        when(repository.existsById("LEG-001")).thenReturn(true);
        var request = new ProductoRequest("LEG-001", "Set", "City", 12000, 1, 1, "", "", List.of());
        assertThrows(ApiException.class, () -> service.crear(request));
        verify(repository, never()).save(any());
    }

    @Test
    void listarOrdenadoPorId() {
        when(repository.findAll()).thenReturn(List.of(producto("LEG-002", 3), producto("LEG-001", 4)));
        assertEquals("LEG-001", service.listar().get(0).id());
    }
}
