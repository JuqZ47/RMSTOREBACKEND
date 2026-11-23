package cl.rmstore.products.service;

import cl.rmstore.products.dto.*;
import cl.rmstore.products.entity.Producto;
import cl.rmstore.products.repository.ProductoRepository;
import cl.rmstore.products.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceCrudTest {
    @Mock ProductoRepository repo;
    @InjectMocks ProductoService service;
    private Producto producto() { return new Producto("LEG-001","Millennium Falcon","Star Wars",169990,3,1,"Set","/principal.jpg",List.of("/galeria.jpg")); }
    private ProductoRequest request(String id,String imagen,List<String> galeria) {
        return new ProductoRequest(id," Millennium Falcon "," Star Wars ",169990,0,1,"Set",imagen,galeria);
    }
    @Test void creaNormalizandoCodigoYTextosYConservaImagenes() {
        when(repo.save(any())).thenAnswer(i->i.getArgument(0));
        var resultado=service.crear(request(" leg-001 "," /principal.jpg ",List.of("/uno.jpg","/dos.jpg")));
        assertEquals("LEG-001",resultado.id());assertEquals("Millennium Falcon",resultado.nombre());
        assertEquals("/principal.jpg",resultado.imagen());assertEquals(2,resultado.imagenes().size());assertEquals(0,resultado.stock());
    }
    @Test void imagenPrincipalPuedeDerivarseDeGaleriaOImagenPorDefecto() {
        when(repo.save(any())).thenAnswer(i->i.getArgument(0));
        assertEquals("/galeria.jpg",service.crear(request("LEG-001","",List.of("/galeria.jpg"))).imagen());
        var resultado=service.crear(request("LEG-002",null,null));
        assertEquals("/img/lego-default.png",resultado.imagen());
        assertEquals(List.of("/img/lego-default.png"),resultado.imagenes());
    }
    @Test void consultaActualizaPrecioYStockCeroYElimina() {
        Producto p=producto();when(repo.findById("LEG-001")).thenReturn(Optional.of(p));
        assertEquals(3,service.obtener("leg-001").stock());
        var actualizado=service.actualizar("LEG-001",request("leg-001","/principal.jpg",List.of()));
        assertEquals(0,actualizado.stock());assertEquals(List.of("/principal.jpg"),actualizado.imagenes());
        service.eliminar("LEG-001");verify(repo).delete(p);
    }
    @Test void impideCambiarIdentificadorYRespondeInexistentes() {
        when(repo.findById("LEG-001")).thenReturn(Optional.of(producto()));
        assertEquals(400,assertThrows(ApiException.class,()->service.actualizar("LEG-001",request("LEG-002","",List.of()))).getStatus().value());
        assertEquals(404,assertThrows(ApiException.class,()->service.obtener("NO-EXISTE")).getStatus().value());
        assertThrows(ApiException.class,()->service.eliminar("NO-EXISTE"));verify(repo,never()).delete(any());
    }
    @Test void validaCantidadesYNoEncuentraProductoAntesDeDescontar() {
        assertEquals(400,assertThrows(ApiException.class,()->service.descontarStock(new DescuentoStockRequest(List.of(new StockItem("LEG-001",0))))).getStatus().value());
        assertEquals(404,assertThrows(ApiException.class,()->service.descontarStock(new DescuentoStockRequest(List.of(new StockItem("LEG-001",1))))).getStatus().value());
    }
    @Test void entidadPuedeActualizarseSinGaleriaYConservaDatosRestantes() {
        Producto p=producto();p.actualizar("Actualizado","City",0,0,0,null,null,null);
        assertEquals("Actualizado",p.getNombre());assertTrue(p.getImagenes().isEmpty());assertEquals(0,p.getPrecio());
        assertNull(p.getDescripcion());assertNull(p.getImagen());
    }
    @Test void arranqueDelegaArgumentosASpringBoot() {
        try(var spring=mockStatic(org.springframework.boot.SpringApplication.class)) {
            String[] args={"--server.port=0"};
            cl.rmstore.products.ProductsApplication.main(args);
            spring.verify(()->org.springframework.boot.SpringApplication.run(cl.rmstore.products.ProductsApplication.class,args));
        }
    }
}
