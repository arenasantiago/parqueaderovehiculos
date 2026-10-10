# AGENTS.md

## Proyecto
API web (Spring Boot + Thymeleaf) para gestión de parqueadero de motos y carros:
registro, listado, modificación, cobro por tiempo y servicios (lavado/casco).

## Stack
- Java 26 (JAVA_HOME = `C:\Program Files\Java\jdk-26.0.2`)
- Spring Boot 4.1.0 (Spring Framework 7, Hibernate 7)
- Maven 3.9.6 (wrapper `mvnw.cmd` — NO usar `mvn` global)
- Base de datos: PostgreSQL (Supabase) — `jdbc:postgresql://db.XXXX.supabase.co:5432`
- Lombok 1.18.46, Thymeleaf

## Comandos (Windows / PowerShell)
- Compilar: `.\mvnw.cmd compile`
- Correr: `.\mvnw.cmd spring-boot:run`  →  http://localhost:8080
- Tests: `.\mvnw.cmd test`
- Limpiar: `.\mvnw.cmd clean`

## Configuración / Variables de entorno
- Credenciales en `.env` (raíz, NO versionado) — leerlo `application.properties` con
  `spring.config.import=optional:file:.env[.properties]`.
- Copiar `.env.example` a `.env` y llenar:
  - `DB_HOST` (ej. `db.XXXX.supabase.co`), `DB_PORT` (5432), `DB_NAME` (postgres),
    `DB_USER` (postgres), `DB_PASSWORD`
  - `SUPABASE_URL`, `SUPABASE_PUBLISHABLE_KEY`, `SUPABASE_SECRET_KEY`, `SUPABASE_JWKS_URL`
- Esquema se auto-genera con `spring.jpa.hibernate.ddl-auto=update` (Hibernate crea la
  tabla `model_vehiculo` en Supabase).

## Estructura del código
- `src/main/java/com/example/parqueaderomotoycarro/`
  - `Controller/ControllerParqueadero.java` — rutas MVC y formularios
  - `Service/IServiceParqueadero.java` + `Service/ServiceParqueadero.java` — lógica de negocio
  - `Repository/IRepositorioVehiculo.java` — JPA repository
  - `Model/ModelVehiculo.java` — entidad JPA (Lombok `@Getter @Setter`)
- `src/main/resources/templates/` — vistas Thymeleaf (index, listavehiculos, modificar, cobrar)
- `src/main/resources/static/` — CSS/JS (Bootstrap)

## Convenciones
- Código en español (variables, mensajes, comentarios).
- Entidad usa Lombok; getters/setters generados (no escribir manualmente).
- El test `@SpringBootTest` conecta contra Supabase real (requiere `.env` con password).
- Mantener `seSSL`/dialectos MySQL fuera: el driver es PostgreSQL.

## Estado / Roadmap
- **Checkpoint alcanzado**: migración MySQL → Supabase (PostgreSQL), Spring Boot 3.2.5 → 4.1.0
  con Java 26, y refactorización principal:
  - Corrección del 500 en Modificar/Cobrar (fragment `navbar(activo)` llamando sin parámetro
    en `modificar.html`/`cobrar.html`).
  - `ServiceParqueadero` reescrito: `cobrar()` devuelve `ModelTicket`, nuevos `calcularHoras`,
    `valorAcumulado` (min. 1 hora), `traerContadores`; `modificarVehiculo` null-safe.
  - Factura en PDF real generado en servidor con flying-saucer (`ServicePdf` + `ticket-pdf.html`).
  - Front: contadores de ocupación, branding con logo, modal de servicios, columna de tiempo.
  - Test de humo de plantillas `TemplateSmokeTest` (JUnit + Mockito, sin BD) que además
    verifica que flying-saucer genera un PDF válido en JDK 26.
- **Pendiente** (deuda restante):
  - `Model/ModelVehiculo.java` — muchos constructores duplicados; revisar si sobran.
  - `Controller/ControllerParqueadero.java` — rutas duplicadas de servicio
    (`/servicio/{id}` y `/servicioModificado/{id}`).
  - Verificación runtime de la factura PDF (requiere `.env` con `DB_PASSWORD` real).
