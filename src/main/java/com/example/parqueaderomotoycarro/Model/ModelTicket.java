package com.example.parqueaderomotoycarro.Model;

import java.time.LocalDateTime;

public record ModelTicket(
        String placa,
        String tipoVehiculo,
        String horaEntrada,
        int horasEnParqueadero,
        boolean casco,
        boolean lavado,
        double valorHoras,
        double valorServicios,
        double total,
        double pago,
        double devuelta,
        LocalDateTime fechaEmision) {
}
