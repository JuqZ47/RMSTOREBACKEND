package cl.duoc.rmstore_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponseDTO {
    private String id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagen;
    private String categoria;
}