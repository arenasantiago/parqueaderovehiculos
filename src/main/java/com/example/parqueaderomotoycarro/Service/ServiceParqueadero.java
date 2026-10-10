package com.example.parqueaderomotoycarro.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parqueaderomotoycarro.Model.ModelContadores;
import com.example.parqueaderomotoycarro.Model.ModelTicket;
import com.example.parqueaderomotoycarro.Model.ModelVehiculo;
import com.example.parqueaderomotoycarro.Repository.IRepositorioVehiculo;

@Service
public class ServiceParqueadero implements IServiceParqueadero {

    public static final int CAPACIDAD_CARROS = 5;
    public static final int CAPACIDAD_MOTOS = 10;

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
                                if (cantidadCarro < CAPACIDAD_CARROS) {
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
                                if (cantidadMoto < CAPACIDAD_MOTOS) {
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
        if (vehi != null) {
            if (vehi.getTipoVehiculo().equals("Moto")) {
                cantidadMoto--;
            } else if (vehi.getTipoVehiculo().equals("Carro")) {
                cantidadCarro--;
            }
            parking.deleteById(id);
        }
        return "¡Se ha eliminado con éxito!";
    }

    @Override
    public String servicios(Long id, boolean casclav) {
        ModelVehiculo vehiculoEncontrado = this.encontrarVehiculo(id);
        if (vehiculoEncontrado != null) {
            if (vehiculoEncontrado.getTipoVehiculo().equals("Moto")) {
                vehiculoEncontrado.setCasco(casclav);
                vehiculoEncontrado.setLavado(false);
                parking.save(vehiculoEncontrado);
                return "Se ha actualizado el servicio de la moto " + vehiculoEncontrado.getPlaca();
            }
            if (vehiculoEncontrado.getTipoVehiculo().equals("Carro")) {
                vehiculoEncontrado.setLavado(casclav);
                vehiculoEncontrado.setCasco(false);
                parking.save(vehiculoEncontrado);
                return "Se ha actualizado el servicio del carro";
            }
        }
        return "Q pasa";
    }

    @Override
    public String modificarVehiculo(Long id, ModelVehiculo vehiculo) {
        ModelVehiculo vehiculoAntiguo = this.encontrarVehiculo(id);
        if (vehiculoAntiguo == null) {
            return "¡Error: El vehículo no existe!";
        }
        String placaNueva = vehiculo.getPlaca() == null ? "" : vehiculo.getPlaca().toUpperCase().trim();
        String horaNueva = vehiculo.getHora() == null ? "" : vehiculo.getHora().trim();

        // Si mandan una placa nueva se valida y se usa, si no se conserva la actual
        String placaFinal = vehiculoAntiguo.getPlaca();
        String tipoVehiculo = vehiculoAntiguo.getTipoVehiculo();
        if (!placaNueva.isEmpty()) {
            String resultadoValidacion = validarPlaca(placaNueva);
            if (!(resultadoValidacion.equals("Carro") || resultadoValidacion.equals("Moto"))) {
                return resultadoValidacion;
            }
            if (placaNueva.equals(vehiculoAntiguo.getPlaca())) {
                return "!Error la placa ya existe!";
            }
            for (ModelVehiculo placaVehiculoAntiguo : this.traerVehiculos()) {
                if (placaVehiculoAntiguo.getPlaca().equals(placaNueva)) {
                    return "¡Error: La placa existe!";
                }
            }
            placaFinal = placaNueva;
            tipoVehiculo = resultadoValidacion;
        }

        // Hora nueva (si vacia o igual, se conserva la original)
        String horaModificada;
        int horaEntradaNueva;
        if (horaNueva.isEmpty() || horaNueva.equals(vehiculoAntiguo.getHora())) {
            horaModificada = vehiculoAntiguo.getHora();
            horaEntradaNueva = vehiculoAntiguo.getHoraEntrada();
        } else {
            horaModificada = horaNueva;
            horaEntradaNueva = extraerHora(horaNueva);
        }

        vehiculoAntiguo.setPlaca(placaFinal);
        vehiculoAntiguo.setTipoVehiculo(tipoVehiculo);
        vehiculoAntiguo.setHora(horaModificada);
        vehiculoAntiguo.setHoraEntrada(horaEntradaNueva);
        parking.save(vehiculoAntiguo);
        return "Se ha modificado con éxito!";
    }

    @Override
    public ModelTicket cobrar(Long id, double pago) {
        // Encontrando el vehiculo
        ModelVehiculo vehiculo = this.encontrarVehiculo(id);
        if (vehiculo == null) {
            return null;
        }
        double total = debePagar(vehiculo.getId());
        if (pago < total) {
            return null;
        }
        int horas = calcularHoras(vehiculo);
        double valorServicios = vehiculo.getLavado() ? lavado : 0;
        if (vehiculo.getTipoVehiculo().equals("Moto") && vehiculo.getCasco()) {
            valorServicios = casco;
        }
        double valorHoras = total - valorServicios;
        ModelTicket ticket = new ModelTicket(
                vehiculo.getPlaca(),
                vehiculo.getTipoVehiculo(),
                vehiculo.getHora(),
                horas,
                vehiculo.getCasco(),
                vehiculo.getLavado(),
                valorHoras,
                valorServicios,
                total,
                pago,
                pago - total,
                LocalDateTime.now());
        parking.deleteById(id);
        return ticket;
    }

    @Override
    public ModelContadores traerContadores() {
        long carros = parking.countByTipoVehiculo("Carro");
        long motos = parking.countByTipoVehiculo("Moto");
        return new ModelContadores(carros, motos, CAPACIDAD_CARROS, CAPACIDAD_MOTOS);
    }

    @Override
    public int calcularHoras(ModelVehiculo vehiculo) {
        LocalDateTime tiempo = LocalDateTime.now();
        int horaActual = tiempo.getHour();
        int horas = horaActual - vehiculo.getHoraEntrada();
        if (horas <= 0) {
            return 1;
        }
        return horas;
    }

    @Override
    public double valorAcumulado(ModelVehiculo vehiculo) {
        if (vehiculo == null) {
            return 0;
        }
        int horas = calcularHoras(vehiculo);
        if (vehiculo.getTipoVehiculo().equals("Carro")) {
            double valor = 7000 * horas;
            if (vehiculo.getLavado()) {
                valor += lavado;
            }
            return valor;
        }
        if (vehiculo.getTipoVehiculo().equals("Moto")) {
            double valor = 3500 * horas;
            if (vehiculo.getCasco()) {
                valor += casco;
            }
            return valor;
        }
        return 0;
    }

    @Override
    public double debePagar(Long id) {
        ModelVehiculo vehiculo = this.encontrarVehiculo(id);
        if (vehiculo == null) {
            return 0;
        }
        return valorAcumulado(vehiculo);
    }

    // Valida una placa y devuelve su tipo ("Carro"/"Moto") o el mensaje de error
    private String validarPlaca(String placa) {
        if (placa.length() != 6) {
            return "¡Error: El tamaño de la placa no es valido!";
        }
        // Verificar si la parte de adelante de la placa contiene numeros
        if (contieneNumeros(placa.substring(0, 3))) {
            return "¡Error: La placa digitada no es valida!";
        }
        // Verificar si es carro
        if (contieneNumeros(placa.substring(5, 6))) {
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
                        return "Carro";
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
                        return "Moto";
                    }
                } else {
                    return "La placa está mal digitada";
                }
            }
        }
        return "¡Error: La placa digitada no es valida!";
    }

    // Extrae la hora (antes de los dos puntos) de un string "HH:MM" o "H:MM"
    private int extraerHora(String hora) {
        String[] partes = hora.split(":");
        try {
            if (partes.length > 0 && !partes[0].trim().isEmpty()) {
                return Integer.parseInt(partes[0].trim());
            }
        } catch (NumberFormatException e) {
            // hora no numerica, se conserva 0
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
