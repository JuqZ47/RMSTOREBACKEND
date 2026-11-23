package cl.duoc.rmstore_backend.controller;

import cl.duoc.rmstore_backend.model.Categoria;
import cl.duoc.rmstore_backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaService.getAllCategorias();
    }

    @GetMapping("/buscar")
    public Categoria getCategoriaById(@RequestParam Long id) {
        return categoriaService.getCategoriaById(id);
    }

    @PostMapping("/add")
    public Categoria saveCategoria(@RequestBody Categoria categoria) {
        return categoriaService.saveCategoria(categoria);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteCategoria(id);
    }
}