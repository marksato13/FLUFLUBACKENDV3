package com.example.demo.controller.general;

import com.example.demo.dto.AlojamientoDTO;
import com.example.demo.dto.AlojamientoDetalleDTO;
import com.example.demo.entity.Alojamiento;
import com.example.demo.service.AlojamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alojamientos")
@CrossOrigin(origins = "http://localhost:4200")
public class AlojamientoController {
	
    @Autowired
    private AlojamientoService alojamientoService;

    
    

    @PostMapping("/create")
    public ResponseEntity<Alojamiento> createAlojamiento(@RequestBody AlojamientoDTO alojamientoDTO,
                                                         @RequestParam Long userId) {
        Alojamiento alojamiento = alojamientoService.createAlojamiento(alojamientoDTO, userId);
        return ResponseEntity.ok(alojamiento);
    }

    
  
    
    @GetMapping("/getAllDetails")
    public ResponseEntity<List<AlojamientoDetalleDTO>> getAllAlojamientosWithDetails() {
        List<AlojamientoDetalleDTO> alojamientoDetalles = alojamientoService.getAllAlojamientosWithDetails();
        return ResponseEntity.ok(alojamientoDetalles);
    }
	    
	 
    /*
   
    @GetMapping("/getAll")
    public ResponseEntity<List<AlojamientoDTO>> getAllAlojamientos() {
        List<Alojamiento> alojamientos = alojamientoService.getAllAlojamientosWithDetails();
        List<AlojamientoDTO> alojamientoDTOs = alojamientos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alojamientoDTOs);
    }

  
      */
    
    @GetMapping("/getAllGuest")
    public ResponseEntity<List<Map<String, Object>>> getAllAlojamientosForGuest() {
        List<Object[]> alojamientos = alojamientoService.getAllAlojamientosForGuest();
        List<Map<String, Object>> result = alojamientos.stream().map(a -> {
            Map<String, Object> alojamientoData = new HashMap<>();
            alojamientoData.put("id", a[0]);
            alojamientoData.put("nombre", a[1]);
            alojamientoData.put("descripcion", a[2]);
            alojamientoData.put("precio", a[3]);
            alojamientoData.put("image1", a[4]);
            alojamientoData.put("image2", a[5]);
            alojamientoData.put("image3", a[6]);
            return alojamientoData;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AlojamientoDTO>> getAlojamientosByUserId(@PathVariable Long userId) {
        List<AlojamientoDTO> alojamientos = alojamientoService.getAlojamientosByUserId(userId);
        return ResponseEntity.ok(alojamientos);
    }
    

    private AlojamientoDTO convertToDTO(Alojamiento alojamiento) {
        AlojamientoDTO dto = new AlojamientoDTO();
        dto.setId(alojamiento.getId());
        dto.setNombre(alojamiento.getNombre());
        dto.setDescripcion(alojamiento.getDescripcion());
        dto.setPrecio(alojamiento.getPrecio());
        dto.setImage1(alojamiento.getImage1());
        dto.setImage2(alojamiento.getImage2());
        dto.setImage3(alojamiento.getImage3());
        dto.setDireccion(alojamiento.getDireccion().getDireccion());
        dto.setNeighborhood(alojamiento.getDireccion().getNeighborhood());
        dto.setLat(alojamiento.getDireccion().getLat().toString());
        dto.setLng(alojamiento.getDireccion().getLng().toString());

        if (alojamiento.getCategoria() != null) {
            dto.setIdCategoria(alojamiento.getCategoria().getId());
        }

        if (alojamiento.getServices() != null) {
            Set<Long> serviceIds = alojamiento.getServices().stream()
                    .map(service -> service.getId())
                    .collect(Collectors.toSet());
            dto.setServicesIds(serviceIds);
        }

        return dto;
    }

}
