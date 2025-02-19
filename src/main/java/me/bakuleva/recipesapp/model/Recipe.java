package me.bakuleva.recipesapp.model;

import lombok.*;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Data
public class Recipe {

    private  String name;
    private  int cookingTime;
    private List<Ingredient> ingredients;
    private List<String> steps;

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", cookingTime=" + cookingTime +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}



