# Parqueadero de moto y carros con interfaz gráfica

Esta es una api que realizará gestión de servicios de un parqueadero, contiene un CRUD/ABML, servicios como **cobrar** según el tiempo de permanencia y servicios adicionales dependiendo el vehiculo.

El valor hora de una **moto** es de $3,500, si trae casco sería $2,000.

El valor hora de un **carro** es de $7,000 y si se desea realizar el lavado de dicho vehiculo costará $30,000. 

Principalmente miraremos la configuración de la base de datos en el `application.properties`

Antes de continuar tener en cuenta que...

Se utiliza la dependencia de `mysql-connector-j` que encontraremos en el **pom.xml**

Por lo tanto, la bd queda configurada de la siguiente manera:

`spring.jpa.hibernate.ddl-auto=update` Esta propiedad se refiere que ya hay una base de datos existente y que quiero actualizarla 

`spring.datasource.url=jdbc:mysql://localhost:3306/parqueadero?seSSL=false&serverTimezone=UTC` 

`spring.datasource.username=root`

`spring.datasource.password=`

`spring.jpa.database-platform= org.hibernate.dialect.MySQL8Dialect`
