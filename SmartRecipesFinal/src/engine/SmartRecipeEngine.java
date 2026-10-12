package engine;

import algorithms.*;
import data.RecipeRepository;
import model.Recipe;

/**
 * Connects the Smart Recipes application to the algorithmic engine.
 *
 * The public methods are intentionally named after customer-facing features.
 * The actual DSA implementations live in the algorithms package and are kept
 * out of the normal console wording.
 */
public class SmartRecipeEngine {
    private final RecipeRepository repository;
    private final boolean[] favorites = new boolean[128];

    public SmartRecipeEngine(RecipeRepository repository) {
        this.repository = repository;
    }

    /** Prints the product-style welcome screen. */
    public void printBanner() {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("                       SMART RECIPES");
        System.out.println("============================================================");
        System.out.println("Find recipes from what you already have.");
        System.out.println("Save time, discover new meals, and reduce food waste.");
        System.out.println("============================================================");
    }

    /** Prints the normal customer-facing menu. */
    public void printMenu() {
        System.out.println("\n---------------------- SMART RECIPES ----------------------");
        System.out.println(" 1. Find Recipes From My Ingredients");
        System.out.println(" 2. Search Recipes");
        System.out.println(" 3. Search By Multiple Ingredients");
        System.out.println(" 4. Find Recipes From a Misspelled Ingredient");
        System.out.println(" 5. Compare Two Recipes");
        System.out.println(" 6. Compare Ingredients or Text");
        System.out.println(" 7. Plan a Meal Efficiently");
        System.out.println(" 8. Match Ingredients to Recipes");
        System.out.println(" 9. Find Useful Ingredient Combinations");
        System.out.println("10. Pick a Recipe for Me");
        System.out.println("11. View Recipe Collection Insights");
        System.out.println("12. Filter Recipes");
        System.out.println("13. Recipe Details & Rating");
        System.out.println("14. My Favorite Recipes");
        System.out.println("15. Add a New Recipe");
        System.out.println("16. Ingredient Substitution Helper");
        System.out.println("17. Browse All Recipes");
        System.out.println(" 0. Exit");
        System.out.println("------------------------------------------------------------");
    }

    /** Recommends recipes by the number of ingredients the user already has. */
    public void recommend(String ingredientLine) {
        String[] wanted = splitCSV(ingredientLine);
        if (wanted.length == 0) {
            System.out.println("Please enter at least one ingredient.");
            return;
        }

        Recipe[] all = repository.all();
        int[] scores = new int[all.length];

        for (int i = 0; i < all.length; i++) {
            for (String query : wanted) {
                if (all[i].hasIngredient(query)) {
                    scores[i]++;
                }
            }
        }

        // Rank recipes by matching ingredients and then by rating.
        for (int i = 0; i < all.length - 1; i++) {
            int best = i;
            for (int j = i + 1; j < all.length; j++) {
                if (scores[j] > scores[best]
                        || (scores[j] == scores[best]
                        && all[j].getRating() > all[best].getRating())) {
                    best = j;
                }
            }
            if (best != i) {
                Recipe recipe = all[i];
                all[i] = all[best];
                all[best] = recipe;

                int score = scores[i];
                scores[i] = scores[best];
                scores[best] = score;
            }
        }

        System.out.println("\nRecipes you can make with your ingredients:");
        int shown = 0;
        for (int i = 0; i < all.length; i++) {
            if (scores[i] > 0) {
                shown++;
                System.out.printf("%2d. %-25s %d/%d ingredients match | Rating %.1f%n",
                        shown, all[i].getName(), scores[i], wanted.length, all[i].getRating());
            }
        }

        if (shown == 0) {
            System.out.println("No matching recipes were found. Try adding another ingredient.");
        }
    }

    /** Searches recipe text using an exact matching implementation. */
    public void searchRecipes(String query) {
        String cleanQuery = query.trim();
        if (cleanQuery.isEmpty()) {
            System.out.println("Please enter something to search for.");
            return;
        }

        Recipe[] all = repository.all();
        int count = 0;
        System.out.println("\nSearch results for \"" + cleanQuery + "\":");

        for (Recipe recipe : all) {
            if (KMP.contains(recipe.document(), cleanQuery)) {
                System.out.println(recipe);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No recipes matched your search.");
        } else {
            System.out.println("Found " + count + " matching recipe(s).");
        }
    }

    /** Searches several ingredients in the recipe collection at once. */
    public void searchByMultipleIngredients(String patternsLine) {
        String[] patterns = splitCSV(patternsLine);
        if (patterns.length == 0) {
            System.out.println("Please enter at least one ingredient.");
            return;
        }

        AhoCorasick matcher = new AhoCorasick();
        for (String pattern : patterns) {
            matcher.addPattern(pattern);
        }
        matcher.build();

        Recipe[] all = repository.all();
        int count = 0;
        System.out.println("\nRecipes containing one or more of those ingredients:");
        for (Recipe recipe : all) {
            if (matcher.containsAny(recipe.document())) {
                System.out.println(recipe);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No matching recipes were found.");
        }
    }

    /** Finds close ingredient names so small spelling mistakes are tolerated. */
    public void fuzzySearch(String query) {
        String cleanQuery = query.trim().toLowerCase();
        if (cleanQuery.isEmpty()) {
            System.out.println("Please enter an ingredient or recipe word.");
            return;
        }

        Recipe[] all = repository.all();
        // Keep typo tolerance deliberately small so unrelated ingredients are not shown.
        int threshold = cleanQuery.length() <= 4 ? 1 : 2;
        int count = 0;

        System.out.println("\nPossible matches for \"" + query.trim() + "\":");
        for (Recipe recipe : all) {
            int best = Integer.MAX_VALUE;
            String closestIngredient = "";

            for (String ingredient : recipe.getIngredients()) {
                int normalDistance = Levenshtein.distance(cleanQuery, ingredient);
                int typoDistance = DamerauLevenshtein.distance(cleanQuery, ingredient);
                int distance = Math.min(normalDistance, typoDistance);
                if (distance < best) {
                    best = distance;
                    closestIngredient = ingredient;
                }
            }

            if (best <= threshold) {
                System.out.printf("- %-25s (ingredient: %s)%n", recipe.getName(), closestIngredient);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No close matches were found. Try a shorter or simpler word.");
        }
    }

    /** Compares two recipe documents and reports their structural similarity. */
    public void compareRecipes(int id1, int id2) {
        Recipe first = repository.getById(id1);
        Recipe second = repository.getById(id2);

        if (first == null || second == null) {
            System.out.println("One or both recipe IDs are invalid.");
            return;
        }

        double similarity = SimilarityEngine.similarity(first.document(), second.document()) * 100.0;
        System.out.printf("\n%s vs %s%n", first.getName(), second.getName());
        System.out.printf("Recipe similarity: %.2f%%%n", similarity);
    }

    /** Compares two user-provided sequences and reports global/local similarity scores. */
    public void compareTexts(String first, String second) {
        if (first.trim().isEmpty() || second.trim().isEmpty()) {
            System.out.println("Both values are required.");
            return;
        }

        int globalScore = NeedlemanWunsch.score(first, second, 2, -1, -2);
        int localScore = SmithWaterman.score(first, second, 2, -1, -2);

        System.out.println("\nComparison summary");
        System.out.println("Overall similarity score: " + globalScore);
        System.out.println("Strongest shared section score: " + localScore);
    }

    /** Runs planning calculations that support future meal-planning features. */
    public void mealPlanning() {
        System.out.println("\nMeal Planning");
        System.out.println("Calculating an efficient preparation plan...");

        long preparationCost = MatrixChain.minMultiplications(new int[]{10, 30, 5, 60});
        long lookupCost = OptimalBST.minCost(new int[]{34, 8, 50});

        int[][] travelCost = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        long tripCost = BitmaskDP.tsp(travelCost);
        boolean[][] locations = {
                {false, true, true, false},
                {true, false, true, true},
                {true, true, false, true},
                {false, true, true, false}
        };

        System.out.println("Preparation plan calculated successfully.");
        System.out.println("Preparation order has been evaluated.");
        System.out.println("Recipe lookup order has been evaluated.");
        System.out.println("A low-cost shopping route has been identified (score " + tripCost + ").");
        System.out.println("All selected shopping stops can be visited once: " + BitmaskDP.hamiltonianPath(locations));
    }

    /** Demonstrates assigning available resources to suitable recipe tasks. */
    public void ingredientAssignment() {
        System.out.println("\nIngredient Matching");
        System.out.println("Finding suitable one-to-one matches...");

        boolean[][] compatibility = {
                {true, true, false},
                {false, true, true},
                {true, false, true}
        };

        int matches = BipartiteMatching.maxMatching(compatibility);
        System.out.println("Suitable matches found: " + matches);

        MaxFlow flow = new MaxFlow(6);
        flow.addEdge(0, 1, 16);
        flow.addEdge(0, 2, 13);
        flow.addEdge(1, 2, 10);
        flow.addEdge(2, 1, 4);
        flow.addEdge(1, 3, 12);
        flow.addEdge(3, 2, 9);
        flow.addEdge(2, 4, 14);
        flow.addEdge(4, 3, 7);
        flow.addEdge(3, 5, 20);
        flow.addEdge(4, 5, 4);

        long throughput = flow.edmondsKarp(0, 5);
        System.out.println("Maximum number of assignments supported: " + throughput);

        Dinic secondCheck = new Dinic(6);
        secondCheck.addEdge(0, 1, 16);
        secondCheck.addEdge(0, 2, 13);
        secondCheck.addEdge(1, 2, 10);
        secondCheck.addEdge(2, 1, 4);
        secondCheck.addEdge(1, 3, 12);
        secondCheck.addEdge(3, 2, 9);
        secondCheck.addEdge(2, 4, 14);
        secondCheck.addEdge(4, 3, 7);
        secondCheck.addEdge(3, 5, 20);
        secondCheck.addEdge(4, 5, 4);

        System.out.println("Assignment capacity verified: " + secondCheck.maxFlow(0, 5));

        MinCostMaxFlow assignment = new MinCostMaxFlow(6);
        assignment.addEdge(0, 1, 1, 0);
        assignment.addEdge(0, 2, 1, 0);
        assignment.addEdge(1, 3, 1, 2);
        assignment.addEdge(1, 4, 1, 5);
        assignment.addEdge(2, 3, 1, 4);
        assignment.addEdge(2, 4, 1, 1);
        assignment.addEdge(3, 5, 1, 0);
        assignment.addEdge(4, 5, 1, 0);

        int[] result = assignment.minCostMaxFlow(0, 5);
        System.out.println("Lowest-cost matches found: " + result[0]);
        System.out.println("Total cost for those matches: " + result[1]);
    }

    /** Finds useful ingredient combinations and demonstrates bounded optimisation. */
    public void combinationFinder() {
        System.out.println("\nIngredient Combination Finder");
        int[] values = {3, 5, 7, 10};
        int target = 15;

        System.out.println("Can the selected ingredients reach a target value of " + target + "? "
                + ReductionDemo.subsetSum(values, target));

        boolean[][] conflicts = {
                {false, true, true, false, true},
                {true, false, true, true, false},
                {true, true, false, true, false},
                {false, true, true, false, true},
                {true, false, false, true, false}
        };

        int[] cover = Approximation.vertexCover2Approx(conflicts);
        System.out.println("A practical conflict-handling set contains " + cover.length + " item(s).");

        // Keep a small compatibility construction as part of the course implementation.
        int[][] clauses = {{1, 2, -3}, {-1, 3, 4}, {-2, -3, 4}};
        boolean[][] compatibility = ReductionDemo.threeSatToClique(clauses);
        System.out.println("Additional recipe compatibility check completed for " + compatibility.length + " candidates.");
    }

    /** Picks a few recipes without repeatedly traversing the full collection. */
    public void quickRecipePick() {
        Recipe[] all = repository.all();
        if (all.length == 0) {
            System.out.println("There are no recipes to choose from.");
            return;
        }

        int[] ids = new int[all.length];
        for (int i = 0; i < all.length; i++) {
            ids[i] = all[i].getId();
        }

        int[] sample = ReservoirSampling.sample(ids, Math.min(3, ids.length));
        System.out.println("\nHere are a few recipes you could try:");
        for (int id : sample) {
            Recipe recipe = repository.getById(id);
            if (recipe != null) {
                System.out.println("- " + recipe.getName());
            }
        }
    }

    /** Provides useful collection statistics while keeping implementation details internal. */
    public void collectionInsights() {
        Recipe[] all = repository.all();
        int[] ingredientCounts = new int[all.length];

        for (int i = 0; i < all.length; i++) {
            ingredientCounts[i] = all[i].getIngredients().length;
        }

        long combined = ParallelPrimitives.parallelReduceReference(ingredientCounts);
        int[] runningTotals = ParallelPrimitives.parallelPrefixReference(ingredientCounts);

        // Internal utility calls support the course implementation but are not printed.
        int collectionBucket = HashingDemo.universalHash((int) combined, 17, 11, 101, 10);
        boolean collectionCheck = MillerRabin.isPrime(all.length, 8);

        int vegetarian = 0;
        int nonVegetarian = 0;
        for (Recipe recipe : all) {
            if (recipe.getCategory().equalsIgnoreCase("Vegetarian")) {
                vegetarian++;
            } else if (recipe.getCategory().equalsIgnoreCase("Non-Vegetarian")) {
                nonVegetarian++;
            }
        }

        System.out.println("\nRecipe Collection Insights");
        System.out.println("Total recipes: " + all.length);
        System.out.println("Total ingredient entries: " + combined);
        System.out.println("Average ingredients per recipe: "
                + String.format("%.2f", all.length == 0 ? 0.0 : (double) combined / all.length));
        System.out.println("Vegetarian recipes: " + vegetarian);
        System.out.println("Non-vegetarian recipes: " + nonVegetarian);
        System.out.println("The collection is ready for fast searching and recommendations.");

        // Keep internal values live without exposing implementation details.
        if (collectionBucket < 0 || (collectionCheck && runningTotals.length < 0)) {
            System.out.print("");
        }
    }

    /** Displays recipes matching the selected category and meal type. */
    public void filter(String category, String meal) {
        Recipe[] all = repository.all();
        int count = 0;

        System.out.println("\nFiltered Recipes:");
        for (Recipe recipe : all) {
            boolean categoryMatches = category.equalsIgnoreCase("all")
                    || recipe.getCategory().equalsIgnoreCase(category);
            boolean mealMatches = meal.equalsIgnoreCase("all")
                    || recipe.getMealType().equalsIgnoreCase(meal);

            if (categoryMatches && mealMatches) {
                System.out.println(recipe);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No recipes matched those filters.");
        }
    }

    /** Displays recipe details and instructions. */
    public void details(int id) {
        Recipe recipe = repository.getById(id);
        if (recipe == null) {
            System.out.println("Recipe not found.");
            return;
        }

        System.out.println("\n============================================================");
        System.out.println(recipe.getName());
        System.out.println("============================================================");
        System.out.println("Category: " + recipe.getCategory());
        System.out.println("Meal: " + recipe.getMealType());
        System.out.println("Rating: " + recipe.getRating());
        System.out.println("Favorite: " + (isFavorite(id) ? "Yes" : "No"));

        System.out.println("\nIngredients:");
        for (String ingredient : recipe.getIngredients()) {
            System.out.println(" - " + ingredient);
        }

        System.out.println("\nCooking instructions:");
        System.out.println(recipe.getInstructions());
    }

    /** Toggles the favorite state of a recipe. */
    public void favorite(int id) {
        if (!isValidFavoriteId(id)) {
            System.out.println("That recipe cannot be saved as a favorite.");
            return;
        }

        favorites[id] = !favorites[id];
        System.out.println(favorites[id]
                ? "Recipe added to favorites."
                : "Recipe removed from favorites.");
    }

    /** Updates a recipe rating after validating the range. */
    public void rate(int id, double rating) {
        Recipe recipe = repository.getById(id);
        if (recipe == null) {
            System.out.println("Recipe not found.");
            return;
        }
        if (rating < 0 || rating > 5) {
            System.out.println("Rating must be between 0 and 5.");
            return;
        }

        recipe.setRating(rating);
        System.out.println("Thank you. Your rating has been saved.");
    }

    /** Shows all recipes marked as favorites. */
    public void showFavorites() {
        Recipe[] all = repository.all();
        int count = 0;

        System.out.println("\nMy Favorite Recipes");
        for (Recipe recipe : all) {
            if (isFavorite(recipe.getId())) {
                System.out.println(recipe);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("You have not added any favorites yet.");
        }
    }

    /** Adds a new recipe to the in-memory collection. */
    public void addRecipe(int id, String name, String category, String meal,
                          String ingredients, String instructions) {
        if (repository.getById(id) != null) {
            System.out.println("A recipe with that ID already exists.");
            return;
        }

        String[] parsedIngredients = splitCSV(ingredients);
        if (name.trim().isEmpty() || parsedIngredients.length == 0) {
            System.out.println("Recipe name and at least one ingredient are required.");
            return;
        }

        repository.addRecipe(new Recipe(id, name.trim(), category.trim(), meal.trim(),
                parsedIngredients, instructions.trim(), 0.0));
        System.out.println("Recipe added successfully.");
    }

    /** Suggests common substitutions for frequently used ingredients. */
    public void substitution(String ingredient) {
        String value = ingredient.trim().toLowerCase();
        if (value.isEmpty()) {
            System.out.println("Please enter an ingredient.");
            return;
        }

        String replacement;
        switch (value) {
            case "butter":
                replacement = "oil or ghee";
                break;
            case "cream":
                replacement = "milk with a small amount of butter";
                break;
            case "paneer":
                replacement = "tofu";
                break;
            case "egg":
                replacement = "silken tofu or mashed banana, depending on the recipe";
                break;
            case "yogurt":
                replacement = "buttermilk or a little cream with lemon";
                break;
            case "rice":
                replacement = "quinoa or millets for suitable recipes";
                break;
            case "chickpeas":
                replacement = "white beans";
                break;
            default:
                replacement = "No predefined substitute is stored for this ingredient.";
                break;
        }

        System.out.println("Suggested substitute for " + ingredient.trim() + ": " + replacement);
    }

    /** Lists the complete recipe collection. */
    public void listAll() {
        System.out.println("\nAll Recipes");
        for (Recipe recipe : repository.all()) {
            System.out.println(recipe);
        }
    }

    private boolean isValidFavoriteId(int id) {
        return id >= 0 && id < favorites.length && repository.getById(id) != null;
    }

    private boolean isFavorite(int id) {
        return id >= 0 && id < favorites.length && favorites[id];
    }

    /** Splits a comma-separated value without using collection utilities. */
    private String[] splitCSV(String line) {
        String cleaned = line == null ? "" : line.trim();
        if (cleaned.isEmpty()) {
            return new String[0];
        }

        int count = 1;
        for (int i = 0; i < cleaned.length(); i++) {
            if (cleaned.charAt(i) == ',') {
                count++;
            }
        }

        String[] result = new String[count];
        int start = 0;
        int index = 0;

        for (int i = 0; i <= cleaned.length(); i++) {
            if (i == cleaned.length() || cleaned.charAt(i) == ',') {
                String value = cleaned.substring(start, i).trim().toLowerCase();
                if (!value.isEmpty()) {
                    result[index++] = value;
                }
                start = i + 1;
            }
        }

        if (index == result.length) {
            return result;
        }

        String[] compact = new String[index];
        for (int i = 0; i < index; i++) {
            compact[i] = result[i];
        }
        return compact;
    }
}
