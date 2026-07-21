# ContainerParser: Recursive Cargo ID Extractor

A high-performance Kotlin utility designed to extract package IDs from nested cargo structures (e.g., Crate[Box[PKG-101], PKG-102]) using Structural Recursion and Clean Code principles.

##  Key Features

* **Divide & Conquer Strategy:** Implements recursive parsing to break down nested containers into manageable sub-problems.
* **Regex-Free Parsing:** Operates without `split()` or Regex, using precise character-level logic for maximum control and compliance with strict constraints.
* **Clean Code Architecture:**
  * **Small & Focused:** Functions follow the "Do One Thing" rule, keeping each logic block minimal and readable.
  * **Descriptive Naming:** Uses intent-revealing names to ensure the code reads like a top-down narrative.
  * **Safe Error Handling:** Replaces cryptic system errors with a custom `StructuralMismatchException` for handling unbalanced brackets gracefully.

##  How it Works (The Call Stack)

The parser leverages the JVM Call Stack to manage nesting levels:
* **Push:** Each `[` triggers a new recursive call, pushing a new Stack Frame to save the current state.
* **Base Case:** The recursion stops at `]` or the end of a segment, resolving the smallest unit of data.
* **Pop:** Resolved IDs are returned to the previous frame as it "pops" off the stack, flattening the final list.

##  Example

* **Input:** `Crate [ Box [ PKG-101 ] , PKG-102 ]`
* **Output:** `["PKG-101", "PKG-102"]`
