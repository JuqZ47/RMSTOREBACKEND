package cl.rmstore.products.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record DescuentoStockRequest(@NotEmpty List<@NotNull @Valid StockItem> items) {}
