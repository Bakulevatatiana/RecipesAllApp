package me.bakuleva.recipesapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import me.bakuleva.recipesapp.exeption.InvalidtException;
import me.bakuleva.recipesapp.model.Ingredient;
import me.bakuleva.recipesapp.model.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class RecipesServicesImpl implements RecipesServices {
    private Map<Long, Recipe> recipeMap = new HashMap<>();
    private static long counter;
    private  FilesService filesService;

private Recipe recipe;
    public RecipesServicesImpl(FilesService filesService) {

        this.filesService = filesService;
    }

    @PostConstruct
    public void init() {



    }
    @Override
    public void saveToFile(){
        try {
            String json=new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveRecipes(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readDataFromFile() {
        String json= filesService.readRecipes();
        try {
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }



    @Override
    public Recipe add(Recipe recipe) {
        recipeMap.put(counter++, recipe);
        saveToFile();
        return recipe;
    }

    @Override
    public Recipe get(long id) {
        if (id <= 0) {
            throw new InvalidtException(" должно быть больше 0");
        }
            return recipeMap.get(id);
        }


    @Override
    public Recipe update(long id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            recipeMap.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }



    @Override
    public List<Recipe> getAll() {
        return new ArrayList<>(this.recipeMap.values());
    }
 @Override
    public Recipe remove(long id) {
        Recipe recipe = recipeMap.remove(id);
        return recipe;
    }
  /*  @Override
    public byte[] getAllInBytes() {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void importRecipes(MultipartFile recipes) {
        try {
            Map<Long, Recipe> mapFromRequest = objectMapper.readValue(recipes.getBytes(), new TypeReference<>() {
            });
            writeDataToFile(mapFromRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] exportTxt() {
        try {
            String template = Files.readString(pathToTxtTemplate, StandardCharsets.UTF_8);
            StringBuilder stringBuilder= new StringBuilder();
            for( Recipe recipe:recipeMap.values()){
                StringBuilder ingredients=new StringBuilder();
                StringBuilder steps=new StringBuilder();
                    for(Ingredient ingredient: recipe.getIngredients()){
                     ingredients.append(" - ").append(ingredient).append("\n");
                    }
                    int stepCounter=1;
                    for( String step:recipe.getSteps()){
                        steps.append(stepCounter++).append(". ").append(step).append("\n");
                    }
                    String recipeData= template.replace("%name%",recipe.getName())
                    .replace("%cookingTime%",String.valueOf(recipe.getCookingTime()))
                            .replace("%ingredients%",ingredients.toString())
                            .replace("%steps%",steps.toString());
                    stringBuilder .append(recipeData).append("\n\n\n");


            }
            return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}

