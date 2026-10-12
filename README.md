# Smart Recipes — Aesthetic Website + DSA-3 Project

## Run the website
1. Extract the project folder.
2. Open `website/index.html` in a browser, or open the folder in VS Code and use the **Live Server** extension.
3. No npm packages or backend server are required for the website demo.

## Website features
- Beige/brown responsive design with a polished recipe discovery layout.
- Ingredient pantry matcher that ranks recipes by matching ingredients.
- Recipe search and filters (vegetarian, non-vegetarian, breakfast, quick).
- Recipe detail cards with ingredients and cooking instructions.
- Fuzzy ingredient search is represented in the companion Java console project; this website currently provides exact keyword search and ingredient matching.
- Save favourites, rate recipes, add custom recipes, ingredient substitution suggestions, and surprise recipe.
- Saved recipes, ratings, and added recipes persist in the current browser via localStorage.

## DSA-3 coverage: honest status
The companion Java project contains representative implementations from all six modules, but it does **not** implement every bullet in the full syllabus, and not every algorithm is necessarily integrated into the normal recipe workflow. Some are academic demonstrations. Review `DSA3_COURSE_MAPPING.md` before claiming coverage.

## Important implementation note
The website is a self-contained frontend demo. It does not currently call the Java console engine; browser actions use JavaScript and browser localStorage. The Java console project remains separate. Do not claim the website is connected to a Java backend unless a server/API integration is added.
