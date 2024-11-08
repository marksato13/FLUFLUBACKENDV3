package com.example.demo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlojamientoDetalleDTO {
    private Long alojamientoId;
    private String alojamientoNombre;
    private String alojamientoDescripcion;
    private BigDecimal alojamientoPrecio;
    private String alojamientoImage1;
    private String alojamientoImage2;
    private String alojamientoImage3;
    
    
    
    
    
    
    
    private String addressDireccion;

    
    private String addressNeighborhood;

    
    private BigDecimal addressLat;

    private BigDecimal addressLng;

    
    
    private String categoriaNombre;
       
    
    private String servicioNombre;
    
    private String servicioDescripcion;
}
