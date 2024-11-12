
package com.proyecto.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Cosas extends Ropa{
     private int cantidad; 

    public Cosas() {
    }

    public Cosas(Ropa ropa) {
        super.setIdRopa(ropa.getIdRopa());
        super.setCategoria(ropa.getCategoria());
        super.setDescripcion(ropa.getDescripcion());
        super.setTalla(ropa.getTalla());
        super.setPrecio(ropa.getPrecio());
        super.setExistencias(ropa.getExistencias());
        super.setActivo(ropa.isActivo());
        super.setRutaImagen(ropa.getRutaImagen());
        this.cantidad = 0;
    }
}
