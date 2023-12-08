/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.repositorios;

import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.enumeraciones.Profesion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tobia
 */
@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {

    @Query("SELECT u FROM Proveedor u WHERE u.nombre = :nombre")
    public Proveedor buscarProveedorPorNombre(@Param("nombre") String nombre);

    @Query("SELECT u FROM Proveedor u WHERE u.email = :email")
    public Proveedor buscarProveedorPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Proveedor u WHERE u.profesion1 = :profesion1")
    public Proveedor buscarProveedorPorProfesion1(@Param("profesion1") String profesion1);

    @Query("SELECT u FROM Proveedor u WHERE u.telefono = :telefono")
    public Proveedor buscarProveedorPorTelefono(@Param("telefono") Long telefono);
    
    @Query("SELECT u FROM Proveedor u WHERE u.profesion1 = :profesion OR u.profesion2 = :profesion")
    public List<Proveedor> buscarProveedorPorProfesion(@Param("profesion") Profesion profesion);

}
