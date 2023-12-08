/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import AplicacionServicios.ServiciosApp.enumeraciones.ProfesionExtra;
import AplicacionServicios.ServiciosApp.enumeraciones.Sexo;
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
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        String profesionFiltro = null;
         List<Proveedor> proveedores = proveedorServicio.listarProveedores(profesionFiltro);
         modelo.addAttribute("proveedores", proveedores);
         
         return "inicio.html";
    }
    
   @GetMapping("/registrar")
    public String registrar(ModelMap modelo){

        try {
         List<Sexo> sexos = Arrays.asList(Sexo.values());
        modelo.put("sexos", sexos);
        List<Profesion> profesiones = Arrays.asList(Profesion.values());
        modelo.put("profesiones", profesiones);
        List<ProfesionExtra> profesionesExtra = Arrays.asList(ProfesionExtra.values());
        modelo.put("profesionesExtra", profesionesExtra);
        } catch (Exception e) {
            modelo.put("error", "Usuario o contraseña invalida!");
        }
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
    
    @GetMapping("/listarProveedores")
    public String listaProveedores(ModelMap modelo,@Param("profesionFiltro") String profesionFiltro){
         List<Proveedor> proveedores = proveedorServicio.listarProveedores(profesionFiltro);
         List<Profesion> profesiones = Arrays.asList(Profesion.values());
         modelo.put("profesiones", profesiones);
         modelo.addAttribute("profesionFiltro" , profesionFiltro);
         modelo.addAttribute("proveedores", proveedores);
         return "lista_proveedores.html";
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
