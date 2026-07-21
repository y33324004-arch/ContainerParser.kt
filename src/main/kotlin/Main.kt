package bytebloom

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/**
 * DOCUMENTATION: JVM CALL STACK OPERATIONS
 * 1. PUSH: Every time a '[' is encountered, the 'parseRecursive' function calls itself.
 *    The JVM pushes a new 'Stack Frame' onto the call stack to handle the nested content [1, 2].
 * 2. STATE PRESERVATION: The calling frame is paused, and all its local variables (like the current list)
 *    are saved in memory while the computer focuses on the new call [3].
 * 3. BASE CASE: When the function reaches ']' or the end of the string, it hits the "Base Case" [4, 5].
 * 4. POP: The current frame is "popped" from the stack, and its results are returned to the
 *    previous frame, which then resumes its execution [3, 6, 7].
 */

class StructuralMismatchException(message: String) : Exception(message)

object ContainerParser {

    /**
     * Entry point to parse the cargo packing string.
     */
    fun parse(input: String?): List<String> {
        if (input == null) return emptyList()

        // Quick verification of the basic balance of the arches to avoid unexpected errors
        var bracketBalance = 0
        for (char in input) {
            if (char == '[') bracketBalance++
            else if (char == ']') bracketBalance--
            if (bracketBalance < 0) {
                throw StructuralMismatchException("Invalid structure: Unbalanced closing bracket detected.")
            }
        }
        if (bracketBalance != 0) {
            throw StructuralMismatchException("Invalid structure: Unbalanced opening bracket detected.")
        }

        val (result, _) = parseRecursive(input, 0)
        return result
    }

    /**
     * Pure recursive function that mimics the "Divide and Conquer" strategy [9, 10].
     * Functions are kept small and focused on one level of abstraction [11, 12].
     */
    private fun parseRecursive(input: String, index: Int): Pair<List<String>, Int> {
        val results = mutableListOf<String>()
        var i = index

        while (i < input.length) {
            val char = input[i]

            when {
                char == '[' -> {
                    // Recursive Case: Reduce the problem by parsing the inner segment [5, 13]
                    val (nestedList, nextIndex) = parseRecursive(input, i + 1)
                    results.addAll(nestedList)
                    i = nextIndex
                }
                char == ']' -> {
                    // Base Case: Simplest case where recursion stops [4, 14]
                    return Pair(results, i + 1)
                }
                char == ',' || char.isWhitespace() -> {
                    i++ // Handle whitespace irregularity and structural commas
                }
                else -> {
                    // Handle words and structural noise
                    val (word, nextIndex) = extractWord(input, i)
                    // Only keep package IDs starting with "PKG-"
                    if (word.startsWith("PKG-")) {
                        results.add(word)
                    }
                    i = nextIndex
                }
            }
        }
        return Pair(results, i)
    }

    /**
     * Extracts a descriptive name or ID without using split() or Regex.
     * Follows the rule of "Doing One Thing" [15].
     */
    private fun extractWord(input: String, index: Int): Pair<String, Int> {
        var current = index
        val sb = StringBuilder()

        while (current < input.length && input[current] !in "[], ") {
            sb.append(input[current])
            current++
        }

        // Trim to handle arbitrary padding within the segment
        return Pair(sb.toString().trim(), current)
    }
}

fun main() {
    val input = "Crate [ Box [ PKG-101 ] , PKG-102 ]"
    val result = ContainerParser.parse(input)
    println(result)
}