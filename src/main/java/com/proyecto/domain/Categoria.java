package com.proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    private String descripcion;
    private String rutaImagen;
    private boolean activo;

    // Relación con la entidad Ropa
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ropa> ropas = new ArrayList<>();

    // Constructor vacío
    public Categoria() {
    }

    // Constructor con campos principales
    public Categoria(String descripcion, String rutaImagen, boolean activo) {
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.activo = activo;
    }

    public Categoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    // Métodos adicionales para manejar la relación
    public void agregarRopa(Ropa ropa) {
        ropas.add(ropa);
        ropa.setCategoria(this);
    }

    public void eliminarRopa(Ropa ropa) {
        ropas.remove(ropa);
        ropa.setCategoria(null);
    }
}
