/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.servicios;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.Imagen;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Resenia;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import AplicacionServicios.ServiciosApp.enumeraciones.ProfesionExtra;
import AplicacionServicios.ServiciosApp.enumeraciones.Rol;
import AplicacionServicios.ServiciosApp.enumeraciones.Sexo;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.repositorios.ContratoRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.ProveedorRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.ReseniaRepositorio;
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
    private ContratoRepositorio contratoRepositorio;

    @Autowired
    private ReseniaRepositorio reseniaRepositorio;

    @Autowired
    private ContratoArchivadoServicio contratoArchivadoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearProveedor(MultipartFile archivo, String nombre, String email, String password, String password2, Profesion profesion1, ProfesionExtra profesion2, Long telefono,String presentacion,Sexo sexo) throws MiExcepcion {


        validarCrearProveedor(archivo, nombre, email, password, password2, telefono, presentacion);

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombre);
        proveedor.setEmail(email);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setActivo(true);
        proveedor.setProfesion1(profesion1);
        proveedor.setPresentacion(presentacion);
        proveedor.setContactos(0);
        if(profesion2.equals(ProfesionExtra.NO)){
            proveedor.setProfesion2(null);
        }else{
            proveedor.setProfesion2(profesion2);
        }
        proveedor.setTelefono(telefono);
        proveedor.setSexo(sexo);
        Imagen imagen = null;

        imagen = imagenServicio.guardar(archivo, proveedor.getSexo().toString(), proveedor.getRol().toString());

        proveedor.setImagen(imagen);

        proveedorRepositorio.save(proveedor);

    }

    public void validarCrearProveedor(MultipartFile archivo, String nombre, String email, String password, String password2, Long telefono,String presentacion) throws MiExcepcion {

        if (nombre.isEmpty() || nombre.length() >=28) {
            throw new MiExcepcion("El nombre no puede estar vacío, o es incorrecto.");
        }
        if (email.isEmpty()) {
            throw new MiExcepcion("El email no puede estar vacío.");
        }
        if (password.isEmpty()) {
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
        if (presentacion.isEmpty()) {
            throw new MiExcepcion("la presentacion debe contener algo.");
        }
        if (presentacion.length() >= 180){
            throw new MiExcepcion("La presentacion no puede tener mas de 26 caracteres.");
        }
    }

    @Transactional
    public void actualizarProovedor(MultipartFile archivo, String idProveedor, String nombre,
            String email, String password, String password2,Profesion profesion,
            ProfesionExtra profesion2, Long telefono,String presentacion,Sexo sexo,String descripcion) throws MiExcepcion {
  
        validarActualizarProveedor(archivo, nombre, email, profesion, profesion2, password, password2, telefono);

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(idProveedor);

        if (respuesta.isPresent()) {
 
            Proveedor proveedor = respuesta.get();
            proveedor.setNombre(nombre);
            proveedor.setEmail(email);
            proveedor.setTelefono(telefono);
            proveedor.setProfesion1(profesion);
            proveedor.setProfesion2(profesion2);
            proveedor.setPresentacion(presentacion);
            proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
            proveedor.setRol(Rol.PROVEEDOR);
            proveedor.setSexo(sexo);
            proveedor.setDescripcion(descripcion);
            
            Imagen imagen;
            
            if(archivo.getSize()!=0){
                System.out.println("archivo contiene algo " + archivo.getSize());
             imagen = imagenServicio.actualizar(archivo, proveedor.getImagen().getId());
             proveedor.setImagen(imagen);
            }

            proveedorRepositorio.save(proveedor);
        }
    }

    public void validarActualizarProveedor(MultipartFile archivo, String nombre, String email, Profesion profesion, ProfesionExtra profesion2, String password, String password2, Long telefono) throws MiExcepcion {

        if (nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede estar vacío, o es incorrecto.");
        }
        if(nombre.length() >=28){
            throw new MiExcepcion("El nombre completo no puede tener mas de 28 caracteres.");
        }
        if (email.isEmpty()) {
            throw new MiExcepcion("El email no puede estar vacío.");
        }
        if (password.isEmpty() ) {
            throw new MiExcepcion("La contraseña no puede estar vacía.");
        }
        if (password.length() <= 7) {
            throw new MiExcepcion("La contraseña debe tener mas de 8 carácteres.");
        }
        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas no coinciden.");
        }
        if (Profesion.valueOf(profesion.toString()).equals(ProfesionExtra.valueOf(profesion2.toString()))){
            throw new MiExcepcion("Las profesiones no pueden coincidir");

        }
        if (telefono.equals(null)) {
            throw new MiExcepcion("Debe ingresar su numero de telefono.");
        }
    }


    // Método que devuelve una lista de proveedores - sin especificar la profesión --
     public List<Proveedor> listarProveedores(String profesion) {
         if(profesion!=null){
        return proveedorRepositorio.buscarProveedorPorProfesion(Profesion.valueOf(profesion));
         }else
        return proveedorRepositorio.findAll();

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
            usuario.setSexo(proveedor.getSexo());
            usuario.setImagen(proveedor.getImagen());
            proveedorRepositorio.delete(proveedor);

            List<Contrato> contratos = contratoRepositorio.buscarPorProveedorId(idProveedor);
            for (Contrato aux : contratos) {
                contratoArchivadoServicio.crearContratoArchivado(aux.getId());
            }

            usuarioRepositorio.save(usuario);

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
     
     
     @Transactional
     public void sumarContacto(String id){

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        if(respuesta.isPresent()){
            Proveedor proveedor = respuesta.get();

            Integer cont = proveedor.getContactos();

            proveedor.setContactos(cont + 1);

            proveedorRepositorio.save(proveedor);
        }

    }
     
     
     @Transactional
     public void calcularPromedio(String id){
        
        int suma = 0;
        int cont = 0;
        List<Resenia> resenias = reseniaRepositorio.buscarPorProveedorId(id);
        for (Resenia aux : resenias) {
            suma += aux.getCalificacion();
            cont += 1;
        }

        double promedio = suma / cont;

        Optional <Proveedor> respuesta = proveedorRepositorio.findById(id);

        if(respuesta.isPresent()){
            Proveedor proveedor = respuesta.get();
            proveedor.setPromedio(promedio);

            proveedorRepositorio.save(proveedor);
        }
    }
     

    
     
   
}
