package com.example.parqueaderomotoycarro.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parqueaderomotoycarro.Model.ModelVehiculo;
import com.example.parqueaderomotoycarro.Repository.IRepositorioVehiculo;

@Service
public class ServiceParqueadero implements IServiceParqueadero {

    @Autowired
    private IRepositorioVehiculo parking;

    private String[] picoYplaca = { "1", "5" };
    private double lavado = 30000;
    private double casco = 2000;

    @Override
    public List<ModelVehiculo> traerVehiculos() {
        List<ModelVehiculo> lista = parking.findAll();
        return lista;
    }

    int cantidadCarro;
    int cantidadMoto;

    @Override
    public String ingresarVehiculo(ModelVehiculo vehiculo) {
        // Creacion de viarables de tiempo local
        LocalDateTime tiempo = LocalDateTime.now();
        int horaEntrada = tiempo.getHour();
        int minutos = tiempo.getMinute();
        String hora = horaEntrada + ":" + minutos;
        // Contiene la placa ingresada y la guarda completamente en mayusculas
        String placa = vehiculo.getPlaca().toUpperCase();
        boolean casco, lavado;
        // Verificando si la placa existe
        for (ModelVehiculo placaVehiculoAntiguo : this.traerVehiculos()) {
            if (placaVehiculoAntiguo.getPlaca().equals(placa)) {
                return "¡Error: La placa existe!";
            }
        }
        try {
            // Comprobando cantidad de carros y motos

            // Comprobando el tamaño de la placa
            if (!(vehiculo.getPlaca().length() > 6 || vehiculo.getPlaca().length() < 6)) {
                // Verificar si la parte de adelante de la placa contiene numeros
                if (contieneNumeros(vehiculo.getPlaca().substring(0, 3))) {
                    return "¡Error: La placa digitada no es valida!";
                }
                // Vetificar si es carro
                else if (contieneNumeros(vehiculo.getPlaca().substring(5, 6))) {
                    // Agregar carro
                    String placaInicio = placa.substring(0, 3);
                    String placaFinal = placa.substring(3, 6);
                    // Verifica si la parte del inicio de la placa NO contiene numeros
                    if (!contieneNumeros(placaInicio)) {
                        // Verifica si la parte final de la placa NO contiene una letra
                        if (!contieneLetra(placaFinal)) {
                            // Guardar el ultimo digito digito de la placa
                            String digitoFinalPlaca = placaFinal.substring(2, 3);
                            // Comprueba pico y placa
                            if (!(digitoFinalPlaca.equals((picoYplaca[0])) || digitoFinalPlaca.equals(picoYplaca[1]))) {
                                if (vehiculo.getCasco()) {
                                    casco = true;
                                } else {
                                    casco = false;
                                }
                                if (cantidadCarro < 5) {
                                    String tipoVehiculo = "Carro";
                                    ModelVehiculo carro = new ModelVehiculo(tipoVehiculo, placa, hora, horaEntrada);
                                    cantidadCarro++;
                                    parking.save(carro);
                                    return "Se ha añadido un nuevo carro!";
                                } else {
                                    return "Cantidad de carros maxima";
                                }
                            } else {
                                return "Usted tiene pico y placa";
                            }
                        } else {
                            return "Esto no es una placa de carro\n";
                        }
                    }
                } else {
                    // Verificar si es una moto
                    String placaInicio = placa.substring(0, 3);
                    String placaFinal = placa.substring(3, 6);
                    // Verifica si la parte de adelante de la placa contiene numeros o no
                    if (contieneNumeros(placaInicio)) {
                        return "¡ERROR!: La placa digitada es invalida!";
                    } else {
                        // Comprobar el ultimo digito de la placa de la moto
                        switch (placaFinal.substring(2, 3)) {
                            case "A":
                                break;
                            case "B":
                                break;
                            case "C":
                                break;
                            case "D":
                                break;
                            case "E":
                                break;
                            case "F":
                                break;
                            case "G":
                                break;
                            case "H":
                                break;
                            default:
                                return "¡ERROR!: PLACA NO EXISTENTE!, \nROBO?";
                        }
                        // comprobar si la posicion 4 y 5 de la placa son numeros
                        String parNumeros = placaFinal.substring(0, 2);
                        if (contieneNumeros(parNumeros)) {
                            // Atrapando el 4to digito de la moto para verificar si tiene pico y placa
                            String picoYplacaMoto = placa.substring(3, 4);
                            if (picoYplacaMoto.equals(picoYplaca[0]) || picoYplacaMoto.equals(picoYplaca[1])) {
                                return "¡ERROR!: Usted tiene pico y placa";
                            } else {
                                if (cantidadMoto < 10) {
                                    String tipoVehiculo = "Moto";
                                    ModelVehiculo moto = new ModelVehiculo(tipoVehiculo, placa, hora, horaEntrada);
                                    cantidadMoto++;
                                    parking.save(moto);
                                    return "Se ha añadido la moto con exito!";
                                } else {
                                    return "Cantidad de motos maxima";
                                }

                            }
                        } else {
                            return "La placa está mal digitada";
                        }
                    }
                }
            } else {
                return "¡Error: El tamaño de la placa no es valido!";
            }
        } catch (Exception e) {
            return "Error, vehiculo no permitido";
        }
        return "";
    }

    @Override
    public ModelVehiculo encontrarVehiculo(Long id) {
        ModelVehiculo vehiculo = parking.findById(id).orElse(null);
        return vehiculo;
    }

    @Override
    public String eliminarVehiculo(Long id) {
        ModelVehiculo vehi = this.encontrarVehiculo(id);
        if (vehi.getTipoVehiculo().equals("Moto")) {
            cantidadMoto--;
        } else if (vehi.getTipoVehiculo().equals("Carro")) {
            cantidadCarro--;
        }
        parking.deleteById(id);
        return "¡Se ha eliminado con éxito!";
    }

    @Override
    public String servicios(Long id, boolean casclav) {
        ModelVehiculo vehiculoEncontrado = this.encontrarVehiculo(id);
        if (vehiculoEncontrado.getTipoVehiculo().equals("Moto")) {
            vehiculoEncontrado.setCasco(casclav);
            vehiculoEncontrado.setLavado(false);
            parking.save(vehiculoEncontrado);
            return "Se ha actualizado el servicio de la moto "+vehiculoEncontrado.getPlaca();
        }
        if (vehiculoEncontrado.getTipoVehiculo().equals("Carro")) {
            vehiculoEncontrado.setLavado(casclav);
            vehiculoEncontrado.setCasco(false);
            parking.save(vehiculoEncontrado);
            return "Se ha actualizado el servicio del carro";
        }
        return "Q pasa";
    }

    @Override
    public String modificarVehiculo(Long id, ModelVehiculo vehiculo) {
        ModelVehiculo vehiculoAntiguo = this.encontrarVehiculo(id);
        String placa = vehiculo.getPlaca().toUpperCase();
        // Verificar si la el vehiculo a modificar tiene placa o no
        int horaNueva = 0;
        // Verifica si el vehiculo a modificar le mandaron placa o no, en caso de que si...
        if (!(vehiculo.getPlaca() == "")) {
            String horaModificada;
            if (vehiculo.getHora() == "") {
                horaModificada = vehiculoAntiguo.getHora();
                horaNueva = vehiculoAntiguo.getHoraEntrada();
            }else if (vehiculo.getHora() != vehiculoAntiguo.getHora()) {
                horaModificada = vehiculo.getHora();
                String mitadHora[] = {horaModificada.substring(0), horaModificada.substring(0, 2)};
                if (!(mitadHora[1].contains(":"))) {
                    horaNueva = Integer.parseInt(mitadHora[1]);
                }else if((contieneNumeros(mitadHora[0]))){
                    horaNueva = Integer.parseInt(mitadHora[0]);
                }
            } 
            else{
                horaModificada = vehiculoAntiguo.getHora();
                horaNueva = vehiculoAntiguo.getHoraEntrada();
            }
            // VehiculoConPlaca
            // Comprobando el tamaño de la placa
            if (!(vehiculo.getPlaca().length() > 6 || vehiculo.getPlaca().length() < 6)) {
                // Verificar si la parte de adelante de la placa contiene numeros
                if (contieneNumeros(vehiculo.getPlaca().substring(0, 3))) {
                    return "¡Error: La placa digitada no es valida!";
                }
                // Vetificar si es carro
                else if (contieneNumeros(vehiculo.getPlaca().substring(5, 6))) {
                    // Modificar carro
                    String placaInicio = placa.substring(0, 3);
                    String placaFinal = placa.substring(3, 6);
                    // Verifica si la parte del inicio de la placa NO contiene numeros
                    if (!contieneNumeros(placaInicio)) {
                        // Verifica si la parte final de la placa NO contiene una letra
                        if (!contieneLetra(placaFinal)) {
                            // Guardar el ultimo digito digito de la placa
                            String digitoFinalPlaca = placaFinal.substring(2, 3);
                            // Comprueba pico y placa
                            if (!(digitoFinalPlaca.equals((picoYplaca[0])) || digitoFinalPlaca.equals(picoYplaca[1]))) {
                                String tipoVehiculo = "Carro";
                                if (vehiculo.getPlaca().equals(vehiculoAntiguo.getPlaca())) {
                                    return "!Error la placa ya existe!";
                                }
                                ModelVehiculo motoConPlaca = new ModelVehiculo(vehiculoAntiguo.getId(),
                                        placa, horaModificada, vehiculoAntiguo.getLavado(), horaNueva,
                                        vehiculoAntiguo.getCasco(), tipoVehiculo);
                                parking.save(motoConPlaca);
                            } else {
                                return "Usted tiene pico y placa";
                            }
                        }
                    }
                } else {
                    // Verificar si es una moto
                    String placaInicio = placa.substring(0, 3);
                    String placaFinal = placa.substring(3, 6);
                    // Verifica si la parte de adelante de la placa contiene numeros o no
                    if (contieneNumeros(placaInicio)) {
                        return "¡ERROR!: La placa digitada es invalida!";
                    } else {
                        // Comprobar el ultimo digito de la placa de la moto
                        switch (placaFinal.substring(2, 3)) {
                            case "A":
                                break;
                            case "B":
                                break;
                            case "C":
                                break;
                            case "D":
                                break;
                            case "E":
                                break;
                            case "F":
                                break;
                            case "G":
                                break;
                            case "H":
                                break;
                            default:
                                return "¡ERROR!: PLACA NO EXISTENTE!, \nROBO?";
                        }
                        // comprobar si la posicion 4 y 5 de la placa son numeros
                        String parNumeros = placaFinal.substring(0, 2);
                        if (contieneNumeros(parNumeros)) {
                            // Atrapando el 4to digito de la moto para verificar si tiene pico y placa
                            String picoYplacaMoto = placa.substring(3, 4);
                            if (picoYplacaMoto.equals(picoYplaca[0]) || picoYplacaMoto.equals(picoYplaca[1])) {
                                return "¡ERROR!: Usted tiene pico y placa";
                            } else {
                                String tipoVehiculo = "Moto";
                                ModelVehiculo motoConPlaca = new ModelVehiculo(vehiculoAntiguo.getId(),
                                placa, horaModificada, vehiculoAntiguo.getLavado(), horaNueva,
                                vehiculoAntiguo.getCasco(), tipoVehiculo);
                                parking.save(motoConPlaca);
                            }
                        } else {
                            return "La placa está mal digitada";
                        }
                    }
                }
            } else {
                return "¡Error: El tamaño de la placa no es valido!";
            }
        }else if(vehiculo.getPlaca() == ""){
            // Vehiculo sin placa
            String horaModificada;
            if (vehiculo.getHora() == "") {
                horaModificada = vehiculoAntiguo.getHora();
                horaNueva = vehiculoAntiguo.getHoraEntrada();
            }else if (vehiculo.getHora() != vehiculoAntiguo.getHora()) {
                horaModificada = vehiculo.getHora();
                String mitadHora[] = {horaModificada.substring(0), horaModificada.substring(0, 2)};
                if ((contieneLetra(mitadHora[1]))) {
                    horaNueva = Integer.parseInt(mitadHora[1]);
                }else if(contieneLetra(mitadHora[0])){
                    horaNueva = Integer.parseInt(mitadHora[0]);
                }
            } else {
                horaModificada = vehiculoAntiguo.getHora();
                horaNueva = vehiculoAntiguo.getHoraEntrada();
            }
            // Comprobando el tamaño de la placa
            ModelVehiculo motoSinPlaca = new ModelVehiculo(vehiculoAntiguo.getId(),
            vehiculoAntiguo.getPlaca(), horaModificada, vehiculoAntiguo.getLavado(),
                    horaNueva, vehiculoAntiguo.getCasco(), vehiculoAntiguo.getTipoVehiculo());
                    parking.save(motoSinPlaca);
        }
        return "Se ha modificado con éxito!";
    }

    @Override
    public String cobro(Long id, double cobro) {
        // Encontrando el vehiculo
        ModelVehiculo vehiculo = this.encontrarVehiculo(id);

        if (vehiculo != null) {
            // Almacenando la cantidad de el metodo creado para saber cuanto paga
            double pago = debePagar(vehiculo.getId());
            if (cobro < pago) {
                return "Saldo insuficiente";
            } else if (cobro >= pago) {
                String lavado;
                double resta = cobro - pago;
                // Verficiando si tuvo lavado o no
                if (vehiculo.getLavado()) {
                    lavado = "Si";
                } else {
                    lavado = "No";
                }
                // Verificando si tuvo casco o no
                String casco;
                if (vehiculo.getCasco()) {
                    casco = "Si";
                } else {
                    casco = "No";
                }
                parking.deleteById(id);
                if (vehiculo.getTipoVehiculo().equals("Moto")) {
                    return "Feliz día, gracias por la estadía.\nLa " + vehiculo.getTipoVehiculo()
                        + " paga tiene las siguientes caracteristicas: \n PLACA: " +
                        vehiculo.getPlaca() + ", Hora de Entrada: " + vehiculo.getHora() +", Cantidad de horas en parqueadero: "+ horasEnParqueadero+", Lavado: "
                        + lavado + ", Casco: " + casco + ", Valor abonado: " + cobro + ", Valor devuelto: " + resta;
                }else if(vehiculo.getTipoVehiculo().equals("Carro")){
                    return "Feliz día, gracias por la estadía.\nEl " + vehiculo.getTipoVehiculo()
                        + " pago tiene las siguientes caracteristicas: \n PLACA: " +
                        vehiculo.getPlaca() + ", Hora de Entrada: " + vehiculo.getHora() +", Cantidad de horas en parqueadero: "+ horasEnParqueadero+ ",Lavado: "
                        + lavado + ", Casco: " + casco + ", Valor abonado: " + cobro + ", Valor devuelto: " + resta;
                }
                
            }
        } else {
            return "El vehiculo no existe";
        }
        return "Feliz día!";
    }
    public static int horasEnParqueadero;
    @Override
    public double debePagar(Long id) {
        double horaFraccionAuto = 7000;
        double horaFraccionMoto = 3500;
        double valorPagar;
        // Encontrando el vehiculo
        ModelVehiculo vehiculo = this.encontrarVehiculo(id);
        // Creación de variables de tiempo
        LocalDateTime tiempo = LocalDateTime.now();
        int horaActual = tiempo.getHour();
        // Creacion de variables para verificar si tiene casco o si lavo el carro
        // Verificar si la placa es de una moto o un carro
        switch (vehiculo.getTipoVehiculo()) {
            case "Carro":
            // Calculando cantidad de tiempo en el parqueadero
            horasEnParqueadero = horaActual - vehiculo.getHoraEntrada();
                if (horasEnParqueadero  > 0) {
                    // Calculando cuanto debe pagar
                    double cantidadAPagar = horaFraccionAuto * horasEnParqueadero;
                    if (vehiculo.getLavado()) {
                        valorPagar = cantidadAPagar += lavado;
                        return valorPagar;
                    }
                    return cantidadAPagar;
                } else if (horasEnParqueadero == 0) {
                    if (vehiculo.getLavado()) {
                        valorPagar = horaFraccionAuto +=lavado;
                        return valorPagar;
                    }
                    return horaFraccionAuto;
                }
                break;
            case "Moto":
            horasEnParqueadero = horaActual - vehiculo.getHoraEntrada();
                if (horasEnParqueadero > 0) {
                    // Calculando cantidad de tiempo en el parqueadero
                    // Calculando cuanto debe pagar
                    double cantidadAPagar = horaFraccionMoto * horasEnParqueadero;
                    if (vehiculo.getCasco()) {
                        valorPagar = cantidadAPagar +=casco;
                        return valorPagar;
                    }
                    return cantidadAPagar;
                } else if (horasEnParqueadero == 0) {
                    if (vehiculo.getCasco()) {
                        valorPagar = horaFraccionMoto += casco;
                        return valorPagar;
                    }
                    return horaFraccionMoto;
                }
                break;
            default:
                break;
        }
        return 0;
    }

    // Verifica si el string mandado contiene una letra
    public boolean contieneLetra(String placa) {
        for (char temp : placa.toCharArray()) {
            if (Character.isLetter(temp)) {
                return true;
            }
        }
        return false;
    }

    public boolean contieneNumeros(String placa) {
        for (char temp : placa.toCharArray()) {
            if (Character.isDigit(temp)) {
                return true;
            }
        }
        return false;
    }
}
