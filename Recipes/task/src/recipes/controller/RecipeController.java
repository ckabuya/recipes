package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Recipe;
import recipes.service.RecipeService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RecipeController {
    private Map<Integer, Recipe> recipes = new HashMap<>();

    RecipeService recipeService;

    RecipeController(){
        this.recipeService = new RecipeService(recipes);
    }
    /**
     * GET /api/recipe/{id} returns a recipe with a specified id as a JSON object (where {id} is the id of a recipe).
     * The server should respond with the 200 (Ok) status code.
     * If a recipe with a specified id does not exist, the server should respond with 404 (Not found).
     * @param id GET /api/recipe/1 request
     * @return
     */

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id){
       Recipe recipe = recipeService.getRecipe(id);
       if(recipe != null) {
           return recipe;
       }
       else{
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }


    }

    /**
     *  POST /api/recipe/new receives a recipe as a JSON object and returns a JSON object with one id field.
     *  This is a uniquely generated number by which we can identify and retrieve a recipe later. The status code should be 200 (Ok).
     * @param recipe
     * @return
     */
    @PostMapping("/api/recipe/new")
    public Map<String,Integer> addRecipe(@RequestBody Recipe recipe){
        Integer id = recipeService.addRecipe(recipe);
        Map<String,Integer> result = new HashMap<>();
        result.put("id",id);

        return result;
    }
}
