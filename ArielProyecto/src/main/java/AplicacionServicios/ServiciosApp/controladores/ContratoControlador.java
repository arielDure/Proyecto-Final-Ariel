/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Rol;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.repositorios.ContratoRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.UsuarioRepositorio;
import AplicacionServicios.ServiciosApp.servicios.ContratoServicio;
import AplicacionServicios.ServiciosApp.servicios.ProveedorServicio;
import AplicacionServicios.ServiciosApp.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tobia
 */
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicio contratoServicio;

    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/crearContratoUsuario/{id}")
    public String crearContrato(ModelMap modelo, HttpSession session, @PathVariable String id) {
        Usuario logeado = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", logeado);
        Proveedor proveedor = proveedorServicio.getOne(id);
        modelo.put("proveedor", proveedor);

        return "usuario_requisitos.html";
    }

    @PostMapping("/requisitos/{id}")
    public String enviarContrato(@PathVariable String id, @RequestParam String requisitos, @RequestParam String idUsuario) throws Exception {
        try {

            Usuario usuario = usuarioServicio.getOne(idUsuario);
            Proveedor proveedor = proveedorServicio.getOne(id);

            contratoServicio.crearContrato(requisitos, usuario, proveedor);
            return "redirect:../../inicio";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:../../inicio";
        }
    }

    //METODO PARA QUE EL PROVEEDOR MIRE SUS PEDIDOS PENDIENTES
    @GetMapping("/pedidosPendientes")
    public String perfil(HttpSession session, ModelMap modelo) {
        Usuario logeado = (Usuario) session.getAttribute("usuariosession");

        if (logeado.getRol().equals(Rol.PROVEEDOR)) {
            List<Contrato> contratos = contratoServicio.listaContratoProveedor(logeado.getId());
            modelo.put("contratos", contratos);
            List<Contrato> contratosConPrecio = contratoServicio.listarPorPrecioNull(logeado.getId());
            modelo.put("contratosPrecioNull", contratosConPrecio);
        }
        if (logeado.getRol().equals(Rol.USUARIO)) {
            List<Contrato> contratos = contratoServicio.listaContratoUsuario(logeado.getId());
            modelo.put("contratos", contratos);
        }

        return "pedidos_list.html";
    }

    @GetMapping("/presupuestoProveedor/{id}")
    public String planillaPresupuesto(ModelMap modelo, @PathVariable String id) {

        Contrato contrato = contratoServicio.getOne(id);
        modelo.put("contrato", contrato);

        return "proveedor_presupuesto.html";
    }

    @PostMapping("/presupuesto/{id}")
    public String enviarPresupuesto(@PathVariable String id, @RequestParam Double precio, @RequestParam String horasAprox, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicioDelTrabajo, @RequestParam String accion) throws MiExcepcion {
        System.out.println("este es el date" + inicioDelTrabajo.toString() + inicioDelTrabajo);

        try {
            if (accion.equals("aceptar")) {
                System.out.println("entre al aceptar");
                contratoServicio.envioDeProveedorACliente(id, precio, horasAprox, inicioDelTrabajo);

                return "redirect:../pedidosPendientes";
            } else if (accion.equals("rechazar")) {
                System.out.println("entre al aceptar");
                contratoServicio.cancelarContrato(id);

                return "redirect:../pedidosPendientes";
            }

        } catch (Exception ex) {
            return "inicio.html";

        }
        return null;

    }

    @GetMapping("/contratoSolicitado/{id}")
    public String contratoSolicitado(@PathVariable String id, ModelMap modelo) {
      
            Contrato contrato = contratoServicio.getOne(id);
            modelo.put("contrato", contrato);
            return "aceptar_contrato.html";
     
    }

    @GetMapping("/contratoConfirmacion/{id}")
    public String contratoConfirmacion(@PathVariable String id) {
        boolean aceptar = true;
        contratoServicio.aceptarContrato(id, aceptar);

        return "redirect:../pedidosPendientes";
    }

    @GetMapping("/cancelarContrato/{id}")
    public String cancelarContrato(@PathVariable String id) {
      contratoServicio.cancelarContrato(id);
        return "redirect:../pedidosPendientes";
    }
    
    @GetMapping("/trabajoFinalizado/{id}")
    public String finalizarTrabajo(@PathVariable String id) {
        contratoServicio.finalizarContrato(id);
        return "redirect:../pedidosPendientes";
        
    }

}
