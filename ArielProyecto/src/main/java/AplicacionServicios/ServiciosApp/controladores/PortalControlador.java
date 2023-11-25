/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Resenia;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import AplicacionServicios.ServiciosApp.enumeraciones.ProfesionExtra;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.servicios.ProveedorServicio;
import AplicacionServicios.ServiciosApp.servicios.ReseniaServicio;
import AplicacionServicios.ServiciosApp.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PathVariable;


/**
 *
 * @author tobia
 */

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
     @Autowired
    private ProveedorServicio proveedorServicio;
     
     @Autowired
     private ReseniaServicio reseniaServicio;
     
  
    
    //Agregue lista de proveedores para mostrar en el index (Ariel Duré)
    @GetMapping("/")
    public String index(ModelMap modelo){
         List<Proveedor> proveedores = proveedorServicio.listarProveedores();
         modelo.addAttribute("proveedores", proveedores);
         return "inicio.html";
    }
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        List<Profesion> profesiones = Arrays.asList(Profesion.values());
        modelo.put("profesiones", profesiones);
        List<ProfesionExtra> profesionesExtra = Arrays.asList(ProfesionExtra.values());
        modelo.put("profesionesExtra", profesionesExtra);
        return "registro.html";
    }
    
   

    @GetMapping("/login")
    public String login(@RequestParam (required = false) String error,ModelMap modelo){
        if(error!=null){
            modelo.put("error", "Usuario o contraseña invalida!");
        }
        
        return"login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        
       Usuario logeado = (Usuario) session.getAttribute("usuariosession");
       if(logeado.getRol().toString().equals("ADMIN")){
           return "redirect:/admin/dashboard";
       }
        return "redirect:/";

        
    }
    
    
    
    
    
    
    
    
    
    
    

//    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN','ROLE_PROVEEDOR')")
//    @GetMapping("/perfil")
//    public String perfil(ModelMap modelo,HttpSession session){
//        Usuario usuario =(Usuario) session.getAttribute("usuariosession");
//        
//        modelo.put("usuario",usuario);
//        return "usuario_modificar.html";
//        
//    }
    
    
}
