
package com.proyecto.service;

import com.proyecto.domain.Ropa;
import java.util.List;


public interface RopaService {
    public List<Ropa> getRopas(boolean activo);

    // Se obtiene un Ropa, a partir del id de un ropa
    public Ropa getRopa(Ropa ropa);

    // Se inserta un nuevo ropa si el id del ropa esta vacío
    // Se actualiza un ropa si el id del ropa NO esta vacío
    public void save(Ropa ropa);

    // Se elimina el ropa que tiene el id pasado por parámetro
    public void delete(Ropa ropa);

    
   
}
