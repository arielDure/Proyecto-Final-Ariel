/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Resenia;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import AplicacionServicios.ServiciosApp.enumeraciones.ProfesionExtra;
import AplicacionServicios.ServiciosApp.enumeraciones.Rol;
import AplicacionServicios.ServiciosApp.enumeraciones.Sexo;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.repositorios.ProveedorRepositorio;
import AplicacionServicios.ServiciosApp.servicios.ContratoServicio;
import AplicacionServicios.ServiciosApp.servicios.ProveedorServicio;
import AplicacionServicios.ServiciosApp.servicios.ReseniaServicio;
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

/**
 *
 * @author tobia
 */
@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private ReseniaServicio reseniaServicio;

    @Autowired
    private ContratoServicio contratoServicio;

    @PostMapping("/registroProveedor")
    public String registroProveedor(MultipartFile archivo, @RequestParam String nombre,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam(required = false) Long telefono,
            @RequestParam Profesion profesion1, @RequestParam ProfesionExtra profesion2, @RequestParam String presentacion, @RequestParam Sexo sexo, ModelMap modelo) {

        try {
            proveedorServicio.crearProveedor(archivo, nombre, email, password, password2, profesion1, profesion2, telefono, presentacion, sexo);

            modelo.put("exito", "Proveedor registrado correctamente");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            List<Sexo> sexos = Arrays.asList(Sexo.values());
            modelo.put("sexos", sexos);
            List<Profesion> profesiones = Arrays.asList(Profesion.values());
            modelo.put("profesiones", profesiones);
            List<ProfesionExtra> profesionesExtra = Arrays.asList(ProfesionExtra.values());
            modelo.put("profesionesExtra", profesionesExtra);
            return "registro.html";
        }
        return "redirect:/login";
    }

    @GetMapping("/actualizarProveedor/{id}")
    public String actualizarProveedor(@PathVariable String id, ModelMap modelo) {
        List<Sexo> sexos = Arrays.asList(Sexo.values());
        modelo.put("sexos", sexos);
        List<Profesion> profesiones = Arrays.asList(Profesion.values());
        modelo.put("profesiones", profesiones);
        List<ProfesionExtra> profesionesExtra = Arrays.asList(ProfesionExtra.values());
        modelo.put("profesionesExtra", profesionesExtra);
        Proveedor proveedor = proveedorRepositorio.getOne(id);
        modelo.put("proveedor", proveedor);

        return "proveedor_modificar.html";
    }

    @PostMapping("/actualizandoProveedor/{idProveedor}")
    public String actualizandoProveedor(@RequestParam MultipartFile archivo, @PathVariable String idProveedor, @RequestParam String nombre,
            @RequestParam String email, @RequestParam String password, HttpSession session,
            @RequestParam String password2, @RequestParam Profesion profesion, @RequestParam String presentacion, @RequestParam String descripcion,
            @RequestParam(required = false) ProfesionExtra profesion2, @RequestParam Long telefono, @RequestParam(required = false) Sexo sexo, ModelMap modelo) {
        Usuario logeado = (Usuario) session.getAttribute("usuariosession");
        try {
            proveedorServicio.actualizarProovedor(archivo, idProveedor, nombre, email, password, password2, profesion, profesion2, telefono, presentacion, sexo, descripcion);

        } catch (MiExcepcion ex) {

            modelo.put("error", ex.getMessage());
            List<Sexo> sexos = Arrays.asList(Sexo.values());
            modelo.put("sexos", sexos);
            List<Profesion> profesiones = Arrays.asList(Profesion.values());
            modelo.put("profesiones", profesiones);
            List<ProfesionExtra> profesionesExtra = Arrays.asList(ProfesionExtra.values());
            modelo.put("profesionesExtra", profesionesExtra);
            modelo.put("proveedor", proveedorServicio.getOne(idProveedor));
            return "proveedor_modificar.html";
        }

        if (logeado.getRol().toString().equals(Rol.ADMIN)) {
            return "redirect:../../admin/listarProveedores";
        }
        modelo.put("exito", "actualizado con exito");
        return "redirect:/inicio";

    }

    @GetMapping("/cambiarRol/{id}")
    public String cambiarRol(@PathVariable String id, ModelMap modelo) {

        try {
            proveedorServicio.proveedorACliente(id);

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "proveedor_perfil.html";
        }
        modelo.put("exito", "cambio exitoso");
        return "redirect:../../logout";  // cuando este el perfil de usuario cambiar 
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/perfilProveedor/{id}")
    public String perfil(ModelMap modelo, HttpSession session, @PathVariable String id) {

        List<Resenia> resenias = reseniaServicio.buscarPorProveedorId(id);
        modelo.put("resenias", resenias);
        Proveedor proveedor = proveedorServicio.getOne(id);
        modelo.put("proveedor", proveedor);
        return "proveedor_perfil.html";

    }

    @GetMapping("/listarProveedores/{profesion}")
    public String mostrarPorProfesion(String profesion, ModelMap modelo) {

        try {
            proveedorServicio.listarProveedores(null);
            modelo.put("profesion", profesion);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "proveedor_list.html";
        }
        return "proveedor_list.html";
    }

}
