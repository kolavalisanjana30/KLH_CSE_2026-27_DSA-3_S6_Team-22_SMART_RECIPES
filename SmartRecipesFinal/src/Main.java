import data.RecipeRepository;
import engine.SmartRecipeEngine;

import java.util.Scanner;

/**
 * Console entry point for the Smart Recipes application.
 *
 * The console is written from a customer's point of view: it describes
 * useful recipe features rather than exposing implementation details.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SmartRecipeEngine engine = new SmartRecipeEngine(new RecipeRepository());

        engine.printBanner();

        boolean running = true;
        while (running) {
            engine.printMenu();
            System.out.print("Choose an option: ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from the menu.");
                continue;
            }

            if (choice == 0) {
                System.out.println("\nThank you for using Smart Recipes. Happy cooking!");
                running = false;
                continue;
            }

            try {
                handleChoice(choice, scanner, engine);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid value.");
            } catch (Exception e) {
                System.out.println("Sorry, that operation could not be completed.");
            }
        }

        scanner.close();
    }

    /** Routes a customer-facing menu option to the corresponding feature. */
    private static void handleChoice(int choice, Scanner scanner, SmartRecipeEngine engine) {
        switch (choice) {
            case 1:
                System.out.print("What ingredients do you have? (comma separated): ");
                engine.recommend(scanner.nextLine());
                break;

            case 2:
                System.out.print("What would you like to search for? ");
                engine.searchRecipes(scanner.nextLine());
                break;

            case 3:
                System.out.print("Enter ingredients to search for (comma separated): ");
                engine.searchByMultipleIngredients(scanner.nextLine());
                break;

            case 4:
                System.out.print("Enter an ingredient or recipe word: ");
                engine.fuzzySearch(scanner.nextLine());
                break;

            case 5:
                System.out.print("Enter the first recipe ID: ");
                int firstRecipe = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter the second recipe ID: ");
                int secondRecipe = Integer.parseInt(scanner.nextLine());
                engine.compareRecipes(firstRecipe, secondRecipe);
                break;

            case 6:
                System.out.print("Enter the first text or ingredient list: ");
                String firstText = scanner.nextLine();
                System.out.print("Enter the second text or ingredient list: ");
                String secondText = scanner.nextLine();
                engine.compareTexts(firstText, secondText);
                break;

            case 7:
                engine.mealPlanning();
                break;

            case 8:
                engine.ingredientAssignment();
                break;

            case 9:
                engine.combinationFinder();
                break;

            case 10:
                engine.quickRecipePick();
                break;

            case 11:
                engine.collectionInsights();
                break;

            case 12:
                System.out.println("\nRecipe Filters");
                System.out.print("Category (all/Vegetarian/Non-Vegetarian): ");
                String category = scanner.nextLine().trim();
                System.out.print("Meal (all/Breakfast/Lunch/Dinner): ");
                String meal = scanner.nextLine().trim();
                engine.filter(category, meal);
                break;

            case 13:
                System.out.print("Enter recipe ID: ");
                int recipeId = Integer.parseInt(scanner.nextLine());
                engine.details(recipeId);
                System.out.print("Action (f=favorite, r=rate, Enter=back): ");
                String action = scanner.nextLine().trim();

                if (action.equalsIgnoreCase("f")) {
                    engine.favorite(recipeId);
                } else if (action.equalsIgnoreCase("r")) {
                    System.out.print("Give this recipe a rating from 0 to 5: ");
                    engine.rate(recipeId, Double.parseDouble(scanner.nextLine()));
                }
                break;

            case 14:
                engine.showFavorites();
                break;

            case 15:
                addRecipe(scanner, engine);
                break;

            case 16:
                System.out.print("Which ingredient needs a substitute? ");
                engine.substitution(scanner.nextLine());
                break;

            case 17:
                engine.listAll();
                break;

            default:
                System.out.println("That option is not available. Please choose from the menu.");
        }
    }

    /** Collects the fields needed to create a new recipe. */
    private static void addRecipe(Scanner scanner, SmartRecipeEngine engine) {
        System.out.println("\nAdd a New Recipe");
        System.out.print("Recipe ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Recipe name: ");
        String name = scanner.nextLine();
        System.out.print("Category (Vegetarian/Non-Vegetarian): ");
        String category = scanner.nextLine();
        System.out.print("Meal type (Breakfast/Lunch/Dinner): ");
        String meal = scanner.nextLine();
        System.out.print("Ingredients (comma separated): ");
        String ingredients = scanner.nextLine();
        System.out.print("Cooking instructions: ");
        String instructions = scanner.nextLine();

        engine.addRecipe(id, name, category, meal, ingredients, instructions);
    }
}
