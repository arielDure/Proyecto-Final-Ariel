/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.servicios;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import AplicacionServicios.ServiciosApp.entidades.ContratoArchivado;
import AplicacionServicios.ServiciosApp.repositorios.ContratoArchivadoRepositorio;
import AplicacionServicios.ServiciosApp.repositorios.ContratoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoArchivadoServicio {

    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Autowired
    private ContratoArchivadoRepositorio contratoArchivadoRepositorio;

    @Transactional
    public void crearContratoArchivado(String id) {

        Optional<Contrato> respuesta = contratoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Contrato contrato = respuesta.get();
            ContratoArchivado contratoArchivado = new ContratoArchivado();

            contratoArchivado.setId(contrato.getId());
            contratoArchivado.setContratoFinalizado(contrato.getContratoFinalizado());
            contratoArchivado.setPrecio(contrato.getPrecio());
            contratoArchivado.setInicioDelTrabajo(contrato.getInicioDelTrabajo());
            contratoArchivado.setRequisitos(contrato.getRequisitos());
            contratoArchivado.setHorasAprox(contrato.getHorasAprox());
            contratoArchivado.setActivo(contrato.getActivo());
            contratoArchivado.setRespuestaProveedor(contrato.getRespuestaProveedor());
            contratoArchivado.setRespuestaUsuario(contrato.getRespuestaUsuario());
            contratoArchivado.setUsuario(contrato.getUsuario().getId());
            contratoArchivado.setProveedor(contrato.getProveedor().getId());

            contratoRepositorio.deleteById(id);

            contratoArchivadoRepositorio.save(contratoArchivado);
        }

    }

    public  List<ContratoArchivado> listarContratosArchivados() {
            List<ContratoArchivado> contratosArchivados = new ArrayList<>();
            contratosArchivados = contratoArchivadoRepositorio.findAll();

            List<ContratoArchivado> contratos = new ArrayList<>();
            for (ContratoArchivado aux : contratos) {
                contratos.add(aux);
            }

            return contratos;
        }

    @Transactional
    public void eliminarContratoArchivado(String id){

        Optional<ContratoArchivado> respuesta = contratoArchivadoRepositorio.findById(id);

        if(respuesta.isPresent()){
            contratoArchivadoRepositorio.deleteById(id);
        }
    }

    }