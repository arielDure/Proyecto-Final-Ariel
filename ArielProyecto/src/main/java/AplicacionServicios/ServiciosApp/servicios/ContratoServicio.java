package AplicacionServicios.ServiciosApp.servicios;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.repositorios.ContratoRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.ProveedorRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Transactional
    public void crearContacto(String id, Usuario usuario, Proveedor proveedor){

        Contrato contrato = new Contrato();
        contrato.setUsuario(usuario);
        contrato.setProveedor(proveedor);
        contrato.setRespuestaUsuario(true);

        contratoRepositorio.save(contrato);
    }

    @Transactional
    public void aceptarContrato(String id, Double precio, Date fechaFinalizacion){

        Optional<Contrato> respuesta = contratoRepositorio.findById(id);

        if(respuesta.isPresent()){

            Date fechaInicio = new Date();

            Contrato contrato = respuesta.get();
            contrato.setPrecio(precio);
            contrato.setContratoFinalizado(fechaFinalizacion);
            contrato.setContratoInicio(fechaInicio);
            contrato.setRespuestaProveedor(true);
            contrato.setActivo(true);

            contratoRepositorio.save(contrato);
        }

    }


    @Transactional
    public void actualizarContrato(String id, Double precio, Date fechaDeFinalizacion){

        Optional<Contrato> respuesta = contratoRepositorio.findById(id);

        if(respuesta.isPresent()){

            Contrato contrato = respuesta.get();
            contrato.setPrecio(precio);
            contrato.setContratoFinalizado(fechaDeFinalizacion);

            contratoRepositorio.save(contrato);

        }

    }

    public List<Contrato> listarContratos(Proveedor id){
        List<Contrato> contrato = new ArrayList<>();
        contrato = contratoRepositorio.findAll();
        List<Contrato> contratos = new ArrayList<>();
        for (Contrato aux : contrato){
            if(aux.getProveedor().getId().equals(id)){
                contratos.add(aux);
            }
        }
        return contratos;
    }

    @Transactional
    public void cancelarContrato(String id){

        Optional<Contrato> respuesta = contratoRepositorio.findById(id);

        if(respuesta.isPresent()){
            contratoRepositorio.deleteById(id);
        }
    }

    @Transactional
    public void finalizarContrato(String id){

        Optional<Contrato> respuesta = contratoRepositorio.findById(id);

        if(respuesta.isPresent()){
            Contrato contrato = respuesta.get();

            if(contrato.getRespuestaProveedor() == false && contrato.getRespuestaUsuario() == false) {
                contrato.setActivo(false);
            }
            contratoRepositorio.save(contrato);
        }
    }



}
