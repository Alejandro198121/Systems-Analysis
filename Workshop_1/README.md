# Genetic Sequence Analysis Project

## Summary

This project is a comprehensive solution for analyzing genetic sequences. It includes functionalities for generating artificial genetic sequences, detecting motifs, filtering sequences based on entropy, and managing sequence data using file operations. The project is structured into several classes, each responsible for different aspects of the application, including user interaction, data management, and sequence analysis.

## Contents

- **`AplMain.java`**: The main class that initializes and runs the application.
- **`Controller.java`**: Handles the core logic of the application, including menu interactions and invoking methods from other classes.
- **`Archivo.java`**: Manages file operations for saving and loading sequence data.
- **`GeneticSequenceManager.java`**: Manages the genetic sequences, including generation, entropy calculation, and motif detection.
- **`VistaConsola.java`**: Provides methods for user interaction via the console, including input and output operations.

## Features

- **Generate Artificial Genetic Sequences**: Create sequences with user-defined parameters, including sequence count and length.
- **Load and Save Sequences**: Save generated sequences to a file and load them for further analysis.
- **Filter Sequences by Entropy**: Remove sequences with low entropy to ensure diversity and randomness.
- **Detect and Display Motifs**: Identify and display recurring patterns (motifs) in the genetic sequences.
- **Interactive Console Interface**: User-friendly menu system for interacting with the application via the console.

## Requirements

- **Java Development Kit (JDK)**: Version 8 or later.
- **Basic Understanding**: Knowledge of genetic sequences and entropy concepts is helpful but not required.

## How to Run

1. Ensure you have Java installed on your machine.
2. Compile all Java files:
   ```bash
   javac controller/AplMain.java controller/Controller.java model/Archivo.java model/GeneticSequenceManager.java view/VistaConsola.java
