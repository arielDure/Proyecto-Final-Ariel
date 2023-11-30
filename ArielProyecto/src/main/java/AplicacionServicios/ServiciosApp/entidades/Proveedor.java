/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.entidades;

import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import AplicacionServicios.ServiciosApp.enumeraciones.ProfesionExtra;
import AplicacionServicios.ServiciosApp.enumeraciones.Sexo;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

/**
 * @author tobia
 */
@Entity
public class Proveedor extends Usuario {

// EN LA BASE DE DATOS NO SE CREA UNA NUEVA TABLA LLAMADA PROVEEDOR , SE INCLUYE DIRECAMENTE COMO USUARIO YA QUE HEREDA DE ÉL 
    @Enumerated(EnumType.STRING)
    private Profesion profesion1;

    @Enumerated(EnumType.STRING)
    private ProfesionExtra profesion2;

    /* figurarnos como asignar mas de un rol en un proveedor. pedir ayuda a primera hora! */
    private Long telefono;
    private Long contactos;
    private String presentacion;
    private String descripcion;
    
    @OneToMany
    private List<Resenia> resenias;



    public Proveedor() {
    }
    
    public Profesion getProfesion1() {
        return profesion1;
    }

    public void setProfesion1(Profesion profesion1) {
        this.profesion1 = profesion1;
    }

    public ProfesionExtra getProfesion2() {
        return profesion2;
    }

    public void setProfesion2(ProfesionExtra profesion2) {
        this.profesion2 = profesion2;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public Long getContactos() {
        return contactos;
    }

    public void setContactos(Long contactos) {
        this.contactos = contactos;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Resenia> getResenias() {
        return resenias;
    }

    public void setResenias(List<Resenia> resenias) {
        this.resenias = resenias;
    }

}
