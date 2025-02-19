package me.bakuleva.recipesapp.services;

import me.bakuleva.recipesapp.model.Ingredient;
import me.bakuleva.recipesapp.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipesServices {
    void saveToFile();

    Recipe add(Recipe recipe);
    Recipe get(long id);
    Recipe update(long id, Recipe recipe);
    List<Recipe> getAll();
  Recipe remove( long id);
  /*  byte [] getAllInBytes();

    void importRecipes(MultipartFile recipes);

    byte[] exportTxt();*/
}
