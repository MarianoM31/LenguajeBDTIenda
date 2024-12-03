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
        model.addAttribute("categorias", categorias); // Lista de categorías
        model.addAttribute("categoria", new Categoria()); // Objeto vacío para el formulario
        model.addAttribute("totalCategorias", categorias.size());
        return "/categoria/listado";
    }

    // Guardar categoría (nueva o modificada)
    @PostMapping("/guardar")
    public String categoriaGuardar(
            @ModelAttribute Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        try {
            if (!imagenFile.isEmpty()) {
                // Sube la imagen a Firebase o tu sistema de almacenamiento
                String rutaImagen = firebaseStoreService.cargaImagen(
                        imagenFile, "categoria", categoria.getIdCategoria());
                categoria.setRutaImagen(rutaImagen);
            }
            // Guarda la categoría
            categoriaService.save(categoria);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría guardada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la categoría: " + e.getMessage());
        }
        return "redirect:/categoria/listado";
    }

    // Eliminar categoría
    @GetMapping("/eliminar/{idCategoria}")
    public String categoriaEliminar(
            @PathVariable Long idCategoria,
            RedirectAttributes redirectAttributes) {
        try {
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(idCategoria); // Crear una instancia con el ID
            categoriaService.delete(categoria); // Eliminar la categoría
            redirectAttributes.addFlashAttribute("mensaje", "Categoría eliminada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la categoría: " + e.getMessage());
        }
        return "redirect:/categoria/listado"; // Redirigir al listado
    }

    @GetMapping("/modificar/{idCategoria}")
    public String categoriaModificar(
            @PathVariable Long idCategoria,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            // Agrega este log para verificar si el ID llega al controlador
            System.out.println("Intentando modificar categoría con ID: " + idCategoria);

            // Buscar la categoría por ID
            Categoria categoria = categoriaService.getCategoria(new Categoria(idCategoria));

            // Agrega este log para verificar si la categoría fue encontrada
            System.out.println("Categoría encontrada: " + (categoria != null ? categoria.getDescripcion() : "No encontrada"));

            if (categoria != null) {
                model.addAttribute("categoria", categoria); // Agregar categoría al modelo
                return "/categoria/modifica"; // Redirigir a la vista de modificación
            } else {
                redirectAttributes.addFlashAttribute("error", "Categoría no encontrada.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar la categoría: " + e.getMessage());
            // Agrega este log para identificar errores
            e.printStackTrace();
        }
        return "redirect:/categoria/listado"; // Redirigir al listado si ocurre un error
    }

}
