/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.servicios.ReseniaServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tobia
 */

@Controller
@RequestMapping("/resenia")
public class ReseniaControlador {
    
    @Autowired
    private ReseniaServicio reseniaServicio;
    
    
    @PostMapping("/crearResenia")
    public String crearResenia(@RequestParam Usuario usuario,@RequestParam Proveedor proveedor,@RequestParam String cuerpo,@RequestParam Integer calificacion,ModelMap modelo){
    
        try {
            reseniaServicio.crearResenia(usuario, proveedor, cuerpo, calificacion);
            //TODAVIA NO SE A DONDE REDIRIGIRLO
            return "redirect:/inicio";
        } catch (MiExcepcion ex) {
            //TODAVIA NO SE A DONDE REDIRIGIRLO
            modelo.put("error", ex.getMessage());
            return "redirect:/inicio";
        }
        
    }
    
    @PostMapping("/actualizarResenia/{id}")
    public String actualizarResenia(@PathVariable String id, @RequestParam String cuerpo, @RequestParam Integer calificacion, @RequestParam Usuario idUsuario,@RequestParam Proveedor idProveedor,ModelMap modelo){
        
        try {
            reseniaServicio.actualizarResenia(id, cuerpo, calificacion, idUsuario, idProveedor);
            //TODAVIA NO SE A DONDE REDIRIGIRLO
            return "redirect:/inicio";
        } catch (MiExcepcion ex) {
             modelo.put("error", ex.getMessage());
             //TODAVIA NO SE A DONDE REDIRIGIRLO
            return "redirect:/inicio";
        }
        
    }
    
}
