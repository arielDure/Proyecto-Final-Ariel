/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.repositorios;

import AplicacionServicios.ServiciosApp.entidades.Resenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author tobia
 */
@Repository
public interface ReseniaRepositorio extends JpaRepository<Resenia, String> {

//    @Query("SELECT * FROM Resenia WHERE id_usuario_id = :id")
//    public Resenia buscarPorIdUsuario(@Param("id_usuario_id") String id);

//    @Query("SELECT u FROM Resenia u WHERE u.idUsuario = :idUsuario")
//    public Resenia buscarPorIdUsuario(@Param("idUsuario") String idUsuario);
////
//    @Query("SELECT u FROM Resenia u WHERE u.calificacion = :calificacion")
//    public Resenia buscarPorCalificacion(@Param("calificacion") Integer calificacion);
////    
//    @Query("SELECT u FROM Resenia u WHERE u.idProveedor = :idProveedor")
//    public Resenia buscarPorIdProveedor(@Param("IdProveedor") String idProveedor); 
//    
//    @Query("SELECT u FROM Resenia u WHERE u.id = :id")
//    public Resenia buscarPorNombreUsuario(@Param("id") String id);
//    
      @Query("SELECT c FROM Resenia c WHERE c.proveedor.id LIKE :proveedor")
     public List<Resenia> buscarPorProveedorId(@Param("proveedor") String id);
}
