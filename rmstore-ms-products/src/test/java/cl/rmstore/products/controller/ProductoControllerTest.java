package cl.rmstore.products.controller;

import cl.rmstore.products.dto.*;
import cl.rmstore.products.exception.ApiException;
import cl.rmstore.products.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = ProductoController.class, properties = "app.cors-origin=http://localhost:5173")
@org.springframework.context.annotation.Import(cl.rmstore.products.config.CorsConfig.class)
class ProductoControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper mapper;
    @MockitoBean ProductoService service;

    private ProductoResponse producto() {
        return new ProductoResponse("LEG-001", "LEG-001", "Millennium Falcon", "Star Wars", 169990,
                12, 3, "Coleccionable", "https://example.org/img.jpg", List.of("https://example.org/img.jpg"));
    }

    @Test
    void listarDevuelveJson() throws Exception {
        when(service.listar()).thenReturn(List.of(producto()));
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("LEG-001"))
                .andExpect(jsonPath("$[0].precio").value(169990));
    }

    @Test
    void crearDevuelve201YLocation() throws Exception {
        when(service.crear(any())).thenReturn(producto());
        var request = new ProductoRequest("LEG-001", "Millennium Falcon", "Star Wars", 169990,
                12, 3, "Coleccionable", "https://example.org/img.jpg", List.of());
        mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/productos/LEG-001"));
    }

    @Test
    void validaStockNegativo() throws Exception {
        var request = new ProductoRequest("LEG-007", "Producto inválido", "City", 1000,
                -1, 3, "", "", List.of());
        mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
        verify(service, never()).crear(any());
    }

    @Test
    void productoInexistenteRetorna404() throws Exception {
        when(service.obtener("NO-EXISTE"))
                .thenThrow(new ApiException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        mockMvc.perform(get("/api/productos/NO-EXISTE"))
                .andExpect(status().isNotFound());
    }

    @Test
    void descontarStockRecibeItems() throws Exception {
        when(service.descontarStock(any())).thenReturn(List.of(producto()));
        mockMvc.perform(post("/api/productos/descontar-stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"items\":[{\"id\":\"LEG-001\",\"cantidad\":2}]}"))
                .andExpect(status().isOk());
        verify(service).descontarStock(any());
    }

    @Test
    void consultaActualizaYEliminaConEstadosCorrectos() throws Exception {
        when(service.obtener("LEG-001")).thenReturn(producto());
        when(service.actualizar(eq("LEG-001"), any())).thenReturn(producto());
        mockMvc.perform(get("/api/productos/LEG-001")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value("LEG-001"));
        var body=new ProductoRequest("LEG-001","Millennium Falcon","Star Wars",169990,0,3,"","/img.jpg",List.of());
        mockMvc.perform(put("/api/productos/LEG-001").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/api/productos/LEG-001")).andExpect(status().isNoContent()).andExpect(content().string(""));
        verify(service).eliminar("LEG-001");
    }

    @Test
    void controlaJsonInvalidoConflictosYCors() throws Exception {
        mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content("{"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").exists());
        when(service.obtener("LEG-001")).thenThrow(new org.springframework.dao.DataIntegrityViolationException("duplicado"));
        mockMvc.perform(get("/api/productos/LEG-001")).andExpect(status().isConflict());
        mockMvc.perform(options("/api/productos").header("Origin","http://localhost:5173").header("Access-Control-Request-Method","POST"))
                .andExpect(status().isOk()).andExpect(header().string("Access-Control-Allow-Origin","http://localhost:5173"));
    }
}
