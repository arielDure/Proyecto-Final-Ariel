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
    private ProveedorServicio proveedorServicio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Transactional
    public void crearContrato(String requisitos, Usuario usuario, Proveedor proveedor){
        Contrato contrato = new Contrato();
        contrato.setUsuario(usuario);
        contrato.setProveedor(proveedor);
        contrato.setRequisitos(requisitos);
        contrato.setActivo(true);
        contrato.setTieneResenia(false);
        
        contratoRepositorio.save(contrato);
    }

    @Transactional
    public void envioDeProveedorACliente(String id, Double precio, String horasAprox,Date inicioDelTrabajo){

        Optional<Contrato> respuesta = contratoRepositorio.findById(id);

        if(respuesta.isPresent()){

            Contrato contrato = respuesta.get();
            contrato.setPrecio(precio);
            contrato.setHorasAprox(horasAprox);
            contrato.setInicioDelTrabajo(inicioDelTrabajo);
            contrato.setRespuestaProveedor(true);

            contratoRepositorio.save(contrato);
        }

    }


    @Transactional
    public void aceptarContrato(String id, boolean confirmacion){

        Optional<Contrato> respuesta = contratoRepositorio.findById(id);

        if(respuesta.isPresent()){

            Contrato contrato = respuesta.get();
            contrato.setRespuestaUsuario(confirmacion);
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

            if(contrato.getRespuestaProveedor() == true && contrato.getRespuestaUsuario() == true) {
                contrato.setActivo(false);
                contrato.setContratoFinalizado(new Date());
            }

            proveedorServicio.sumarContacto(contrato.getProveedor().getId());
            contratoRepositorio.save(contrato);
        }
    }
    
   
    public List<Contrato> listaContratoProveedor(String idProveedor) {
         return contratoRepositorio.listaContratoProveedor(idProveedor);
    }
    
     public List<Contrato> listaContratoUsuario(String idUsuario) {
        return contratoRepositorio.listaContratoUsuario(idUsuario);
    }
    
    
    public Contrato getOne(String id){
        return contratoRepositorio.getOne(id);
    }
    
    public List<Contrato> listarPorPrecioNull(String idProveedor){
        return contratoRepositorio.buscarPorProveedorPrecio2(idProveedor);
        
    }


}
