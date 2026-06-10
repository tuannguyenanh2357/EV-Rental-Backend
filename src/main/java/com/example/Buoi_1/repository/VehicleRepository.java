package com.example.Buoi_1.repository;

import com.example.Buoi_1.entity.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
}
