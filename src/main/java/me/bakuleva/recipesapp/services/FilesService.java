package me.bakuleva.recipesapp.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

@Service
public interface FilesService {
    boolean saveIngredients(String json);

    boolean saveRecipes(String json);

    String readIngredients();

    String readRecipes();

    File getRecipesFile();

    File getIngredientsFile();

    boolean uploadIngredientFile(MultipartFile file);

    boolean uploadRecipeFile(MultipartFile file);

    boolean cleanRecipeFile();

    boolean cleanIngredientFile();

    Path createTempFile(String suffix);
}