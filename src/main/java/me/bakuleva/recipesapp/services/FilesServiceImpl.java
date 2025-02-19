package me.bakuleva.recipesapp.services;

import lombok.NoArgsConstructor;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import me.bakuleva.recipesapp.services.FilesService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.files}")
    private String filesPath;
    @Value("${name.file.ingredients}")
    private String ingredientFileName;
    @Value("${name.file.recipes}")
    private String recipesFileName;

    @Override
    public boolean saveIngredients(String json) {
        try {
            cleanIngredientFile();
            Files.writeString(Path.of(filesPath, ingredientFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveRecipes(String json) {
        try {
            cleanRecipeFile();
            Files.writeString(Path.of(filesPath, recipesFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readIngredients() {
        try {
            return Files.readString(Path.of(filesPath, ingredientFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String readRecipes() {
        try {
            return Files.readString(Path.of(filesPath, recipesFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public File getRecipesFile() {
        return new File(filesPath + "/" + recipesFileName);
    }

    @Override
    public File getIngredientsFile() {
        return new File(filesPath + "/" + ingredientFileName);
    }


    @Override
    public boolean uploadIngredientFile(MultipartFile file) {
        if (cleanIngredientFile()) {
            System.out.println("Очистка прошла успешно");
        } else {
            System.out.println("Что-то с очисткой не задалось...");
        }
        File dataFile = getIngredientsFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean uploadRecipeFile(MultipartFile file) {
        if (cleanRecipeFile()) {
            System.out.println("Очистка прошла успешно");
        } else {
            System.out.println("Что-то с очисткой не задалось...");
        }
        File dataFile = getRecipesFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * очищаем файл базы данных рецептов
     * @return boolean очищено или нет
     */
    @Override
    public boolean cleanRecipeFile() {
        try {

            Files.deleteIfExists(Path.of(filesPath, recipesFileName));
            Files.createFile(Path.of(filesPath, recipesFileName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * очищаем файл базы данных ингридиентов
     * @return boolean прошла очистка или нет
     */
    @Override
    public boolean cleanIngredientFile() {
        try {
            Files.deleteIfExists(Path.of(filesPath, ingredientFileName));
            Files.createFile(Path.of(filesPath, ingredientFileName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * создаем временный файл для выгрузки нужных данных
     * @param suffix файла
     * @return путь к временному файлу, чтобы открывать входящий в файл поток
     */
    @Override
    public Path createTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(filesPath), "tempFile", suffix);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}