package AplicacionServicios.ServiciosApp.entidades;
import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Contrato {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;

    private Date contratoFinalizado;
    private Double precio;
    private Date inicioDelTrabajo;
    private String requisitos;
    private String horasAprox;
    private Boolean activo;
    private Boolean respuestaProveedor;
    private Boolean respuestaUsuario;
    private  Boolean tieneResenia;


    @OneToOne
    private Usuario usuario;

    @OneToOne
    private Proveedor proveedor;

    public Contrato() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getTieneResenia() {
        return tieneResenia;
    }

    public void setTieneResenia(Boolean tieneResenia) {
        this.tieneResenia = tieneResenia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    
   
    
    
    
}