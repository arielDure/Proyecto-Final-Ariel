/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.repositorios;

import AplicacionServicios.ServiciosApp.entidades.Usuario;
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
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    // Query para buscar en la base de dato por nombre;
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE :nombre")
    public List<Usuario> buscarUsuarioPorNombre(@Param("nombre") String nombre);

    @Query("SELECT u FROM Usuario u WHERE u.email LIKE :email")
    public Usuario buscarUsuarioPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.id LIKE :id")
    public Usuario buscarPorId(@Param("id") String id);
    
    @Query("SELECT u FROM Usuario u WHERE u.activo LIKE :activo ORDER BY activo DESC")
    public Usuario buscarPorEstadoDesc(@Param("activo") String activo);
    
    @Query("SELECT u FROM Usuario u WHERE u.activo LIKE :activo ORDER BY activo")
    public Usuario buscarPorEstadoAsc(@Param("activo") String activo);
    
    
}
