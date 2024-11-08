package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.demo.entity.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlojamientoDTO {
    private Long id;
    private Long idUser;         
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String image1;
    private String image2;
    private String image3;
    private Long direccionId;     
    private String direccion;     
    private String neighborhood; 
    private String lat;           
    private String lng;           
    private Long idCategoria;     
    
    
   

    
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Set<Long> servicesIds;
    
    
}
