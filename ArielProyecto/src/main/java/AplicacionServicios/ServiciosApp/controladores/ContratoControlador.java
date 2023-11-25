package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.servicios.ContratoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ariel
 */
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    ContratoServicio contratoServicio;
    
    @GetMapping("/inicio")
    public String inicioContrato(){
        return "usuario_requisitos.html";
    }
}
