package me.bakuleva.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.bakuleva.recipesapp.model.Recipe;
import me.bakuleva.recipesapp.services.FilesService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@RestController
    @RequestMapping("/files")
    public class FilesController {
        private final FilesService filesService;

        public FilesController(FilesService filesService) {
            this.filesService = filesService;
        }

        @GetMapping("/export/recipes")
        public ResponseEntity<InputStreamResource> downloadDataFileRecipes() throws FileNotFoundException {
            File file = filesService.getRecipesFile();
            if (file.exists()) {
                InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.json\"")
                        .contentLength(file.length())
                        .body(isr);
            } else {
                return ResponseEntity.noContent().build();
            }
        }
        @GetMapping("/export/ingredients")
        public ResponseEntity<InputStreamResource> downloadDataFileIngredients() throws FileNotFoundException {
            File file = filesService.getIngredientsFile();
            if (file.exists()) {
                InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ingredients.json\"")
                        .contentLength(file.length())
                        .body(isr);
            } else {
                return ResponseEntity.noContent().build();
            }
        }
    @PostMapping(value = "/importRecipe",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFileRecipe(@RequestParam MultipartFile file) {
        filesService.cleanRecipeFile();
        File dataFile = filesService.getRecipesFile();
        try (FileOutputStream fos=new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(),fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

        @PutMapping(value = "/recipesImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Void> uploadRecipeBase(@RequestParam MultipartFile file) {
            if (filesService.uploadRecipeFile(file)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    @PostMapping(value = "/importIngredients",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFileIngredients(@RequestParam MultipartFile file) {
        filesService.cleanIngredientFile();
        File dataFile = filesService.getIngredientsFile();
        try (FileOutputStream fos=new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(),fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

        @PutMapping(value = "/ingredientsImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Void> uploadIngredientBase(@RequestParam MultipartFile file) {
            if (filesService.uploadIngredientFile(file)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }






    }


