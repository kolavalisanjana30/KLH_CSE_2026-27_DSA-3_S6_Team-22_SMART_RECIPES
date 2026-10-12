package data;

import model.Recipe;

public class RecipeRepository {
    private Recipe[] recipes;
    private int size;

    public RecipeRepository() {
        recipes = new Recipe[32];
        size = 0;
        seed();
    }

    private void add(Recipe r) {
        if (size == recipes.length) {
            Recipe[] next = new Recipe[recipes.length * 2];
            for (int i = 0; i < size; i++) next[i] = recipes[i];
            recipes = next;
        }
        recipes[size++] = r;
    }

    private void seed() {
        add(new Recipe(1, "Tomato Rice", "Vegetarian", "Lunch", new String[]{"rice","tomato","onion","oil","salt"}, "Saute onion and tomato, add cooked rice and season well.", 4.5));
        add(new Recipe(2, "Paneer Butter Masala", "Vegetarian", "Dinner", new String[]{"paneer","tomato","onion","butter","cream","spices"}, "Cook onion and tomato gravy, add spices, paneer and finish with cream.", 4.8));
        add(new Recipe(3, "Egg Curry", "Non-Vegetarian", "Dinner", new String[]{"egg","tomato","onion","oil","chilli","spices"}, "Boil eggs, prepare onion tomato gravy and simmer eggs in the gravy.", 4.3));
        add(new Recipe(4, "Vegetable Pulao", "Vegetarian", "Lunch", new String[]{"rice","carrot","peas","beans","onion","spices"}, "Saute vegetables and spices, add rice and cook until fluffy.", 4.4));
        add(new Recipe(5, "Chicken Biryani", "Non-Vegetarian", "Lunch", new String[]{"rice","chicken","onion","tomato","yogurt","spices"}, "Marinate chicken, prepare masala, layer with rice and cook on low heat.", 4.9));
        add(new Recipe(6, "Masala Dosa", "Vegetarian", "Breakfast", new String[]{"rice","urad dal","potato","onion","salt"}, "Prepare dosa batter, make potato masala and cook dosa on a hot pan.", 4.6));
        add(new Recipe(7, "Chole", "Vegetarian", "Dinner", new String[]{"chickpeas","tomato","onion","ginger","garlic","spices"}, "Cook chickpeas and simmer with onion tomato masala and spices.", 4.5));
        add(new Recipe(8, "Vegetable Sandwich", "Vegetarian", "Breakfast", new String[]{"bread","tomato","onion","carrot","cucumber","cheese"}, "Layer vegetables and cheese between bread and toast until crisp.", 4.1));
        add(new Recipe(9, "Fried Rice", "Vegetarian", "Dinner", new String[]{"rice","carrot","peas","beans","soy sauce","onion"}, "Stir fry vegetables, add cooked rice and soy sauce and toss on high heat.", 4.2));
        add(new Recipe(10, "Omelette", "Non-Vegetarian", "Breakfast", new String[]{"egg","onion","tomato","salt","pepper"}, "Beat eggs with vegetables and cook on a lightly oiled pan.", 4.0));
        add(new Recipe(11, "Dal Tadka", "Vegetarian", "Lunch", new String[]{"dal","onion","tomato","garlic","cumin","oil"}, "Cook dal, prepare garlic cumin tadka and combine.", 4.7));
        add(new Recipe(12, "Aloo Gobi", "Vegetarian", "Lunch", new String[]{"potato","cauliflower","onion","tomato","turmeric","spices"}, "Saute potato and cauliflower with onion tomato masala until tender.", 4.4));
        add(new Recipe(13, "Chicken Curry", "Non-Vegetarian", "Dinner", new String[]{"chicken","onion","tomato","ginger","garlic","spices"}, "Brown chicken, add onion tomato masala and simmer until cooked.", 4.6));
        add(new Recipe(14, "Poha", "Vegetarian", "Breakfast", new String[]{"poha","onion","peanuts","potato","turmeric","lemon"}, "Soak poha briefly, temper spices and onion, then mix with poha and lemon.", 4.3));
        add(new Recipe(15, "Rajma Rice", "Vegetarian", "Lunch", new String[]{"rajma","rice","onion","tomato","ginger","spices"}, "Cook rajma in onion tomato gravy and serve with steamed rice.", 4.6));
    }

    public Recipe[] all() {
        Recipe[] out = new Recipe[size];
        for (int i = 0; i < size; i++) out[i] = recipes[i];
        return out;
    }

    public Recipe getById(int id) {
        for (int i = 0; i < size; i++) if (recipes[i].getId() == id) return recipes[i];
        return null;
    }

    public void addRecipe(Recipe recipe) { add(recipe); }
    public int size() { return size; }
}
