package com.example.demo.serviceImpl;

import com.example.demo.dto.AlojamientoDTO;
import com.example.demo.dto.AlojamientoDetalleDTO;
import com.example.demo.entity.Address;
import com.example.demo.entity.Alojamiento;
import com.example.demo.entity.Categoria;
import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.AlojamientoRepository;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AlojamientoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlojamientoServiceImpl implements AlojamientoService {

	  @Autowired
	    private AlojamientoRepository alojamientoRepository;

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private AddressRepository addressRepository;
	    

	    @Autowired
	    private CategoriaRepository categoriaRepository;
	    
	    @Autowired
	    private ServiceRepository serviceRepository; 

	  
	    @Override
	    public List<AlojamientoDetalleDTO> getAllAlojamientosWithDetails() {
	        List<Object[]> results = alojamientoRepository.findAllAlojamientosWithDetails();
	        
	        return results.stream().map(record -> {
	            AlojamientoDetalleDTO dto = new AlojamientoDetalleDTO();
	            dto.setAlojamientoId(((Number) record[0]).longValue());
	            dto.setAlojamientoNombre((String) record[1]);
	            dto.setAlojamientoDescripcion((String) record[2]);
	            dto.setAlojamientoPrecio((BigDecimal) record[3]);
	            dto.setAlojamientoImage1((String) record[4]);
	            dto.setAlojamientoImage2((String) record[5]);
	            dto.setAlojamientoImage3((String) record[6]);
	            
	            
	            dto.setAddressDireccion((String) record[7]);
	            dto.setAddressNeighborhood((String) record[8]);
	            dto.setAddressLat((BigDecimal) record[9]);
	            dto.setAddressLng((BigDecimal) record[10]);

	            
	            
	            dto.setCategoriaNombre((String) record[11]);
	            dto.setServicioNombre((String) record[12]);
	            dto.setServicioDescripcion((String) record[13]);
	            return dto;
	        }).collect(Collectors.toList());
	    }
	    
	    /* 
    @Override
    public List<Alojamiento> getAllAlojamientosWithDetails() {
        return alojamientoRepository.findAllWithDetails();
    }
     */  
    

    @Override
    public List<Object[]> getAllAlojamientosForGuest() {
        return alojamientoRepository.findAllForGuest();
    }
    

    @Override
    public List<AlojamientoDTO> getAlojamientosByUserId(Long userId) {
        List<Alojamiento> alojamientos = alojamientoRepository.findAllByUserId(userId);
        return alojamientos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
    
    
      

    @Override
    @Transactional
    public Alojamiento createAlojamiento(AlojamientoDTO alojamientoDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getHasActiveSubscription() == null || !user.getHasActiveSubscription()) {
            throw new RuntimeException("Usuario no tiene una suscripción activa");
        }



        Address address = new Address();
        address.setDireccion(alojamientoDTO.getDireccion());
        address.setNeighborhood(alojamientoDTO.getNeighborhood());
        address.setLat(new BigDecimal(alojamientoDTO.getLat()));
        address.setLng(new BigDecimal(alojamientoDTO.getLng()));
        address.setUser(user);
        address.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Address savedAddress = addressRepository.save(address);

        Alojamiento alojamiento = new Alojamiento();
        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setImage1(alojamientoDTO.getImage1());
        alojamiento.setImage2(alojamientoDTO.getImage2());
        alojamiento.setImage3(alojamientoDTO.getImage3());
        alojamiento.setDireccion(savedAddress);
        alojamiento.setUser(user);
        alojamiento.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Categoria categoria = categoriaRepository.findById(alojamientoDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        alojamiento.setCategoria(categoria);

        Set<com.example.demo.entity.Service> services = alojamientoDTO.getServicesIds().stream()
                .map(serviceId -> serviceRepository.findById(serviceId)
                        .orElseThrow(() -> new RuntimeException("Service no encontrado con id: " + serviceId)))
                .collect(Collectors.toSet());
        alojamiento.setServices(services);

        return alojamientoRepository.save(alojamiento);
    }


 
}