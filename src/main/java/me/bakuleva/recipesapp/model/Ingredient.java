
package me.bakuleva.recipesapp.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Ingredient {
    private  String name;
    private  int weight;
    private  String measureUnit;

    @Override
    public String toString() {
        return  name + " - " + weight +
                " " + measureUnit ;
    }
}


