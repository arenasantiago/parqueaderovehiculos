# Parqueadero de Moto y Carros

Este proyecto es una API para la gestión de servicios de un parqueadero de vehículos, con funcionalidades como CRUD/ABML y servicios adicionales según el tipo de vehículo. La aplicación permite calcular el costo por tiempo de permanencia y servicios adicionales como lavado de vehículos.

## Características
- **Gestión de parqueadero:** CRUD para vehículos (motos y carros).
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


## Base de datos
La base de datos MySQL almacena la información de cada vehículo, incluyendo:
- **ID** (único para cada vehículo).
- **Tipo de vehículo** (moto o carro).
- **Placa** (única para identificar el vehículo).
- **Hora de ingreso** (para calcular la tarifa).
- **Servicios** (si el vehículo solicitó servicios adicionales como lavado).
 Base de datos: `parqueadero`
 Usuario: `root`

 ## Configuracion del application.properties
  `spring.jpa.hibernate.ddl-auto=update`
  `spring.datasource.url=jdbc:mysql://localhost:3306/parqueadero?seSSL=false&serverTimezone=UTC`
  `spring.datasource.username=root`
  `spring.datasource.password=`
  `spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect`
  ## Endpoints

### 1. **POST /vehiculo**
   - **Descripción**: Registra un vehículo en el parqueadero.
   - **Cuerpo de la solicitud**:
     ```json
     {
       "tipo": "moto",  // "moto" o "carro"
       "placa": "ABC123", 
       "servicios": ["lavado"],  // Servicios adicionales (opcional)
       "horaIngreso": "2024-12-01T10:00:00"
     }
     ```
   - **Respuesta**: Estado del registro del vehículo.

### 2. **GET /vehiculo/{id}**
   - **Descripción**: Obtiene la información detallada de un vehículo por su ID (placa única).
   - **Respuesta**:
     ```json
     {
       "id": 1,
       "tipo": "carro",
       "placa": "ABC123",
       "horaIngreso": "2024-12-01T10:00:00",
       "servicios": ["lavado"],
       "costo": 7000  // Costo calculado
     }
     ```

### 3. **PUT /vehiculo/{id}**
   - **Descripción**: Actualiza la información de un vehículo (por ejemplo, si el vehículo pide servicios adicionales).
   - **Cuerpo de la solicitud**:
     ```json
     {
       "tipo": "carro",
       "placa": "ABC123",
       "servicios": ["lavado", "llantas"]
     }
     ```
   - **Respuesta**: Estado de la actualización.

### 4. **DELETE /vehiculo/{id}**
   - **Descripción**: Elimina un vehículo del parqueadero (por ejemplo, cuando se retira el vehículo).
   - **Respuesta**: Confirmación de eliminación.

### 5. **GET /vehiculo/{id}/costo**
   - **Descripción**: Calcula el costo del parqueadero para un vehículo en función del tiempo de permanencia y servicios adicionales solicitados.
   - **Respuesta**:
     ```json
     {
       "costo": 7000  // Costo total por hora y servicios adicionales.
     }
     ```
## Cálculo de Tarifas
### Motos:
- **Costo básico**: $3,500 por hora.
- **Costo adicional**: $2,000 si trae casco.

### Carros:
- **Costo básico**: $7,000 por hora.
- **Costo adicional**: $30,000 por lavado.

### Ejemplo de cálculo de tarifas:
- **Motos**: Si una moto permanece estacionada durante 3 horas y trae casco:
  - 3 horas x $3,500 = $10,500
  - Casco adicional: $2,000
  - **Total**: $12,500

- **Carros**: Si un carro permanece estacionado durante 4 horas y solicita lavado:
  - 4 horas x $7,000 = $28,000
  - Lavado adicional: $30,000
  - **Total**: $58,000
 ## Ejecución
   `mvn spring-boot:run`
