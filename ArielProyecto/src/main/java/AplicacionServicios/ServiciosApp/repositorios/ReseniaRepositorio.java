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

      @Query("SELECT c FROM Resenia c WHERE c.proveedor.id = :idProveedor")
     public List<Resenia> buscarPorProveedorId(@Param("idProveedor") String idProveedor);


}
