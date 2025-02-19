package me.bakuleva.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.bakuleva.recipesapp.exeption.InvalidtException;
import me.bakuleva.recipesapp.model.Ingredient;
import me.bakuleva.recipesapp.services.IngredientsServices;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ингредиенты",description = "Подробное описание ингредиентов для рецептов")

public class IngredientsController {
    private final IngredientsServices ingredientsServices;

    public IngredientsController(IngredientsServices ingredientsServices) {
        this.ingredientsServices = ingredientsServices;

    }
    @ExceptionHandler(InvalidtException.class)
    public ResponseEntity<String> handleInvalidSockRequestException(InvalidtException invalidSockRequestException){
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());}

   @GetMapping
   @Operation(description = "Получение всех ингредиентов.")
    public List<Ingredient> getAll(){
        return  this.ingredientsServices.getAll();}
   @PostMapping
   @Operation(description = "Добавление ингредиентов.")
    public ResponseEntity<?> addIngredient(@RequestBody Ingredient ingredient){

     if (StringUtils.isBlank(ingredient.getName())) {
         return ResponseEntity.badRequest().body("Название ингредиента не может быть пустым.");
     }
         return ResponseEntity.ok(ingredientsServices.add(ingredient));
     }

    @GetMapping("/{id}")
    @Operation(description = "Поиск ингредиентов.")
    public Ingredient getIngredient(@PathVariable("id") long id){
        return ingredientsServices.get(id);

    }

    @PutMapping("/{id}")
    @Operation(description = "Замена ингредиентов.")
    public Ingredient updateIngredient(@PathVariable("id") long id,@RequestBody Ingredient ingredient){
        return ingredientsServices.update(id, ingredient);}

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление ингредиентов.")
    public Ingredient deleteIngredient(@PathVariable("id") long id){
        return ingredientsServices.remove(id);
    }
    /*@GetMapping("/download")
    public ResponseEntity<byte[]> downloadRecipes(){
        byte[] bytes=ingredientsServices.getAllInBytes();
        if (bytes==null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(bytes.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"ingredients.json\"")
                .body(bytes);

    }
    @PostMapping(value = "import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importIngredients(MultipartFile ingredients){
        ingredientsServices.importIngredients(ingredients);

    }*/


}
