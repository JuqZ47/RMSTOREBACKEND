package cl.rmstore.products.dto;

import cl.rmstore.products.entity.Producto;
import java.util.List;

public record ProductoResponse(String id, String codigo, String nombre, String categoria, Integer precio,
                               Integer stock, Integer stockCritico, String descripcion, String imagen,
                               List<String> imagenes) {
    public static ProductoResponse from(Producto p) {
        return new ProductoResponse(p.getId(), p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(),
                p.getStock(), p.getStockCritico(), p.getDescripcion(), p.getImagen(), p.getImagenes());
    }
}
