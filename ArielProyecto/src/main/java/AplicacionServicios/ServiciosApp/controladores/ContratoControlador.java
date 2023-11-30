/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.repositorios.ContratoRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.UsuarioRepositorio;
import AplicacionServicios.ServiciosApp.servicios.ContratoServicio;
import AplicacionServicios.ServiciosApp.servicios.ProveedorServicio;
import AplicacionServicios.ServiciosApp.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
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
    public String crearContrato(ModelMap modelo, HttpSession session,@PathVariable String id) {
        Usuario logeado = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", logeado);
        Proveedor proveedor = proveedorServicio.getOne(id);
        modelo.put("proveedor", proveedor);
        
       
         
        return "usuario_requisitos.html";
    }


    @PostMapping("/requisitos/{id}")
    public String enviarContrato(@PathVariable String id,@RequestParam String requisitos, @RequestParam String idUsuario) throws Exception {
        try {
            System.out.println("Entre");
            System.out.println("id de usuario" + idUsuario);
            System.out.println("id de proveedor" + id);
               
               
            Usuario usuario = usuarioServicio.getOne(idUsuario);
            Proveedor proveedor = proveedorServicio.getOne(id);
            
            contratoServicio.crearContrato(requisitos, usuario, proveedor);
            return "redirect:../../inicio";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:../../inicio";
        }
    }
    


    @GetMapping("/presupuestoProveedor/{id}")
    public String planillaPresupuesto(ModelMap modelo, @PathVariable String id){
        
        Contrato contrato = contratoServicio.getOne(id);
        modelo.put("contrato", contrato);
        
        return "proveedor_presupuesto.html";
    }
    
    //METODO PARA QUE EL PROVEEDOR MIRE SUS PEDIDOS PENDIENTES
    @GetMapping("/pedidosPendientes")
    public String perfil(HttpSession session,ModelMap modelo) {
    Usuario logeado = (Usuario) session.getAttribute("usuariosession");
    List<Contrato> contratos = contratoServicio.listaContratoProveedor(logeado.getId());
    modelo.put("contratos", contratos);
    
    return "pedidos_list.html";
    }

//
//   @PostMapping("/presupuesto")
//    public String enviarPresupuesto(@RequestParam String id, @RequestParam Double precio, @RequestParam String horasAprox, @RequestParam Date inicioDelTrabajo, @RequestParam String accion ) throws Exception{
//       try{
//           if(accion.equals("enviar")){
//               contratoServicio.respuestaProveedorAlContrato(id, precio,  horasAprox, inicioDelTrabajo);
//               return "inicio.html";
//           }else if(accion.equals("rechazar")){
//                contratoServicio.cancelarContrato(id);
//                return "inicio.html";
//           }
//
//       }catch (Exception ex){
//           return "inicio.html";
//
//       }
//    return null;
//   
    
}



