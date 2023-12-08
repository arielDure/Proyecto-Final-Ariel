/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.servicios.ContratoServicio;
import AplicacionServicios.ServiciosApp.servicios.ProveedorServicio;
import AplicacionServicios.ServiciosApp.servicios.ReseniaServicio;
import AplicacionServicios.ServiciosApp.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private ContratoServicio contratoServicio;

    @PostMapping("/crearResenia/{id}")
    public String crearResenia(@RequestParam String idUsuario, @RequestParam String idProveedor, @RequestParam String cuerpo, @RequestParam String calificacion, ModelMap modelo, @PathVariable String id) {

        Usuario usuario = usuarioServicio.getOne(idUsuario);
        Contrato contrato = contratoServicio.getOne(id);
        Proveedor proveedor = proveedorServicio.getOne(contrato.getProveedor().getId());

        try {
            reseniaServicio.crearResenia(usuario, proveedor, cuerpo, Integer.parseInt(calificacion));
            proveedorServicio.calcularPromedio(contrato.getProveedor().getId());
            reseniaServicio.cambiarEstadoDeTieneResenia(id);

            return "redirect:/inicio";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/inicio";
        }

    }

    @GetMapping("/cancelarResenia/{id}")
    public String cancelarResenia(@PathVariable String id) {
        Contrato contrato = contratoServicio.getOne(id);
        try {
            reseniaServicio.cambiarEstadoDeTieneResenia(id);
            return "redirect:../contrato/pedidosPendientes";
        } catch (Exception e) {
            return "redirect:/inicio";
        }
    }

    @PostMapping("/actualizarResenia/{id}")
    public String actualizarResenia(@PathVariable String id, @RequestParam String cuerpo, @RequestParam Integer calificacion, @RequestParam Usuario idUsuario, @RequestParam Proveedor idProveedor, ModelMap modelo) {

        try {
            reseniaServicio.actualizarResenia(id, cuerpo, calificacion, idUsuario, idProveedor);
         
            return "redirect:/inicio";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
   
            return "redirect:/inicio";
        }

    }

}
