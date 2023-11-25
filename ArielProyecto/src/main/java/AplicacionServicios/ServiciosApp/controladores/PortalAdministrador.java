package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Resenia;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import AplicacionServicios.ServiciosApp.enumeraciones.ProfesionExtra;
import AplicacionServicios.ServiciosApp.enumeraciones.Rol;
import AplicacionServicios.ServiciosApp.repositorios.ProveedorRepositorio;
import AplicacionServicios.ServiciosApp.servicios.ProveedorServicio;
import AplicacionServicios.ServiciosApp.servicios.ReseniaServicio;
import AplicacionServicios.ServiciosApp.servicios.UsuarioServicio;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class PortalAdministrador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private ReseniaServicio reseniaServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "dashboard.html";
    }

    @GetMapping("/listarUsuarios")
    public String listarUsuarios(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuario();

        modelo.addAttribute("usuarios", usuarios);
        return "usuario_list.html";
    }

    @GetMapping("/listarProveedores")
    public String listarProveedores(ModelMap modelo) {
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);

        return "proveedor_list.html";
    }

    @GetMapping("/resenia/{id}")
    public String listarResenias(ModelMap modelo, @PathVariable String id) {

        List<Resenia> resenias = reseniaServicio.buscarPorProveedorId(id);
        modelo.addAttribute("resenias", resenias);
        return "resenia_list.html";

    }
    //USUARIO
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
            return "usuario_list.html";
        }

    }

    @PostMapping("/usuarioAproveedor/{id}")
    public String cambioRol(@PathVariable String id, @RequestParam(required = false) String error, ModelMap modelo, @RequestParam(required = false) String profesion, @RequestParam(required = false) String profesion2, @RequestParam(required = false) Long telefono) {
        try {
            usuarioServicio.clienteAProveedor(id, profesion, profesion2, telefono);
          
            return "usuario_list.html";
        } catch (Exception e) {
            modelo.put("error", error);
            return "usuario_a_proveedor.html";
        }
    }
    //PROVEEDOR
    @GetMapping("/cambiarRolProveedor/{id}")
    public String cambiarRolProveedor(@PathVariable String id, ModelMap modelo) {
      
        try {
            proveedorServicio.proveedorACliente(id);
            modelo.put("exito", "cambio exitoso");
            return "proveedor_perfil.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
          return "proveedor_perfil.html";
        }
    }
    
    
    
}
