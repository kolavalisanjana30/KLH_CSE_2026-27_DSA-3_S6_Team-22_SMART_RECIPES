# DSA-3 Course Outcome Mapping — Smart Recipes

This is a practical mapping of representative topics, not a claim that every syllabus bullet is implemented.

| Module | Representative topic(s) in companion Java project | File(s) to inspect | Application relationship |
|---|---|---|---|
| 1 — System / query engine | Query dispatch, repository and complexity discussion | `src/Main.java`, `src/engine/SmartRecipeEngine.java`, `src/data/RecipeRepository.java` | Provides the query/application structure. The exact TextHack Indian-language Wikipedia corpus and citation-flow dataset are not part of this recipe dataset. |
| 2 — String algorithms | KMP, Z algorithm, Rabin–Karp, Aho–Corasick, suffix array and LCP-based similarity | `src/algorithms/KMP.java`, `ZAlgorithm.java`, `RabinKarp.java`, `AhoCorasick.java`, `SuffixArray.java`, `SimilarityEngine.java` | Directly relevant to exact search, multi-pattern search and text similarity. |
| 3 — Advanced DP | Levenshtein, Damerau–Levenshtein, Needleman–Wunsch, Smith–Waterman, matrix-chain, optimal BST, bitmask DP and extra DP demos | `src/algorithms/Levenshtein.java`, `DamerauLevenshtein.java`, `NeedlemanWunsch.java`, `SmithWaterman.java`, `MatrixChain.java`, `OptimalBST.java`, `BitmaskDP.java`, `DPExtras.java`, `TreeDP.java` | Edit distance is directly relevant to typo-tolerant ingredient search; several other algorithms are demonstrations rather than recipe features. |
| 4 — Network flow | Max flow, Dinic, min-cost max-flow, bipartite matching | `src/algorithms/MaxFlow.java`, `Dinic.java`, `MinCostMaxFlow.java`, `BipartiteMatching.java` | Demonstrates capacity-constrained assignment and matching. It should not be described as powering ordinary recipe search unless the engine explicitly calls it. |
| 5 — NP-completeness / approximation | Reduction demonstration and approximation/vertex-cover-style routines | `src/algorithms/ReductionDemo.java`, `Approximation.java`, `BitmaskDP.java` | Academic optimization demonstrations. Not all listed topics (e.g. every reduction, PTAS/FPTAS, parameterized complexity) are implemented as executable algorithms. |
| 6 — Randomized / parallel | Miller–Rabin, randomized quicksort, reservoir sampling, hashing and parallel primitives | `src/algorithms/MillerRabin.java`, `RandomizedQuickSort.java`, `ReservoirSampling.java`, `HashingDemo.java`, `ParallelPrimitives.java` | Demonstrates representative topics. Miller–Rabin and parallel primitives are academic demos, not required for core recipe recommendations. |

## Faculty explanation
The main application-linked concepts are:
1. **KMP / string matching** for keyword search (inspect call sites before claiming exact integration).
2. **Levenshtein edit distance** for typo-tolerant matching (inspect call sites before claiming exact integration).
3. **Suffix Array + LCP** for text similarity.
4. Sorting/ranking for ingredient-match recommendations.

Other algorithms are kept as course-outcome demonstrations. The normal user-facing interface should show product features rather than algorithm names.

## Constraint reminder
The course specification says `java.util.*` is forbidden inside the engine and students must hand-build algorithms. Check `SmartRecipeEngine.java` and all classes it invokes against that requirement before submission. This mapping does not certify compliance by itself.
