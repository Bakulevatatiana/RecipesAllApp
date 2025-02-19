package me.bakuleva.recipesapp.controllers;

import me.bakuleva.recipesapp.record.RecordInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class InfoController {

    @GetMapping
    public String index(){
        return  "Приложение запущенно.";
    }

    @GetMapping("/info")
    public RecordInfo info(){
        return new RecordInfo("Бакулева Татьяна","Recipes", LocalDate.of(2023,02,21),"Список рецептов.");

    }
}
