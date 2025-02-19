package me.bakuleva.recipesapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.bakuleva.recipesapp.model.Ingredient;
import org.springframework.stereotype.Service;
import me.bakuleva.recipesapp.exeption.InvalidtException;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientsServicesImpl  implements IngredientsServices {

    private Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private static long counter = 0;
    private final FilesService filesService;
    private Ingredient ingredient;

    public IngredientsServicesImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    public void init() {



    }

    @Override
    public void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesService.saveIngredients(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readDataFromFile() {
        String json = filesService.readIngredients();
        try {
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient add(Ingredient ingredient) {
        ingredientMap.put(counter++, ingredient);
        saveToFile();
        return ingredientMap.get(counter);
    }

    @Override
    public Ingredient get(long id) {
        if (id <= 0) {
            throw new InvalidtException(" должно быть больше 0");
        }

        return ingredientMap.get(id);
    }
    @Override
    public Ingredient update(long id, Ingredient ingredient) {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.put(id, ingredient);
            saveToFile();
            return ingredient;
        }
        throw new RuntimeException("Ингредиент для замены не найден");
    }
    @Override
    public List<Ingredient> getAll() {
        return new ArrayList<>(this.ingredientMap.values()) ;
    }

    @Override
    public Ingredient remove(long id) {
        Ingredient ingredient = ingredientMap.remove(id);
        return ingredient;
    }}
