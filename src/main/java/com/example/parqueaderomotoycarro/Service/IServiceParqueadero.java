package com.example.parqueaderomotoycarro.Service;

import java.util.List;

import com.example.parqueaderomotoycarro.Model.ModelVehiculo;

public interface IServiceParqueadero {
    //Me trae la lista de vehiculos
    public List<ModelVehiculo> traerVehiculos();

    //Ingresa un nuevo vehiculo
    public String ingresarVehiculo(ModelVehiculo vehiculo);

    //Modificar vehiculo
    public String modificarVehiculo(Long id, ModelVehiculo vehiculo);

    //Eliminar vehiculo
    public String eliminarVehiculo(Long id);

    //Encontrar vehiculo
    public ModelVehiculo encontrarVehiculo(Long id);
    //TotalPagar
    public double debePagar(Long id);
    //Cobra y elimina un carro
    public String cobro(Long id, double cobro);

    //servicios
    public String servicios(Long id, boolean caslav);
}
