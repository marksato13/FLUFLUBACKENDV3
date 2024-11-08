package com.example.demo.service;

import com.example.demo.dto.AlojamientoDTO;
import com.example.demo.dto.AlojamientoDetalleDTO;
import com.example.demo.entity.Alojamiento;
import java.util.List;

public interface AlojamientoService {
    //List<Alojamiento> getAllAlojamientosWithDetails();
    
    List<Object[]> getAllAlojamientosForGuest();

    // Nuevo método para agregar alojamiento
    Alojamiento createAlojamiento(AlojamientoDTO alojamientoDTO, Long userId);

    //Alojamiento updateAlojamiento(AlojamientoDTO alojamientoDTO, Long userId);
    
    
    List<AlojamientoDTO> getAlojamientosByUserId(Long userId);

    
    
   List<AlojamientoDetalleDTO> getAllAlojamientosWithDetails();

    
}