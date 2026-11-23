package cl.rmstore.products.dto;

import jakarta.validation.constraints.*;

public record StockItem(@NotBlank String id, @Min(1) int cantidad) {}
