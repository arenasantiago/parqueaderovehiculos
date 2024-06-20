package com.example.parqueaderomotoycarro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.parqueaderomotoycarro.Model.ModelVehiculo;

@Repository
public interface IRepositorioVehiculo extends JpaRepository <ModelVehiculo, Long>{

}