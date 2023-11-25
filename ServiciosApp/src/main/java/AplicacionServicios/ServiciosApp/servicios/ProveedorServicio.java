/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.servicios;

import AplicacionServicios.ServiciosApp.entidades.Imagen;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import AplicacionServicios.ServiciosApp.enumeraciones.ProfesionExtra;
import AplicacionServicios.ServiciosApp.enumeraciones.Rol;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
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

/**
 * @author tobia
 */
@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearProveedor(MultipartFile archivo, String nombre, String email, String password, String password2, Profesion profesion1, ProfesionExtra profesion2, Long telefono) throws MiExcepcion {

        validar(archivo, nombre, email, password, password2, telefono);

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombre);
        proveedor.setEmail(email);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setActivo(true);
        proveedor.setProfesion1(profesion1);
        if(profesion2.equals(ProfesionExtra.VACIO)){
            proveedor.setProfesion2(null);
        }else{
            proveedor.setProfesion2(profesion2);
        }
        proveedor.setTelefono(telefono);
        proveedor.setContactos(null);
        Imagen imagen = null;

        imagen = imagenServicio.guardar(archivo);

        proveedor.setImagen(imagen);

        proveedorRepositorio.save(proveedor);

    }

    public void validar(MultipartFile archivo, String nombre, String email, String password, String password2, Long telefono) throws MiExcepcion {

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
            throw new MiExcepcion("Las contraseñas no coinciden.");
        }
        if (telefono == null) {
            throw new MiExcepcion("Debe ingresar su numero de telefono.");
        }

    }

    @Transactional
    public void actualizarProovedor(MultipartFile archivo, String idProveedor, String nombre,
            String email, String password, String password2, Profesion profesion,
            ProfesionExtra profesion2, Long telefono) throws MiExcepcion {
  
        validar(archivo, nombre, email, password, password2, telefono);

   
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(idProveedor);

        if (respuesta.isPresent()) {
 
            Proveedor proveedor = respuesta.get();
            proveedor.setNombre(nombre);
            proveedor.setEmail(email);
            proveedor.setTelefono(telefono);
            proveedor.setProfesion1(profesion);
            proveedor.setProfesion2(profesion2);
            proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
            proveedor.setRol(Rol.PROVEEDOR);
            
            Imagen imagen;
            
            if(archivo.getSize()!=0){
                System.out.println("archivo contiene algo " + archivo.getSize());
             imagen = imagenServicio.actualizar(archivo, proveedor.getImagen().getId());
             proveedor.setImagen(imagen);
            }

            proveedorRepositorio.save(proveedor);
        }
    }


    // Método que devuelve una lista de proveedores - sin especificar la profesión --
    public List<Proveedor> listarProveedores() {
        List<Proveedor> proveedor = new ArrayList<>();
        proveedor = proveedorRepositorio.findAll();
        return proveedor;

    }


    // Método para eliminar un proveedor de la lista
    @Transactional
    public void borrarProovedorPorId(String idProveedor) {
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(idProveedor);

        if (respuesta.isPresent()) {
            proveedorRepositorio.deleteById(idProveedor);
        }

    }

    //  Método que devuelve una lista de proveedores pero discriminando el tipo de servicio que brinda.
    public List<Proveedor> buscarPorProfesion(String profesion1) {
        List<Proveedor> proveedores = new ArrayList<>();
        proveedores = proveedorRepositorio.findAll();
        List<Proveedor> proveedors = new ArrayList<>();
        for (Proveedor elemento : proveedores) {
            if (elemento.getProfesion1().equals(profesion1) || elemento.getProfesion2().equals((profesion1))) {
                proveedors.add(elemento);
            }
        }
        return proveedors;
    }

    @Transactional
    public void proveedorACliente(String idProveedor) throws MiExcepcion{
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(idProveedor);

        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            Usuario usuario = new Usuario();

            usuario.setNombre(proveedor.getNombre());
            usuario.setId(proveedor.getId());
            usuario.setEmail(proveedor.getEmail());
            usuario.setPassword(proveedor.getPassword());
            usuario.setActivo(true);
            usuario.setRol(Rol.USUARIO);

            borrarProovedorPorId(idProveedor);

            usuarioRepositorio.save(usuario);

        }else{
            throw new MiExcepcion("id no encontrada");
        }

    }

     // Método que devuelve una lista  con todas las coincidencias que haya en la base de dato del atributo nombre
      public List<Proveedor> buscarPorNombre(String nombre) {
        List<Proveedor> proveedores = new ArrayList<>();
        proveedores = proveedorRepositorio.findAll();
        List<Proveedor> nombresProveedores = new ArrayList<>();
        for (Proveedor elementos : proveedores) {
            if (elementos.getNombre().equals(nombre)) {
                nombresProveedores.add(elementos);
            }
        }
        return nombresProveedores;
    }
    
         // Método que devuelve proovedor al ingresar el numero de telefono.
    public Proveedor buscarProveedorPorTelefono(Long telefono) {
        return buscarProveedorPorTelefono(telefono);
    }
    
     public Proveedor getOne(String id){
       return proveedorRepositorio.getOne(id);
    }
}
