package com.example.parqueaderomotoycarro.Service;

import java.util.List;

import com.example.parqueaderomotoycarro.Model.ModelContadores;
import com.example.parqueaderomotoycarro.Model.ModelTicket;
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
    //Cobra y elimina un carro, devolviendo la factura (null si no hay saldo suficiente)
    public ModelTicket cobrar(Long id, double pago);

    //servicios
    public String servicios(Long id, boolean caslav);

    //Contadores de ocupacion por tipo
    public ModelContadores traerContadores();

    //Horas transcurridas de un vehiculo (minimo 1)
    public int calcularHoras(ModelVehiculo vehiculo);

    //Valor acumulado actual de un vehiculo
    public double valorAcumulado(ModelVehiculo vehiculo);
}
