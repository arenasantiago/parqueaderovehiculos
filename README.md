# Parqueadero de Moto y Carros

Este proyecto es una API para la gestión de servicios de un parqueadero de vehículos, con funcionalidades como CRUD/ABML y servicios adicionales según el tipo de vehículo. La aplicación permite calcular el costo por tiempo de permanencia y servicios adicionales como lavado de vehículos.

## Características

- **Gestión de parqueadero:** CRUD para vehículos (motos y carros).
- **Cálculo de tarifas:** 
  - **Motos:** $3,500 por hora, $2,000 adicional si trae casco.
  - **Carros:** $7,000 por hora, $30,000 adicional por lavado.
- **Base de datos MySQL**: Configuración de base de datos en `application.properties`.

## Tecnologías Utilizadas

- **Backend:** Java (Spring Boot)
- **Base de datos:** MySQL
- **Dependencias:**
  - `mysql-connector-j`

## Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/arenasantiago/parqueaderovehiculos.git


## Configura la base de datos

