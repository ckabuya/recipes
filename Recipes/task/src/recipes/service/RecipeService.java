package recipes.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {

        this.recipeRepository = recipeRepository;
    }

    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Integer addRecipe(Recipe recipe) {
        Recipe saved;
       /* int count = (int) recipeRepository.count();
        synchronized (this) {
            if (count == 0) {
                recipe.setId(1);
                saved = recipeRepository.save(recipe);
            } else {
                recipe.setId(count + 1);
                saved = recipeRepository.save(recipe);
            }
        }
        ;*/
        saved = recipeRepository.save(recipe);
        System.out.println(recipeRepository.count()+ " Added Recipe: " + saved);

        return saved.getId();
    }

    public Recipe updateRecipe(Recipe recipe, int id) {

        Recipe update = getRecipe(id);
        update.setCategory(recipe.getCategory());
        update.setDate(recipe.getDate());
        update.setDescription(recipe.getDescription());
        update.setIngredients(recipe.getIngredients());
        update.setDirections(recipe.getDirections());
        update.setName(recipe.getName());
        System.out.println(recipe + " Updating Recipe :" + update);
        return recipeRepository.save(update);
    }

    public Recipe getRecipe(Integer id) {
        System.out.println(" Getting Recipe :" + id);
        return recipeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteRecipe(Integer id) {
        recipeRepository.deleteById(id);
        System.out.println("Removing Recipe:" + id);
    }

    public List<Recipe> search(String type, String param) {
        switch (type) {
            case "name":
                return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(param.trim());
            case "category":
                return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(param);
            default:
                throw new RuntimeException("Invalid search option");
        }
    }

}
