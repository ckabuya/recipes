package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Recipe;
import recipes.service.RecipeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
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

    /***
     * GET /api/recipe/search takes one of the two mutually exclusive query parameters:
     * category – if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sort the recipes by date (newer first);
     * name – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sort the recipes by date (newer first).
     * @param searchParam
     * @return
     */
    @GetMapping("/api/recipe/search/")
    public List<Recipe> search(@RequestParam Map<String,String> searchParam){
       //If 0 parameters were passed, or more than 1, the server should return
        if(searchParam.isEmpty() || searchParam.size()> 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String param = searchParam.get("name");
        String param2 = searchParam.get("category");
         if (param == null && param2 == null){
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
         }

        List<Recipe>  recipesList =null;
        if(param !=null) {
            recipesList = recipeService.search("name", param.trim());
            System.out.println("Searching by "+param + " record :" +recipesList.size() +" Data "+recipesList);
        }
        else if (param2 !=null){

            recipesList = recipeService.search("category",param2.trim());
            System.out.println("Searching by "+param2 + " record :" +recipesList.size() +" Data "+recipesList);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
       return recipesList;
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int id){

        if(recipeService.getRecipe(id) !=null ){
            recipeService.deleteRecipe(id);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
        if(recipe == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getName() == null || recipe.getName().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getDescription() == null || recipe.getDescription().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getDescription() == null || recipe.getDescription().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getDirections() == null || recipe.getDirections().length == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getIngredients() == null || recipe.getIngredients().length == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getCategory() == null || recipe.getCategory().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else {
            Integer id = recipeService.addRecipe(recipe);
            Map<String,Integer> result = new HashMap<>();
            result.put("id",id);
            return result;
        }

    }

    /**
     * PUT /api/recipe/{id} receives a recipe as a JSON object and updates a recipe with a specified id.
     * Also, update the date field too. The server should return the 204 (No Content) status code.
     * If a recipe with a specified id does not exist, the server should return 404 (Not found).
     * The server should respond with 400 (Bad Request)
     * if a recipe doesn't follow the restrictions indicated above (all fields are required, string fields can't be blank, arrays should have at least one item);
     *
     * @param recipe
     * @param id
     * @return
     */
    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<String> updateRecipe(@RequestBody Recipe recipe,@PathVariable int id){
        if(recipe == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getName() == null || recipe.getName().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getDescription() == null || recipe.getDescription().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getDescription() == null || recipe.getDescription().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getDirections() == null || recipe.getDirections().length == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getIngredients() == null || recipe.getIngredients().length == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(recipe.getCategory() == null || recipe.getCategory().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else {
            Recipe u = recipeService.updateRecipe(recipe,id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
}
