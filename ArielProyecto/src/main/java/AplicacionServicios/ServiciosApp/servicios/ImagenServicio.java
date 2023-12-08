package AplicacionServicios.ServiciosApp.servicios;

import AplicacionServicios.ServiciosApp.entidades.Imagen;
import AplicacionServicios.ServiciosApp.excepciones.MiExcepcion;
import AplicacionServicios.ServiciosApp.repositorios.ImagenRepositorio;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;


    // función que guarda una imagen en la base de dato.
    @Transactional
    public Imagen guardar(MultipartFile archivo, String sexo, String rol) throws MiExcepcion {
        try {
            if (archivo != null &&  !archivo.isEmpty() ) {

                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } else if (archivo.isEmpty() && sexo.equals("MASCULINO")  && rol.equals("USUARIO")){
                ClassPathResource defaultImagenUser = new ClassPathResource("static/img/imagenDefaultHombre.JPG");
                System.out.println( "tipo de sexo" +  sexo);
                try(InputStream inputStream = defaultImagenUser.getInputStream()) {
                    Imagen imagen = new Imagen();
                    imagen.setMime("image/jpg");
                    imagen.setNombre("imagen_por_defecto.jpg");
                    imagen.setContenido(StreamUtils.copyToByteArray(inputStream));

                    return imagenRepositorio.save(imagen);

                } catch (IOException e) {
                    throw new MiExcepcion("error al leer la imagen por defecto");
                }
            } else if (archivo.isEmpty() && sexo.equals("FEMENINO") && rol.equals("USUARIO")) {
                ClassPathResource defaultImagenUser = new ClassPathResource("static/img/imagenDefaultMujer.JPG");
                System.out.println( "tipo de sexo" +  sexo);
                try(InputStream inputStream = defaultImagenUser.getInputStream()) {
                    Imagen imagen = new Imagen();
                    imagen.setMime("image/jpg");
                    imagen.setNombre("imagen_por_defecto.jpg");
                    imagen.setContenido(StreamUtils.copyToByteArray(inputStream));

                    return imagenRepositorio.save(imagen);

                } catch (IOException e) {
                    throw new MiExcepcion("error al leer la imagen por defecto");
                }

            }else if (archivo.isEmpty() && sexo.equals("OTRO") && rol.equals("USUARIO")) {
                ClassPathResource defaultImagenUser = new ClassPathResource("static/img/imagenDefaulOtros.JPG");
                System.out.println( "tipo de sexo" +  sexo);
                try(InputStream inputStream = defaultImagenUser.getInputStream()) {
                    Imagen imagen = new Imagen();
                    imagen.setMime("image/jpg");
                    imagen.setNombre("imagen_por_defecto.jpg");
                    imagen.setContenido(StreamUtils.copyToByteArray(inputStream));

                    return imagenRepositorio.save(imagen);

                } catch (IOException e) {
                    throw new MiExcepcion("error al leer la imagen por defecto");
                }

            } else if (archivo.isEmpty() && rol.equals("PROVEEDOR") ) {
                ClassPathResource defaultImagenUser = new ClassPathResource("static/img/imagenDefaultProveedor.JPG");
                System.out.println( "tipo de sexo" +  sexo);
                try(InputStream inputStream = defaultImagenUser.getInputStream()) {
                    Imagen imagen = new Imagen();
                    imagen.setMime("image/jpg");
                    imagen.setNombre("imagen_por_defecto.jpg");
                    imagen.setContenido(StreamUtils.copyToByteArray(inputStream));

                    return imagenRepositorio.save(imagen);

                } catch (IOException e) {
                    throw new MiExcepcion("error al leer la imagen por defecto");
                }
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return  null;
    }

    // Función para actualizar una imagen en la base de dato.
    @Transactional
    public Imagen actualizar(MultipartFile archivo, String id) throws MiExcepcion {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (id != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(id);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }
}
