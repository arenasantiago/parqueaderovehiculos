package com.example.parqueaderomotoycarro.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class ModelVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String placa;
    private int horaEntrada, horaSalida;
    private String hora;
    private boolean casco;
    private boolean lavado;
    private String tipoVehiculo;
    private double pago;

    public ModelVehiculo(Long id, String placa, int horaEntrada, int horaSalida, String hora, boolean casco, boolean lavado, String tipoVehiculo) {
        this.id = id;
        this.placa = placa;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.hora = hora;
        this.casco = casco;
        this.lavado = lavado;
        this.tipoVehiculo = tipoVehiculo;
    }
    public ModelVehiculo (Long id, String placa, String hora, boolean casco, boolean lavado, String tipoVehiculo){
        this.id = id;
        this.placa = placa;
        this.hora = hora;
        this.casco = casco;
        this.lavado = lavado;
        this.tipoVehiculo = tipoVehiculo;
    }
    public ModelVehiculo (Long id, String placa, String hora, boolean lavado, int horaEntrada, boolean casco, String tipoVehiculo){
        this.id = id;
        this.placa = placa;
        this.hora = hora;
        this.lavado = lavado;
        this.horaEntrada = horaEntrada;
        this.casco = casco;
        this.tipoVehiculo = tipoVehiculo;
    }
    public ModelVehiculo (Long id, String placa, String hora,boolean lavado, String tipoVehiculo){
        this.id = id;
        this.placa = placa;
        this.hora = hora;
        this.lavado = lavado;
        this.tipoVehiculo = tipoVehiculo;
    }
    
    public boolean getLavado(){
        return lavado;
    }
    public boolean getCasco(){
        return casco;
    }
    
    public ModelVehiculo(String tipoVehiculo,String placa, String hora,int horaEntrada){
        this.placa = placa;
        this.hora = hora;
        this.horaEntrada = horaEntrada;
        this.tipoVehiculo = tipoVehiculo;
    }
    
    public ModelVehiculo(){

    }
}
