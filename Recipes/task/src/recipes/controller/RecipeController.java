package recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;

@RestController
public class RecipeController {
    Recipe newRec = new Recipe();
    @GetMapping("/api/recipe")
    public Recipe getRecipe(){
       return newRec;
    }
    @PostMapping("/api/recipe")
    public Recipe addRecipe(@RequestBody Recipe recipe){
     this.newRec = recipe;
     return newRec;
    }
}
