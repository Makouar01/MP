package com.example.controller;

import com.example.entity.User;
import com.example.service.FileStorageService;
import com.example.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/file")
public class FileController {
    private final UserService userService;
    private final FileStorageService fileStorageService;

    public FileController(UserService userService , FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }
    @GetMapping("/{id}/photoProfil")
    public ResponseEntity<byte[]> getUserPhotoProfil(@PathVariable Long id) {
        try {
            // Récupérer l'utilisateur et son nom de fichier photo de profil
            String fileName = userService.getUserById(id)
                    .map(User::getPhotoProfil)
                    .filter(Objects::nonNull)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur ou photo non trouvé"));

            // Construire le chemin du fichier
            Path filePath = Paths.get(fileStorageService.getUploadDir(), fileName);

            // Vérifier si le fichier existe
            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fichier introuvable ou illisible");
            }

            // Lire le fichier en octets
            byte[] imageBytes = Files.readAllBytes(filePath);

            // Déterminer le type MIME du fichier
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Retourner l'image en réponse
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(imageBytes);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la lecture du fichier", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inconnue est survenue", e);
        }
    }

}
