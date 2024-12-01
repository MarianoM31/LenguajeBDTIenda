package com.proyecto.controller;

import com.proyecto.domain.Categoria;
import com.proyecto.service.CategoriaService;
import com.proyecto.service.impl.FirebaseStoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FirebaseStoreServiceImpl firebaseStoreService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("totalCategorias", categorias.size());
        return "/categoria/listado";
    }

    @PostMapping("/guardar")
    public String categoriaGuardar(
            @ModelAttribute Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        try {
            if (!imagenFile.isEmpty()) {
                String rutaImagen = firebaseStoreService.cargaImagen(
                        imagenFile, "categoria", categoria.getIdCategoria());
                categoria.setRutaImagen(rutaImagen);
            }
            categoriaService.save(categoria);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría guardada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la categoría: " + e.getMessage());
        }
        return "redirect:/categoria/listado";
    }

    @GetMapping("/eliminar/{idCategoria}")
    public String categoriaEliminar(
            @PathVariable Long idCategoria,
            RedirectAttributes redirectAttributes) {
        try {
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(idCategoria);
            categoriaService.delete(categoria);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría eliminada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la categoría: " + e.getMessage());
        }
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")
    public String categoriaModificar(
            @PathVariable Long idCategoria,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Categoria categoria = categoriaService.getCategoria(new Categoria(idCategoria));
            if (categoria != null) {
                model.addAttribute("categoria", categoria);
                return "/categoria/modifica";
            } else {
                redirectAttributes.addFlashAttribute("error", "Categoría no encontrada.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar la categoría: " + e.getMessage());
        }
        return "redirect:/categoria/listado";
    }

}
