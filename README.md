# Parqueadero de Motos y Carros

Aplicación web (Spring Boot + Thymeleaf) para la gestión de un parqueadero de motos y
carros: registro, listado, modificación, cobro por tiempo de permanencia y servicios
adicionales (lavado y casco).

## Características

- **Registro de vehículos** con validación de placa (formato colombiano), pico y placa
  y aforo máximo (5 carros / 10 motos).
- **Listado, modificación y eliminación** de vehículos.
- **Servicios adicionales**: casco para motos, lavado para carros.
- **Cobro automático** calculado por horas de permanencia + servicios.
- Interfaz Thymeleaf + Bootstrap (sin JavaScript propio).

## Tecnologías

- **Java 26** (`JAVA_HOME = C:\Program Files\Java\jdk-26.0.2`)
- **Spring Boot 4.1.0** (Spring Framework 7, Hibernate 7, Jakarta EE 11)
- **Maven 3.9.6** (wrapper `mvnw.cmd`)
- **PostgreSQL** en la nube (Supabase) con driver `org.postgresql:postgresql`
- **Lombok 1.18.46**, **Thymeleaf**

## Requisitos

- JDK 26 instalado y `JAVA_HOME` apuntando a él (necesario para `mvnw`).
- Una cuenta de Supabase con el password de la base de datos Postgres.

## Configuración

1. Clona el repositorio:

   ```bash
   git clone https://github.com/arenasantiago/parqueaderovehiculos.git
   ```

2. Copia el archivo de variables de entorno:

   ```bash
   copy .env.example .env
   ```

3. Llena `.env` con tus credenciales (NO versionado):

   ```properties
   DB_HOST=db.XXXX.supabase.co
   DB_PORT=5432
   DB_NAME=postgres
   DB_USER=postgres
   DB_PASSWORD=tu_password_de_la_bd

   SUPABASE_URL=https://XXXX.supabase.co
   SUPABASE_PUBLISHABLE_KEY=sb_publishable_XXXX
   SUPABASE_SECRET_KEY=sb_secret_XXXX
   SUPABASE_JWKS_URL=https://XXXX.supabase.co/auth/v1/.well-known/jwks.json
   ```

   > `DB_PASSWORD` es el password de la **base de datos** Postgres, no la
   > `SUPABASE_SECRET_KEY`. Se obtiene en Supabase →
   > *Project Settings → Database → Connection string → Reveal password*.

## Ejecución

```powershell
.\mvnw.cmd spring-boot:run
```

Abre http://localhost:8080

El esquema se auto-genera con `spring.jpa.hibernate.ddl-auto=update` (Hibernate crea la
tabla `model_vehiculo` en Supabase al primer arranque).

## Rutas (vistas MVC)

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/` | Página principal (registro de vehículo) |
| POST | `/ingresar` | Registra un nuevo vehículo |
| POST | `/servicio/{id}` | Agrega servicio (casco/lavado) |
| GET | `/lista` | Lista de vehículos en el parqueadero |
| POST | `/eliminar/{id}` | Elimina un vehículo |
| GET | `/modificar/{id}` | Formulario de modificación |
| POST | `/modificar/modificando/{id}` | Guarda la modificación |
| POST | `/servicioModificado/{id}` | Servicio desde el listado |
| GET | `/cobrar/{id}` | Formulario de cobro (muestra monto) |
| POST | `/cobrando/{id}` | Procesa el cobro y retira el vehículo |

## Base de datos

Tabla `model_vehiculo` (generada por Hibernate) con los campos:

- `id` — identificador único (secuencia).
- `placa` — placa del vehículo (única).
- `tipo_vehiculo` — `Moto` o `Carro`.
- `hora_entrada` / `hora` — hora de ingreso (para calcular tarifa).
- `casco` — booleano, servicio de casco (motos).
- `lavado` — booleano, servicio de lavado (carros).
- `pago` — valor abonado al cobrar.

## Cálculo de tarifas

### Motos
- **Costo básico**: $3.500 por hora.
- **Costo adicional**: $2.000 si trae casco.

### Carros
- **Costo básico**: $7.000 por hora.
- **Costo adicional**: $30.000 por lavado.

### Ejemplos
- **Moto** 3 horas con casco: 3 × $3.500 + $2.000 = **$12.500**.
- **Carro** 4 horas con lavado: 4 × $7.000 + $30.000 = **$58.000**.

## Estado del proyecto

> **Checkpoint** — versión funcional "sin IA", construida a mano, recién migrada de
> MySQL local a Supabase (PostgreSQL) y de Spring Boot 3.2.5 a 4.1.0. A partir de este
> punto se iniciará la **refactorización** del código.
