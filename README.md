# Container Parser 

A pure, structurally recursive parser built in Kotlin to process nested cargo packing strings and extract a flattened list of package IDs, without relying on global mutable state, regular expressions, or array-splitting routines.

---

##  Features

* **Whitespace Irregularity Handled:** Safely handles arbitrary padding and spaces around tokens and structural delimiters.
* **Structural Noise Filtering:** Filters out container keywords (e.g., `Crate`, `Box`), extracting only pure package IDs starting with `PKG-`.
* **Empty Brackets & Mismatched Elements Resilience:** Gracefully ignores empty containers (e.g., `Box[]`) and blank elements caused by consecutive commas (e.g., `,,`).
* **Custom Domain Exception:** Throws a descriptive `StructuralMismatchException` when structural or bracket imbalances occur, rather than crashing with standard out-of-bounds errors.
* **Pure Structural Recursion:** Built entirely using immutable parameters and divide-and-conquer recursive parsing patterns.

---

##  JVM Call Stack Operations & Lifecycle Trace

This implementation operates entirely on the JVM thread stack through a recursive state machine:

1. **Frame Allocation (Push):** Every time an opening bracket `[` or nested sequence is encountered, the recursive function invokes itself, pushing a new **Stack Frame** onto the JVM thread's execution stack. This frame securely isolates local variables, pointers, and return addresses.
2. **Immutable State Preservation:** By avoiding global mutable collections, all partial lists and states are passed forward immutably as function parameters, guaranteeing zero side-effects across execution branches.
3. **Base Case Resolution & Frame Destruction (Pop):** When reaching a terminating condition (such as a closing bracket `]` or end-of-string bounds), the active function computes its result and pops its frame off the JVM call stack, returning control and data upwards to the parent frame.
4. **Upward Aggregation:** Partial lists of package IDs are merged sequentially as frames unwind back to the root entry point.

---

##  Code Example & Usage

```kotlin
fun main() {
    val input = "Crate [ Box [ PKG-101 ] , PKG-102 ]"
    val result = ContainerParser.parse(input)
    println(result) // Output: [PKG-101, PKG-102]
}
