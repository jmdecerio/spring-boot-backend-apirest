package com.bolsadeideas.springboot.backend.apirest.models.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class UploadFileService implements IUploadFileService {

    public final static String DIRECTORIO_UPLOADS = "uploads";

    @Override
    public Resource cargar(String nombreFoto) throws MalformedURLException {
        Path rutaArchivo = getPath(nombreFoto);
        log.info(rutaArchivo.toString());
        Resource recurso = new UrlResource(rutaArchivo.toUri());
        if (!recurso.exists() && !recurso.isReadable()) {
            throw new RuntimeException("Error no se pudo cargar la imagen");
        }
        return recurso;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
        Path rutaArchivo = getPath(nombreArchivo);
        log.info(rutaArchivo.toString());
        Files.copy(archivo.getInputStream(), rutaArchivo);
        return nombreArchivo;
    }

    @Override
    public boolean eliminar(String nombrefoto) {
        if (nombrefoto != null && nombrefoto.length() > 0) {
            Path rutaArchivoAnterior = Paths.get("uploads").resolve(nombrefoto).toAbsolutePath();
            File archivoAnterior = rutaArchivoAnterior.toFile();
            if (archivoAnterior.exists() && archivoAnterior.canRead()) {
                archivoAnterior.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String nombreFoto) {
        return Paths.get(DIRECTORIO_UPLOADS).resolve(nombreFoto).toAbsolutePath();
    }
}
