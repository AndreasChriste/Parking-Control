package com.api.parkingcontrol.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.parkingcontrol.models.ParkingSpotModel;

//responsável pelas transações com a base de dados
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID>{

    boolean existsByLicensePlateCar(String licensePlateCard);
    boolean existsByParkingSpotNumber(String parkingSpotNumber);
    boolean existsByApartmentAndBlock(String apartment, String block);
    
}
