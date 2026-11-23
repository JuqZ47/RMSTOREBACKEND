package cl.rmstore.products.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
public class cdProducto {
    @Id
    @Column(length = 32, nullable = false)
    private String id;

    @Column(length = 120, nullable = false)
    private String nombre;

    @Column(length = 60, nullable = false)
    private String categoria;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "stock_critico", nullable = false)
    private Integer stockCritico;

    @Column(length = 2000)
    private String descripcion;

    @Column(length = 2048)
    private String imagen;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "producto_imagenes", joinColumns = @JoinColumn(name = "producto_id"))
    @OrderColumn(name = "orden")
    @Column(name = "imagen_url", length = 2048, nullable = false)
    private List<String> imagenes = new ArrayList<>();

    protected Producto() {}

    public Producto(String id, String nombre, String categoria, Integer precio, Integer stock, Integer stockCritico,
                    String descripcion, String imagen, List<String> imagenes) {
        this.id = id;
        actualizar(nombre, categoria, precio, stock, stockCritico, descripcion, imagen, imagenes);
    }

    public void actualizar(String nombre, String categoria, Integer precio, Integer stock, Integer stockCritico,
                           String descripcion, String imagen, List<String> imagenes) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.stockCritico = stockCritico;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.imagenes.clear();
        if (imagenes != null) this.imagenes.addAll(imagenes);
    }

    public void descontarStock(int cantidad) { this.stock -= cantidad; }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public Integer getPrecio() { return precio; }
    public Integer getStock() { return stock; }
    public Integer getStockCritico() { return stockCritico; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public List<String> getImagenes() { return List.copyOf(imagenes); }
}
