# Smart Recipes — DSA-3 Complete Console Project

A console-based Java project that applies the DSA-3 course outcomes to a practical
recipe-search and recommendation problem.

## Requirements

- JDK 17 or later
- VS Code + Extension Pack for Java (recommended)
- No external Java packages

## Run in VS Code — Windows

```text
javac -d bin (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
java -cp bin Main
```

## Run — macOS/Linux

```text
mkdir -p bin
javac -d bin $(find src -name "*.java")
java -cp bin Main
```

## Project design

The project has two layers:

1. **Smart Recipes application** — recommendations, search, fuzzy matching,
   similarity, filters, ratings, favorites, substitutions and recipe management.
2. **DSA-3 demonstration layer** — additional demonstrations that allow each
   course module to be shown without forcing unrelated algorithms into the main
   recipe workflow.

The user-facing console deliberately uses **feature-oriented names only**.
Algorithm names are kept in source-code comments, class names and this README so
that the console remains clean for a normal user.

## DSA-3 course mapping

### Module 1 — System / query engine

- Console query interface
- Recipe corpus
- Feature-to-algorithm dispatch
- Complexity notes and project documentation

### Module 2 — String algorithms

- KMP — exact recipe text search
- Z-function — alternate exact search
- Rabin-Karp with two rolling hashes — fast pattern search
- Aho-Corasick — multiple ingredient search
- Suffix array + Kasai LCP — recipe text similarity

### Module 3 — Advanced dynamic programming

- Levenshtein / Wagner-Fischer — fuzzy ingredient search
- Damerau-Levenshtein — transposition-aware fuzzy search
- Needleman-Wunsch — global sequence comparison
- Smith-Waterman — local sequence comparison
- Matrix-chain multiplication
- Optimal BST
- Bitmask TSP
- Hamiltonian path detection
- SOS DP
- Tree DP for independent set and diameter
- Rerooting for distance sums

### Module 4 — Network flow

- Augmenting-path maximum flow
- Dinic maximum flow
- Bipartite matching
- Minimum-cost maximum-flow assignment

### Module 5 — NP-completeness and approximation

- 3-SAT to CLIQUE construction
- SUBSET-SUM decision demonstration
- Vertex-cover 2-approximation using maximal matching
- Console explanations of P, NP, co-NP, PTAS, FPTAS, APX and FPT

### Module 6 — Randomised and parallel algorithms

- Randomised quicksort
- Miller-Rabin primality testing
- Universal hashing demonstration
- Reservoir sampling
- Reduce and prefix-scan reference operations
- Work/span discussion

## Main application features

- Ingredient-based recipe recommendation
- Exact recipe search
- Fuzzy ingredient search
- Recipe similarity
- Category and meal filters
- Recipe details and cooking instructions
- Favorites
- Ratings
- Add your own recipe
- Ingredient substitution helper

## Important implementation rule

The `engine` package intentionally does not import `java.util.*`. Core data
structures and algorithmic logic use primitive arrays and explicit loops. The
console entry point uses only `Scanner` for user input.

## Suggested faculty demonstration

1. Enter `rice,tomato,onion` and show ranked recipe recommendations.
2. Search for `tomato` and show exact matches.
3. Search for `panner` and show fuzzy matching of `paneer`.
4. Compare two recipe IDs and show their similarity score.
5. Open the planning, resource-assignment, difficult-problem, randomized and
   parallel labs to demonstrate the remaining course outcomes.

## Source-code style

Every algorithm class contains a short class-level comment explaining its role.
The core application methods are separated by feature and use descriptive
variable names. Algorithm names are not printed in normal console output.

## Client-facing interface

The normal console is intentionally written as a product interface. It does not
print DSA algorithm names, implementation class names, complexity labels, or
course-module terminology. The algorithms remain in the source tree and are
mapped in this README for faculty review.

Examples:

- `Search Recipes` uses the project's exact text-search implementation.
- `Find Recipes From a Misspelled Ingredient` uses edit-distance implementations.
- `Compare Two Recipes` uses the suffix-based similarity implementation.
- `Plan a Meal Efficiently`, `Match Ingredients to Recipes`, and
  `Find Useful Ingredient Combinations` exercise the advanced planning,
  assignment, and optimisation implementations without exposing their academic
  names to the end user.
- `Pick a Recipe for Me` and `Recipe Collection Insights` use the project's
  randomised and parallel utility implementations internally.
