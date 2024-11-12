package com.proyecto.service.impl;

import com.proyecto.Dao.RopaDao;
import com.proyecto.domain.Ropa;
import com.proyecto.service.RopaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RopaServiceImpl implements RopaService {
    @Autowired
    private RopaDao ropaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Ropa> getRopas(boolean activos) {
        var lista = ropaDao.findAll();
        if (activos) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Ropa getRopa(Ropa ropa) {
        return ropaDao.findById(ropa.getIdRopa()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Ropa ropa) {
        ropaDao.save(ropa);
    }

    @Override
    @Transactional
    public void delete(Ropa ropa) {
        ropaDao.delete(ropa);
    }

    
    
}
