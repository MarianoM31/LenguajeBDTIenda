package com.proyecto.controller;

import com.proyecto.domain.Cosas;
import com.proyecto.domain.Ropa;
import com.proyecto.service.CategoriaService;
import com.proyecto.service.RopaService;
import com.proyecto.service.impl.FirebaseStoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ropa")
public class RopaController {

    @Autowired
    private RopaService ropaService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    private String listado(Model model) {
        var ropas = ropaService.getRopas(false);
        model.addAttribute("ropas", ropas);

        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);

        model.addAttribute("totalRopas", ropas.size());
        return "/ropa/listado";
    }

    @GetMapping("/nuevo")
    public String ropaNuevo(Ropa ropa) {
        return "/ropa/modifica";
    }

    @Autowired
    private FirebaseStoreServiceImpl firebaseStoreService;

    @PostMapping("/guardar")
    public String ropaGuardar(Ropa ropa,
            @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()) {
            ropaService.save(ropa);
            ropa.setRutaImagen(
                    firebaseStoreService.cargaImagen(
                            imagenFile,
                            "ropa",
                            ropa.getIdRopa()));
        }
        ropaService.save(ropa);
        return "redirect:/ropa/listado";
    }

    @GetMapping("/eliminar/{idRopa}")
    public String ropaEliminar(Ropa ropa) {
        ropaService.delete(ropa);
        return "redirect:/ropa/listado";
    }

    @GetMapping("/modificar/{idRopa}")
    public String ropaModificar(Ropa ropa, Model model) {
        ropa = ropaService.getRopa(ropa);
        model.addAttribute("ropa", ropa);

        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);

        return "/ropa/modifica";
    }

      
    
}
