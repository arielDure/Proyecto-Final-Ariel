package AplicacionServicios.ServiciosApp.entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ContratoArchivado {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;

    private Date contratoInicio;
    private Date contratoFinalizado;
    private Double precio;
    private Date inicioDelTrabajo;
    private String requisitos;
    private String horasAprox;
    private Boolean activo;
    private Boolean respuestaProveedor;
    private Boolean respuestaUsuario;

    private String usuario;

    private String proveedor;

    public ContratoArchivado() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getContratoInicio() {
        return contratoInicio;
    }

    public void setContratoInicio(Date contratoInicio) {
        this.contratoInicio = contratoInicio;
    }

    public Date getContratoFinalizado() {
        return contratoFinalizado;
    }

    public void setContratoFinalizado(Date contratoFinalizado) {
        this.contratoFinalizado = contratoFinalizado;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getInicioDelTrabajo() {
        return inicioDelTrabajo;
    }

    public void setInicioDelTrabajo(Date inicioDelTrabajo) {
        this.inicioDelTrabajo = inicioDelTrabajo;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getHorasAprox() {
        return horasAprox;
    }

    public void setHorasAprox(String horasAprox) {
        this.horasAprox = horasAprox;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getRespuestaProveedor() {
        return respuestaProveedor;
    }

    public void setRespuestaProveedor(Boolean respuestaProveedor) {
        this.respuestaProveedor = respuestaProveedor;
    }

    public Boolean getRespuestaUsuario() {
        return respuestaUsuario;
    }

    public void setRespuestaUsuario(Boolean respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}
