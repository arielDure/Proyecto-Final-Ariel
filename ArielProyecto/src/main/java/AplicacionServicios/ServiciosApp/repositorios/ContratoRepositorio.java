package AplicacionServicios.ServiciosApp.repositorios;

import AplicacionServicios.ServiciosApp.entidades.Contrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato, String> {

    @Query("SELECT c FROM Contrato c WHERE c.proveedor.id = :idProveedor AND c.activo = true")
    List<Contrato> listaContratoProveedor(@Param("idProveedor") String idProveedor);
    
    @Query("SELECT c FROM Contrato c WHERE c.usuario.id = :idUsuario AND c.activo = true")
    List<Contrato> listaContratoUsuario(@Param("idUsuario") String idUsuario);
    
    @Query("SELECT c FROM Contrato c WHERE c.usuario.id LIKE :usuario")
    List<Contrato> buscarPorUsuarioId(@Param("usuario") String id);
    
    @Query("SELECT c FROM Contrato c WHERE c.proveedor.id LIKE :proveedor")
    List<Contrato> buscarPorProveedorId(@Param("proveedor") String id);
    
}
