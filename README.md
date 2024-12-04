Parqueadero de Moto y Carros
Este proyecto es una API para la gestión de servicios de un parqueadero de vehículos, con funcionalidades como CRUD/ABML y servicios adicionales según el tipo de vehículo. La aplicación permite calcular el costo por tiempo de permanencia y servicios adicionales como lavado de vehículos.

Características
Gestión de parqueadero: CRUD para vehículos (motos y carros).
Cálculo de tarifas:
Motos: $3,500 por hora, $2,000 adicional si trae casco.
Carros: $7,000 por hora, $30,000 adicional por lavado.
Base de datos MySQL: Configuración de base de datos en application.properties.
Tecnologías Utilizadas
Backend: Java (Spring Boot)
Base de datos: MySQL
Dependencias:
mysql-connector-j
Instalación
Clona el repositorio:
bash
Copiar código
git clone https://github.com/arenasantiago/parqueaderovehiculos.git
Configura la base de datos en MySQL:
Base de datos: parqueadero
Usuario: root
Contraseña: (deja vacío o configura según tu setup)
Configura el archivo application.properties:
properties
Copiar código
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/parqueadero?seSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
Ejecuta la aplicación:
bash
Copiar código
mvn spring-boot:run
Uso
La API está disponible para gestionar el parqueadero y calcular tarifas. Consulta los endpoints para más detalles.
Contribuciones
Las contribuciones son bienvenidas. Si deseas mejorar este proyecto, sigue estos pasos:

Haz un fork del repositorio.
Crea una nueva rama (git checkout -b feature/nueva-funcionalidad).
Realiza tus cambios y haz commit (git commit -am 'Añadí una nueva funcionalidad').
Sube tus cambios a tu fork (git push origin feature/nueva-funcionalidad).
Abre un pull request.
Licencia
Este proyecto está bajo la licencia MIT.
