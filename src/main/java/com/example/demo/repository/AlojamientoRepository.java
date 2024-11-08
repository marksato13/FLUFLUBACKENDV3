package com.example.demo.repository;

import com.example.demo.dto.AlojamientoDetalleDTO;
import com.example.demo.entity.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlojamientoRepository extends JpaRepository<Alojamiento, Long> {

	
    @Query("SELECT a FROM Alojamiento a JOIN FETCH a.direccion d LEFT JOIN FETCH a.services c")
    List<Alojamiento> findAllWithDetails();
    
    /*
    @Query("SELECT a FROM Alojamiento a " +
    	       "LEFT JOIN FETCH a.direccion " +
    	       "LEFT JOIN FETCH a.categoria " +
    	       "LEFT JOIN FETCH a.services")
    	List<Alojamiento> findAllWithDetails();

    */
    
    
    @Query(value = "SELECT " +
            "a.id AS alojamiento_id, " +
            "a.nombre AS alojamiento_nombre, " +
            "a.descripcion AS alojamiento_descripcion, " +
            "a.precio AS alojamiento_precio, " +
            "a.image1 AS alojamiento_image1, " +
            "a.image2 AS alojamiento_image2, " +
            "a.image3 AS alojamiento_image3, " +
            
            
            
            
            "ad.direccion AS address_direccion, " +
            "ad.neighborhood AS address_neighborhood, " +
            "ad.lat AS address_lat, " +
            "ad.lng AS address_lng, " +

            
            "c.nombre AS categoria_nombre, " +
            "s.nombre AS servicio_nombre, " +
            "s.descripcion AS servicio_descripcion " +
            
            
            
            
            
            "FROM alojamientos a " +
            
            
            "LEFT JOIN address ad ON a.direccion_id = ad.id " +
            "LEFT JOIN categorias c ON a.id_categoria = c.id " +
            "LEFT JOIN alojamiento_has_services ahs ON a.id = ahs.id_alojamiento " +
            "LEFT JOIN services s ON ahs.id_service = s.id", nativeQuery = true)
    
    
    
    
    List<Object[]> findAllAlojamientosWithDetails();
    
    
    
    
    
    @Query("SELECT a FROM Alojamiento a JOIN a.user u JOIN u.subscriptions s JOIN s.plan p " +
            "WHERE s.status = 'active' AND CURRENT_TIMESTAMP BETWEEN s.startDate AND s.endDate " +
            "ORDER BY p.name DESC")
     List<Alojamiento> findAllByPlanPriority();

    @Query("SELECT COUNT(a) FROM Alojamiento a WHERE a.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM Alojamiento a LEFT JOIN FETCH a.direccion LEFT JOIN FETCH a.services WHERE a.user.id = :userId")
    List<Alojamiento> findAllByUserId(@Param("userId") Long userId);


    @Query("SELECT a.id, a.nombre, a.descripcion, a.precio, a.image1, a.image2, a.image3 FROM Alojamiento a")
    List<Object[]> findAllForGuest();
    
    
    @Query("SELECT a FROM Alojamiento a LEFT JOIN FETCH a.categoria LEFT JOIN FETCH a.services WHERE a.id = :id")
    Optional<Alojamiento> findByIdWithDetails(@Param("id") Long id);

    
}
