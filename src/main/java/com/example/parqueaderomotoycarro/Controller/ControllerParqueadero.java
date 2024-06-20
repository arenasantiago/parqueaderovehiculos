package com.example.parqueaderomotoycarro.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.parqueaderomotoycarro.Model.ModelVehiculo;
import com.example.parqueaderomotoycarro.Service.IServiceParqueadero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
public class ControllerParqueadero {

    @Autowired 
    private IServiceParqueadero serviciosParqueadero;

    //Mostrar la pagina principal
    @GetMapping("/")
    public String mostrarPag(Model model) {
        boolean desapareceForm = true;
        model.addAttribute("vehiculo", new ModelVehiculo());
        model.addAttribute("desapareceForm", desapareceForm);
        return "index";
    }
    @PostMapping("/ingresar")
    public String agregarVehiculo(@ModelAttribute("vehiculo") ModelVehiculo vehiculo, Model model) {
        boolean desapareceForm = false;
        String resultado = serviciosParqueadero.ingresarVehiculo(vehiculo);
        Long idVehiculo = null;
        ModelVehiculo vehiculoMandado = new ModelVehiculo();
        String tipoVehiculo = "";
        for (ModelVehiculo aVehiculo : serviciosParqueadero.traerVehiculos()) {
            if(aVehiculo.getPlaca().equals(vehiculo.getPlaca())){
                idVehiculo = aVehiculo.getId();
                vehiculoMandado = serviciosParqueadero.encontrarVehiculo(idVehiculo);
                tipoVehiculo = vehiculoMandado.getTipoVehiculo();
                break;
            }
        }
        model.addAttribute("vehiculo", new ModelVehiculo());
        model.addAttribute("tipovehiculo", tipoVehiculo);
        model.addAttribute("id", idVehiculo);
        model.addAttribute("Moto", "Moto");
        model.addAttribute("Carro", "Carro");
        model.addAttribute("resultado", resultado);
        model.addAttribute("lista", serviciosParqueadero.traerVehiculos());
        model.addAttribute("desapareceForm", desapareceForm);
        return "index";
    }
    
    @PostMapping("/servicio/{id}")
    public String servicio(@ModelAttribute("vehiculo") ModelVehiculo vehiculo,@PathVariable Long id, Model model) {
        ModelVehiculo vehi = serviciosParqueadero.encontrarVehiculo(id);
        String resultado = "";
        if (vehi.getTipoVehiculo().equals("Moto")) {
            resultado = serviciosParqueadero.servicios(id, vehiculo.getCasco());
        }else if(vehi.getTipoVehiculo().equals("Carro")){
            resultado = serviciosParqueadero.servicios(id, vehiculo.getLavado());
        }
        model.addAttribute("resultadoServicio", resultado);
        model.addAttribute("lista", serviciosParqueadero.traerVehiculos());
        return "redirect:/";
    }
    
    //Trae la lista de los vehiculos
    @GetMapping("/lista")
    public String traerLista(Model model) {
        model.addAttribute("lista", serviciosParqueadero.traerVehiculos());
        return "listavehiculos";
    }
    @PostMapping("/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Long id, Model model) {
        String resultado = serviciosParqueadero.eliminarVehiculo(id);
        model.addAttribute("resultado", resultado);
        model.addAttribute("lista", serviciosParqueadero.traerVehiculos());
        return "listavehiculos";
    }
    @GetMapping("/modificar/{id}")
    public String modificarPlaca(@PathVariable Long id, Model model) {
        boolean desapareceForm = false;
        model.addAttribute("vehiculo", new ModelVehiculo());
        ModelVehiculo vehicu = serviciosParqueadero.encontrarVehiculo(id);
        String placaAModificar = vehicu.getPlaca();
        model.addAttribute("placaAModificar", placaAModificar);
        model.addAttribute("id", id);
        model.addAttribute("desapareceForm", desapareceForm);
        return "modificar";
    }
    @PostMapping(path = ("/modificar/modificando/{id}"))
    public String modificar(@PathVariable Long id, @ModelAttribute("vehiculo") ModelVehiculo vehiculo, Model model) {
        ModelVehiculo vehi = serviciosParqueadero.encontrarVehiculo(id);
        String resultado = serviciosParqueadero.modificarVehiculo(id, vehiculo);
        if (!(resultado.equals("Se ha modificado con éxito!"))) {
            model.addAttribute("resultado", resultado);
            return "listavehiculos";
        }else{
        String placaAModificar = vehi.getPlaca();
        boolean desapareceForm = true;
        model.addAttribute("desapareceForm", desapareceForm);
        model.addAttribute("resultado", resultado);
        model.addAttribute("vehiculo", new ModelVehiculo());
        model.addAttribute("placaAModificar", placaAModificar);
        model.addAttribute("tipovehiculo", vehi.getTipoVehiculo());
        model.addAttribute("horaOriginal", vehi.getHora());
        return "modificar";
        }
    }
    @PostMapping("/servicioModificado/{id}")
    public String servicioModificando(@PathVariable Long id, @ModelAttribute("vehiculo") ModelVehiculo vehiculo, Model model) {
        ModelVehiculo vehi = serviciosParqueadero.encontrarVehiculo(id);
        String resultado = "";
        if (vehi.getTipoVehiculo().equals("Moto")) {
            resultado = serviciosParqueadero.servicios(id, vehiculo.getCasco());
        }else if(vehi.getTipoVehiculo().equals("Carro")){
            resultado = serviciosParqueadero.servicios(id, vehiculo.getLavado());
        }
        model.addAttribute("resultadoServicio", resultado);
        model.addAttribute("lista", serviciosParqueadero.traerVehiculos());
        return "listavehiculos";
    }
    @GetMapping("/cobrar/{id}")
    public String cobro(@PathVariable Long id, Model model) {
        ModelVehiculo vehiculo = serviciosParqueadero.encontrarVehiculo(id);
        double pago = serviciosParqueadero.debePagar(id);
        model.addAttribute("vehiculo", new ModelVehiculo());
        model.addAttribute("debePagar", pago);
        model.addAttribute("placaOriginal", vehiculo.getPlaca());
        model.addAttribute("id", vehiculo.getId());
        return "cobrar";
    }
    @PostMapping("/cobrando/{id}")
    public String cobrando(@PathVariable Long id, @ModelAttribute("vehiculo")ModelVehiculo vehiculo, Model model) {
        String resultCobro = serviciosParqueadero.cobro(id, vehiculo.getPago());
        model.addAttribute("resultCobro", resultCobro);
        return "listavehiculos";
    }
    
    
    
    
}
