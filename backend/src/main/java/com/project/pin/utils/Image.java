package com.project.pin.utils;

import com.project.pin.entity.Autor;
import com.project.pin.entity.Comprador;
import com.project.pin.entity.Usuario;

public class Image {
    private static final String DEFAULT_IMAGE_URL = "back-end/src/main/resources/static/images/default-img-profile.jpg";

    //DESSA FORMA PODEMOS RECUPERAR A IMAGEM NO FRONT
    public static String getImageAutor(Autor autor) {
        return (autor.getImg() != null && !autor.getImg().isEmpty()) ? autor.getImg() : DEFAULT_IMAGE_URL;
    }

    public static String getImageComprador(Comprador comprador) {
        return (comprador.getImg() != null && !comprador.getImg().isEmpty()) ? comprador.getImg(): DEFAULT_IMAGE_URL;
    }
}
