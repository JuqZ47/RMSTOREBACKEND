package cl.rmstore.products.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record ProductoRequest(
    @NotBlank @Size(min = 3, max = 32) String id,
    @NotBlank @Size(max = 120) String nombre,
    @NotBlank @Size(max = 60) String categoria,
    @NotNull @Min(0) Integer precio,
    @NotNull @Min(0) Integer stock,
    @NotNull @Min(0) Integer stockCritico,
    @Size(max = 2000) String descripcion,
    @Size(max = 2048) String imagen,
    @Size(max = 12) List<@NotBlank @Size(max = 2048) String> imagenes
) {}
