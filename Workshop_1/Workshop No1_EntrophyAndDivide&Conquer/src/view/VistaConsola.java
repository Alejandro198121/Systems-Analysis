package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The VistaConsola class handles user interaction via the console.
 * It provides methods for displaying information, reading user input,
 * and showing sequences with their corresponding entropy.
 */
public class VistaConsola {

    private Scanner scanner;

    /**
     * Constructor initializes the scanner for reading user input.
     */
    public VistaConsola() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays a message to the user.
     *
     * @param mensaje The message to display.
     */
    public void mostrarInformacion(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Prompts the user for an integer input and returns the entered value.
     *
     * @param mensaje The message to display to the user before reading the integer.
     * @return The integer value entered by the user.
     */
    public int leerDatoEntero(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextInt();
    }

    /**
     * Prompts the user for a double input and returns the entered value.
     *
     * @param mensaje The message to display to the user before reading the double.
     * @return The double value entered by the user.
     */
    public double leerDatoDouble(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextDouble();
    }

    /**
     * Prompts the user to input probabilities for bases A, C, G, and T,
     * and returns these probabilities in an array.
     *
     * @return An array containing the probabilities of bases A, C, G, and T in that order.
     */
    public double[] getProbabilidadesBases() {
        double[] probabilidades = new double[4];
        System.out.print("Ingrese la probabilidad de A: ");
        probabilidades[0] = scanner.nextDouble();
        System.out.print("Ingrese la probabilidad de C: ");
        probabilidades[1] = scanner.nextDouble();
        System.out.print("Ingrese la probabilidad de G: ");
        probabilidades[2] = scanner.nextDouble();
        System.out.print("Ingrese la probabilidad de T: ");
        probabilidades[3] = scanner.nextDouble();
        return probabilidades;
    }

    /**
     * Displays sequences along with their entropy values.
     *
     * @param secuencias A map where the key is the sequence ID and the value is the sequence string.
     * @param entropias A map where the key is the sequence ID and the value is the entropy of the sequence.
     */
    public void mostrarSecuenciasConEntropia(HashMap<Integer, String> secuencias, Map<Integer, Double> entropias) {
        for (Integer id : secuencias.keySet()) {
            String secuencia = secuencias.get(id);
            double entropia = entropias.get(id);
            System.out.println("ID: " + id + " - Secuencia: " + secuencia + " - Entropía: " + entropia);
        }
    }
}
