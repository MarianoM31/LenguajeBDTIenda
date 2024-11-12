package com.proyecto.service;

import org.springframework.web.multipart.MultipartFile;

public interface FirebaseStoreService {
    
     public String cargaImagen(MultipartFile archivoLocalCliente, String carpeta, Long id);

    
    final String BucketName = "store-75095.appspot.com";

   
    final String rutaSuperiorStorage = "store";

    
    final String rutaJsonFile = "firebase";
    
  
    final String archivoJsonFile = "store-75095-firebase-adminsdk-s2b9s-1c621565ba.json";
}
