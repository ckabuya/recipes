package recipes.service;
import java.util.Map;
import recipes.model.Recipe;
@service
public class RecipeService {
    private Map<Integer, Recipe> recipesList;

    RecipeService(Map<Integer, Recipe> map){
        this.recipesList = map;
    }

    public Integer addRecipe(Recipe recipe) {
        if(recipesList.isEmpty()){
            recipesList.put(1,recipe);
            return 1;
        }
        else{
            int id = recipesList.size()+1;
            recipesList.put(id,recipe);
            return id;
        }
    }

    public Recipe getRecipe(Integer id){
        return recipesList.get(id);
    }
}
