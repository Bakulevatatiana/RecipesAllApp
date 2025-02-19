package me.bakuleva.recipesapp.services;

import me.bakuleva.recipesapp.model.Ingredient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IngredientsServices {
    void saveToFile();

    Ingredient add(Ingredient ingredient);
    Ingredient get(long id);
    Ingredient update(long id,Ingredient ingredient);

    List<Ingredient> getAll();

    Ingredient remove(long id);
   /* byte[] getAllInBytes();


    void importIngredients(MultipartFile ingredients);*/
}
