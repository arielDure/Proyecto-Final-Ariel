/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AplicacionServicios.ServiciosApp.controladores;

import AplicacionServicios.ServiciosApp.entidades.Proveedor;
import AplicacionServicios.ServiciosApp.entidades.Usuario;
import AplicacionServicios.ServiciosApp.servicios.ProveedorServicio;
import AplicacionServicios.ServiciosApp.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author tobia
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private ProveedorServicio proveedorServicio;
    
    
   @GetMapping("/perfilUsuario/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id){
      Usuario usuario = usuarioServicio.getOne(id);
      byte[] imagen = usuario.getImagen().getContenido();
      
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
      
      return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
    }
    
    @GetMapping("/perfilProveedor/{id}")
    public ResponseEntity<byte[]> imagenProveedor(@PathVariable String id){
      Proveedor proveedor = proveedorServicio.getOne(id);
      byte[] imagen = proveedor.getImagen().getContenido();
      
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
      
      return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
    }
}
