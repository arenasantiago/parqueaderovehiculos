package com.example.parqueaderomotoycarro;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.linkbuilder.ILinkBuilder;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.parqueaderomotoycarro.Model.ModelContadores;
import com.example.parqueaderomotoycarro.Model.ModelTicket;
import com.example.parqueaderomotoycarro.Model.ModelVehiculo;
import com.example.parqueaderomotoycarro.Service.IServiceParqueadero;
import com.example.parqueaderomotoycarro.Service.ServicePdf;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TemplateSmokeTest {

    private SpringTemplateEngine crearMotor() {
        SpringTemplateEngine motor = new SpringTemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCacheable(false);
        motor.setTemplateResolver(resolver);
        motor.setLinkBuilder(new ILinkBuilder() {
            @Override
            public String buildLink(IExpressionContext context, String base, Map<String, Object> parameters) {
                StringBuilder enlace = new StringBuilder(base);
                if (parameters != null && !parameters.isEmpty()) {
                    enlace.append('?');
                    for (Map.Entry<String, Object> parametro : parameters.entrySet()) {
                        if (enlace.charAt(enlace.length() - 1) != '?') {
                            enlace.append('&');
                        }
                        enlace.append(parametro.getKey()).append('=').append(parametro.getValue());
                    }
                }
                return enlace.toString();
            }

            @Override
            public String getName() {
                return "sinContextoWeb";
            }

            @Override
            public Integer getOrder() {
                return 0;
            }
        });
        return motor;
    }

    // Verifica que las plantillas se procesen sin errores de fragmentos o expresiones
    @Test
    void lasPlantillasSeProcesanSinErrores() {
        SpringTemplateEngine motor = crearMotor();

        IServiceParqueadero servicio = Mockito.mock(IServiceParqueadero.class);
        Mockito.when(servicio.valorAcumulado(Mockito.any())).thenReturn(7000.0);
        Mockito.when(servicio.calcularHoras(Mockito.any())).thenReturn(2);

        ModelVehiculo moto = new ModelVehiculo("Moto", "ABC12A", "8:30", 8);
        ModelVehiculo carro = new ModelVehiculo("Carro", "XYZ789", "9:15", 9);
        ModelContadores contadores = new ModelContadores(1, 1, 5, 10);

        Context contextoLista = new Context();
        contextoLista.setVariable("lista", List.of(moto, carro));
        contextoLista.setVariable("contadores", contadores);
        contextoLista.setVariable("servicio", servicio);
        contextoLista.setVariable("vehiculo", new ModelVehiculo());
        motor.process("listavehiculos", contextoLista);

        ModelTicket ticket = new ModelTicket("ABC12A", "Moto", "8:30", 3, true, false,
                10500, 2000, 12500, 15000, 2500, LocalDateTime.now());
        Context contextoTicket = new Context();
        contextoTicket.setVariable("ticket", ticket);
        motor.process("ticket-pdf", contextoTicket);

        motor.process("fragments", new Context());
    }

    // Verifica que flying-saucer genere un PDF valido desde la plantilla del ticket
    @Test
    void elTicketSeConvierteEnPdf() {
        SpringTemplateEngine motor = crearMotor();

        ModelTicket ticket = new ModelTicket("ABC12A", "Moto", "8:30", 3, true, false,
                10500, 2000, 12500, 15000, 2500, LocalDateTime.now());
        Context contexto = new Context();
        contexto.setVariable("ticket", ticket);
        String html = motor.process("ticket-pdf", contexto);

        byte[] pdf = new ServicePdf().generarPdf(html);
        String cabecera = new String(pdf, 0, 4, java.nio.charset.StandardCharsets.US_ASCII);
        assertTrue(pdf.length > 1000, "El PDF generado deberia tener contenido");
        assertTrue(cabecera.equals("%PDF"), "El archivo debe comenzar con la cabecera %PDF");
    }
}
