package com.example.demo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "alojamientos")

public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    private String image1;
    private String image2;
    private String image3;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", nullable = false)
    private Address direccion;
    
    
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "alojamiento_has_services",  
        joinColumns = @JoinColumn(name = "id_alojamiento"),
        inverseJoinColumns = @JoinColumn(name = "id_service")
    )
    private Set<Service> services;

}
