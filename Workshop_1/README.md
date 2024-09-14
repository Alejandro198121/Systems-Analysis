# Workshop 1: Genetic Sequence Analysis

## **Summary**

In this workshop, a system was developed to manage, generate, and analyze genetic sequences. The system was built using the **Model-View-Controller (MVC)** architecture, ensuring separation between the user interface, the underlying logic, and the control flow. The key functionalities include:

- Generating random DNA sequences with random probabilities for bases A, C, G, and T.
- Calculating the **entropy** of each sequence to assess the randomness within each set of nucleotides.
- Filtering sequences based on a user-defined entropy threshold.
- Detecting the **most frequent motif** of a specified size from the sequences.
- Ensuring **persistence** of data by saving and loading sequences from a file.

## **Contents**

- **`GeneticSequenceManager.java`**: The core logic for managing genetic sequences, generating them with random probabilities, calculating entropy, filtering by entropy, and detecting motifs.
- **`Controller.java`**: Coordinates user input, calls the appropriate model methods, and manages the workflow of the system.
- **`VistaConsola.java`**: Handles user interaction through the console and displays the results of sequence generation and analysis.
- **`Archivo.java`**: Manages saving and loading the sequence data to and from a file for persistence.
- **`Informe_Workshop_1.pdf`**: A detailed report including system analysis, complexity analysis, chaos analysis, results, discussion, and conclusions.
- **`README.md`** (this file): A summary of the workshop's content and its development.

## **How to Run**

1. Clone this repository and navigate to the `Workshop_1` folder.
2. Compile the Java files.
3. Run the `AplMain` class to start interacting with the system.
4. You can choose between using an existing database (from the `.txt` file) or generating new sequences.

## **Additional Information**

For a detailed explanation of the algorithms, their complexity, and the results obtained, refer to the `Informe_Workshop_1.pdf` document. It includes sections on system analysis, results, and conclusions drawn from the execution of the project.
