package model;

public class Recipe {
    private final int id;
    private final String name;
    private final String category;
    private final String mealType;
    private final String[] ingredients;
    private final String instructions;
    private double rating;

    public Recipe(int id, String name, String category, String mealType, String[] ingredients, String instructions, double rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.rating = rating;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getMealType() { return mealType; }
    public String[] getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String document() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(' ');
        for (String s : ingredients) sb.append(s).append(' ');
        sb.append(instructions);
        return sb.toString().toLowerCase();
    }

    public boolean hasIngredient(String query) {
        String q = query.trim().toLowerCase();
        for (String ingredient : ingredients) {
            if (ingredient.toLowerCase().contains(q)) return true;
        }
        return false;
    }

    public String toString() {
        return id + ". " + name + " | " + category + " | " + mealType + " | Rating: " + rating;
    }
}
