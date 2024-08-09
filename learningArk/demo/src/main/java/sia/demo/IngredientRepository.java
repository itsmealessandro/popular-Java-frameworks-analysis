package sia.demo;


public interface IngredientRepository {
    
    Ingredient findOne(String id);

    Iterable<Ingredient> findAll();

    Ingredient save(Ingredient ingredient);


}
