/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import AplicacionServicios.ServiciosApp.enumeraciones.ProfesionExtra;
import AplicacionServicios.ServiciosApp.enumeraciones.Sexo;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.servicios.ContratoServicio;
import AplicacionServicios.ServiciosApp.servicios.ProveedorServicio;
import AplicacionServicios.ServiciosApp.servicios.UsuarioServicio;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;
    
    @Autowired
    private ContratoServicio contratoServicio;


   @PostMapping("/registroUsuario")
    public String registroUsuario(MultipartFile archivo, @RequestParam String nombre,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo, Sexo sexo) {

        try {

            usuarioServicio.crearUsuario(archivo, nombre, email, password, password2, sexo);
            modelo.put("exito", "Usuario registrado correctamente!");

        } catch (MiExcepcion ex) {

            modelo.put("error", ex.getMessage());
            List<Sexo> sexos = Arrays.asList(Sexo.values());
            modelo.put("sexos", sexos);
            modelo.put("nombre",nombre);
            modelo.put("email",email);
            return "registro.html";
        }
        return "redirect:/login";

    }

    @GetMapping("/actualizarUsuario/{id}")
    public String actualizarUsuario(@PathVariable String id, ModelMap modelo) {
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);
        List<Sexo> sexos = Arrays.asList(Sexo.values());
        modelo.put("sexos", sexos);
        return "usuario_modificar.html";

    }


    @PostMapping("/actualizandoUsuario/{idUsuario}")
    public String actualizandoUsuario(@RequestParam MultipartFile archivo, @PathVariable String idUsuario, @RequestParam String nombre,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, HttpSession session, ModelMap modelo, Sexo sexo) {
        Usuario logeado = (Usuario) session.getAttribute("usuariosession");
        try {
            usuarioServicio.actualizarUsuario(archivo, idUsuario, nombre, email, password, password2, sexo);
        } catch (MiExcepcion ex) {
            List<Sexo> sexos = Arrays.asList(Sexo.values());
            modelo.put("sexos", sexos);
            modelo.put("error", ex.getMessage());
            return "usuario_modificar.html";
        }
        modelo.put("exito", "actualizado con exito");
        if (logeado.getRol().toString().equals("ADMIN")) {
            return "redirect:../../admin/listarUsuarios";
        }

        return "redirect:/inicio";

    }
    
    
    @GetMapping("/cambiarRol/{id}")
    public String cambiarRol(@RequestParam(required = false) String error, ModelMap modelo, @PathVariable String id) {

        try {
            Usuario usuario = usuarioServicio.getOne(id);
            List<Profesion> profesiones = Arrays.asList(Profesion.values());
            modelo.put("profesiones", profesiones);
            List<ProfesionExtra> profesionesExtra = Arrays.asList(ProfesionExtra.values());
            modelo.put("profesionesExtra", profesionesExtra);
            modelo.put("usuario", usuario);
            return "usuario_a_proveedor.html";
        } catch (Exception e) {
            modelo.put("error", error);
            return "redirect:../perfil/{id}";
        }

    }
    
   

    @PostMapping("/usuarioAproveedor/{id}")
    public String cambioRol(@PathVariable String id, @RequestParam(required = false) String error, ModelMap modelo, @RequestParam(required = false) String profesion, @RequestParam(required = false) String profesion2, @RequestParam Long telefono, @RequestParam(required = false) Sexo sexo) {
        try {
            
            usuarioServicio.clienteAProveedor(id, profesion, profesion2, telefono, sexo);
            return "redirect:../../logout";

        } catch (Exception e) {
            modelo.put("error", error);
            Usuario usuario = usuarioServicio.getOne(id);
            List<Profesion> profesiones = Arrays.asList(Profesion.values());
            modelo.put("profesiones", profesiones);
            List<ProfesionExtra> profesionesExtra = Arrays.asList(ProfesionExtra.values());
            modelo.put("profesionesExtra", profesionesExtra);
            modelo.put("usuario", usuario);
            return "usuario_a_proveedor.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/perfil/{id}")
    public String perfil(ModelMap modelo, HttpSession session, @PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);

        return "usuario_perfil.html";

    }

    @GetMapping("/cargarResenia/{id}")
    public String cargarResenia(@PathVariable String id, ModelMap modelo, HttpSession session) {
        Contrato contrato = contratoServicio.getOne(id);
        modelo.put("contrato", contrato);
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        Proveedor proveedor = proveedorServicio.getOne(contrato.getProveedor().getId());
        modelo.put("proveedor", proveedor);
        return "resenia.html";
    }

}
