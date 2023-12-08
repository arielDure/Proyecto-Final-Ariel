/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.servicios;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Resenia;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.repositorios.ContratoRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.ReseniaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tobia
 */
@Service
public class ReseniaServicio {

    @Autowired
    private ReseniaRepositorio reseniaRepositorio;
    
    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Transactional
    public void crearResenia(Usuario usuario, Proveedor proveedor, String cuerpo, Integer calificacion) throws MiExcepcion{

        validar(cuerpo, calificacion);
        
        Resenia resenia = new Resenia();
        
        resenia.setUsuario(usuario);
        resenia.setProveedor(proveedor);
        resenia.setCuerpo(cuerpo);
        resenia.setCalificacion(calificacion);
       
       
        resenia.setFecha(new Date());

        reseniaRepositorio.save(resenia);

    }

    public List<Resenia> listarResenias() {
        List<Resenia> resenias = new ArrayList();
        resenias = reseniaRepositorio.findAll();
        return resenias;
    }
    
    @Transactional
    public void actualizarResenia(String idResenia, String cuerpo, Integer calificacion, Usuario idUsuario, Proveedor idProveedor) throws MiExcepcion {

        validar(cuerpo, calificacion);

        Optional<Resenia> respuesta = reseniaRepositorio.findById(idResenia);

        if (respuesta.isPresent()) {

            Resenia resenia = respuesta.get();
            resenia.setCalificacion(calificacion);
            resenia.setCuerpo(cuerpo);
            resenia.setFecha(new Date());
            
            reseniaRepositorio.save(resenia);
        }
        
    }
    
    @Transactional
    public void borrarReseniaPorID(String idResenia) {

        Optional<Resenia> respuesta = reseniaRepositorio.findById(idResenia);

        if (respuesta.isPresent()) {
            reseniaRepositorio.deleteById(idResenia);
        }

    }
    
    
    public void validar(String cuerpo, Integer calificacion) throws MiExcepcion {

       
        if (cuerpo.isEmpty()) {
            throw new MiExcepcion("El cuerpo no puede estar vacío, o ser nulo");
        }
        if (cuerpo.length() >= 255){
            throw new MiExcepcion("La reseña esta limitada a 255 caracteres.");
        }
        if (calificacion == null) {
            throw new MiExcepcion("La calificacion no puede ser nula o no estar comprendida entre 1 y 5");
        }
    }
    
    public List<Resenia> buscarPorProveedorId(String idProveedor){
      
        List<Resenia> resenias = reseniaRepositorio.buscarPorProveedorId(idProveedor);
        
        return resenias;
    }
    
    @Transactional
    public void cambiarEstadoDeTieneResenia(String id){
       Optional <Contrato> respuesta = contratoRepositorio.findById(id);
       
        if (respuesta.isPresent()) {
            
            Contrato contrato = respuesta.get();
            
            contrato.setTieneResenia(true);
            
            contratoRepositorio.save(contrato);
        }
       
    }
}
