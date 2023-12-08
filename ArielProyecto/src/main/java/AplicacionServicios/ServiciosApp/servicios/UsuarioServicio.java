/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.servicios;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.Imagen;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import AplicacionServicios.ServiciosApp.enumeraciones.ProfesionExtra;
import AplicacionServicios.ServiciosApp.enumeraciones.Rol;
import AplicacionServicios.ServiciosApp.enumeraciones.Sexo;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.repositorios.ContratoArchivadoRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.ContratoRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.ProveedorRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author tobia
 */
@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;
    
    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Autowired
    private ContratoArchivadoRepositorio contratoArchivadoRepositorio;

    @Autowired
    private ContratoArchivadoServicio contratoArchivadoServicio;

    @Transactional
    public void crearUsuario(MultipartFile archivo, String nombre, String email, String password, String password2, Sexo sexo) throws MiExcepcion {

        validarCrearUsuario(archivo, nombre, email, password, password2);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USUARIO);
        usuario.setActivo(true);
        usuario.setSexo(sexo);

        Imagen imagen = null;

        imagen = imagenServicio.guardar(archivo, usuario.getSexo().toString(), usuario.getRol().toString());

        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);

    }

    public void validarCrearUsuario(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiExcepcion {

        if (archivo == null) {
            throw new MiExcepcion("El archivo no puede estar vacío");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("El nombre no puede estar vacío, o es incorrecto");
        }
        if (email.isEmpty() || email == null) {
            throw new MiExcepcion("El email no puede estar vacío");
        }
        if (password.isEmpty() || password == null) {
            throw new MiExcepcion("La contraseña no puede estar vacía.");
        }
        if (password.length() <= 7) {
            throw new MiExcepcion("La contraseña debe tener mas de 8 carácteres.");
        }
        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas no coinciden");
        }
        if (email.isEmpty()) {
            throw new MiExcepcion("El email esta vacio");
        }
    }

    public String buscarPorEmail(String email) throws MiExcepcion {

        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);
        if (usuario != null) {
            return usuario.getEmail();
        } else {
            return "";
        }

    }
  
    
    
    @Transactional
    public void actualizarUsuario(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2, Sexo sexo) throws MiExcepcion {

        validarActualizarUsuario(archivo, nombre, email, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(Rol.USUARIO);
            usuario.setSexo(sexo);

            String idImagen = null;

            
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);
        }
    }
    
      public void validarActualizarUsuario(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiExcepcion {

        if (nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede estar vacío");
        }
        if (email.isEmpty()) {
            throw new MiExcepcion("El email no puede estar vacío");
        }
        if (password.isEmpty()) {
            throw new MiExcepcion("La contraseña no puede estar vacía.");
        }
        if (password.length() <= 7) {
            throw new MiExcepcion("La contraseña debe tener mas de 8 carácteres.");
        }
        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas no coinciden");
        }
    }

    public List<Usuario> listarUsuario() {
        List<Usuario> usuariosAux = new ArrayList();
        List<Usuario> usuarios = new ArrayList();
         usuariosAux = usuarioRepositorio.findAll();
        
        for (Usuario usuario : usuariosAux) {
            
            if (usuario.getRol().equals(Rol.USUARIO)) {
               usuarios.add(usuario);
            }
            
        }
        return usuarios;
    }

    @Transactional
    public void borrarUsuarioPorID(String idUsuario) {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            usuarioRepositorio.deleteById(idUsuario);
        }

    }

    @Transactional
    public void clienteAProveedor(String idUsuario, String profesion, String profesion2, Long telefono, Sexo sexo) throws MiExcepcion{

//        validarClienteAProveedor(telefono, profesion, profesion2);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            Proveedor proveedor = new Proveedor();

            proveedor.setNombre(usuario.getNombre());
            proveedor.setId(usuario.getId());
            proveedor.setEmail(usuario.getEmail());
            proveedor.setPassword(usuario.getPassword());
            proveedor.setActivo(true);
            proveedor.setRol(Rol.PROVEEDOR);
            proveedor.setImagen(usuario.getImagen());
            proveedor.setTelefono(telefono);
            proveedor.setProfesion1(Profesion.valueOf(profesion));
            proveedor.setProfesion2(ProfesionExtra.valueOf(profesion2));
            proveedor.setSexo(Sexo.MASCULINO);
            borrarUsuarioPorID(idUsuario);
            
            List<Contrato> contratos = contratoRepositorio.buscarPorUsuarioId(idUsuario);
            for (Contrato aux : contratos) {
                contratoArchivadoServicio.crearContratoArchivado(aux.getId());

            }
            proveedorRepositorio.save(proveedor);

    }
    }
    
    public void validarClienteAProveedor(Long telefono, String profesion, String profesion2) throws MiExcepcion {
        if (telefono == null) {
            throw new MiExcepcion("Debe proveer un numero de telefono celular.");
        }
        if (telefono.toString().length() >= 15){
            throw new MiExcepcion("El telefono no puede tener más de 15 caracteres.");
        }
        if (profesion.equals(profesion2)){
            throw new MiExcepcion("Las profesiones no pueden coincidir");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        }
        return null;

    }
    
     public Usuario getOne(String id){
       return usuarioRepositorio.getOne(id);
    }
     
    @Transactional
    public Usuario invernarCuenta(String idUsuario){
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setActivo(false);
            return usuario;
        }
        return null;
    }
    
    
}
