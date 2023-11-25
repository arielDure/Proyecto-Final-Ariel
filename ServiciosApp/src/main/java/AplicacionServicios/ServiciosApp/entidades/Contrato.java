package AplicacionServicios.ServiciosApp.entidades;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Contrato {

    @Id
    @GeneratedValue(generator="uuid")
    private   String id;

    private String  contacto;
    private Date contratoInicio;
    private Date contratoFinalizado;
    private Double precio;

    private Boolean activo;

    private Boolean respuestaProveedor;

    private Boolean respuestaUsuario;
    @OneToOne
    private Usuario usuario;

    @OneToOne
    private  Proveedor proveedor;


    public Contrato() {
    }

    public Contrato(String id, String contacto, Date contratoInicio, Date contratoFinalizado, Double precio, Boolean activo, Usuario usuario, Proveedor proveedor) {
        this.id = id;
        this.contacto = contacto;
        this.contratoInicio = contratoInicio;
        this.contratoFinalizado = contratoFinalizado;
        this.precio = precio;
        this.activo = activo;
        this.usuario = usuario;
        this.proveedor = proveedor;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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