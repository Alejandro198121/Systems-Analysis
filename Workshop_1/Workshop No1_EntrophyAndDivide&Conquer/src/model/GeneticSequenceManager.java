package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GeneticSequenceManager {
	private HashMap<Integer, String> database;
	private Random random;

	/**
	 * Constructor initializes the database and random number generator.
	 */
	public GeneticSequenceManager() {
		database = new HashMap<>();
		random = new Random();
	}

	/**
	 * Generates random probabilities for A, C, G, and T that sum up to 1.
	 *
	 * @return An array containing the probabilities for bases A, C, G, and T.
	 */
	private double[] generarProbabilidadesAleatorias() {
		double pA = random.nextDouble();
		double pC = random.nextDouble();
		double pG = random.nextDouble();
		double pT = random.nextDouble();

		// Normalize to sum up to 1
		double suma = pA + pC + pG + pT;
		return new double[] { pA / suma, pC / suma, pG / suma, pT / suma };
	}

	/**
	 * Generates random sequences and stores them in the database.
	 *
	 * @param n The number of sequences to generate.
	 * @param m The length of each sequence.
	 */
	public void generarSecuencias(int n, int m) {
		double[] probabilidades = generarProbabilidadesAleatorias();
		double pA = probabilidades[0];
		double pC = probabilidades[1];
		double pG = probabilidades[2];
		double pT = probabilidades[3];

		for (int i = 0; i < n; i++) {
			StringBuilder secuencia = new StringBuilder();
			for (int j = 0; j < m; j++) {
				double probabilidad = random.nextDouble();
				if (probabilidad < pA) {
					secuencia.append('A');
				} else if (probabilidad < pA + pC) {
					secuencia.append('C');
				} else if (probabilidad < pA + pC + pG) {
					secuencia.append('G');
				} else {
					secuencia.append('T');
				}
			}
			database.put(i, secuencia.toString());
		}
	}

	/**
	 * Validates the range of the number of sequences (1000 <= n <= 2000000).
	 *
	 * @param n The number of sequences to validate.
	 * @return The validated number of sequences.
	 * @throws IllegalArgumentException if n is not in the valid range.
	 */
	public int validarNumeroDeSecuencias(int n) {
		if (n < 1000 || n > 2000000) {
			throw new IllegalArgumentException("El número de secuencias debe estar entre 1000 y 2000000.");
		}
		return n;
	}

	/**
	 * Validates the length of the sequences (5 <= m <= 100).
	 *
	 * @param m The length of sequences to validate.
	 * @return The validated length of sequences.
	 * @throws IllegalArgumentException if m is not in the valid range.
	 */
	public int validarTamañoSecuencias(int m) {
		if (m < 5 || m > 100) {
			throw new IllegalArgumentException("El tamaño de las secuencias debe estar entre 5 y 100.");
		}
		return m;
	}

	/**
	 * Validates the size of the motif (4 <= s <= 10).
	 *
	 * @param s The size of the motif to validate.
	 * @return The validated size of the motif.
	 * @throws IllegalArgumentException if s is not in the valid range.
	 */
	public int validarTamañoMotivo(int s) {
		if (s < 4 || s > 10) {
			throw new IllegalArgumentException("El tamaño del motivo debe estar entre 4 y 10.");
		}
		return s;
	}

	/**
	 * Generates random sequences with specified probabilities for A, C, G, and T.
	 *
	 * @param n  The number of sequences to generate.
	 * @param m  The length of each sequence.
	 * @param pA The probability of base A.
	 * @param pC The probability of base C.
	 * @param pG The probability of base G.
	 * @param pT The probability of base T.
	 */
	public void generarSecuencias(int n, int m, double pA, double pC, double pG, double pT) {
		double sumaProbabilidades = pA + pC + pG + pT;
		pA /= sumaProbabilidades;
		pC /= sumaProbabilidades;
		pG /= sumaProbabilidades;
		pT /= sumaProbabilidades;

		Random random = new Random();
		for (int i = 0; i < n; i++) {
			StringBuilder secuencia = new StringBuilder();
			for (int j = 0; j < m; j++) {
				double probabilidad = random.nextDouble();
				if (probabilidad < pA) {
					secuencia.append('A');
				} else if (probabilidad < pA + pC) {
					secuencia.append('C');
				} else if (probabilidad < pA + pC + pG) {
					secuencia.append('G');
				} else {
					secuencia.append('T');
				}
			}
			database.put(i, secuencia.toString());
		}
	}

	/**
	 * Calculates the entropy of a given sequence.
	 *
	 * @param sequence The sequence for which to calculate entropy.
	 * @return The entropy of the sequence.
	 */
	public double calcularEntropia(String secuencia) {
		int length = secuencia.length();
		if (length == 0)
			return 0.0;

		Map<Character, Integer> conteo = new HashMap<>();
		for (char base : secuencia.toCharArray()) {
			conteo.put(base, conteo.getOrDefault(base, 0) + 1);
		}

		double entropia = 0.0;
		for (int frecuencia : conteo.values()) {
			double probabilidad = (double) frecuencia / length;
			entropia -= probabilidad * (Math.log(probabilidad) / Math.log(2)); // Log base 2
		}
		return entropia;
	}

	/**
	 * Filters sequences based on an entropy threshold.
	 *
	 * @param threshold The entropy threshold for filtering.
	 */
	public void filtrarPorEntropia(double umbral) {
		database.entrySet().removeIf(entry -> calcularEntropia(entry.getValue()) < umbral);
	}

	/**
	 * Detects the most frequent motif of a given size.
	 *
	 * @param s The size of the motif to detect.
	 * @return The most frequent motif.
	 */
	public String detectarMotivo(int s) {
		HashMap<String, Integer> conteoMotivos = new HashMap<>();
		for (String secuencia : database.values()) {
			for (int i = 0; i <= secuencia.length() - s; i++) {
				String subsecuencia = secuencia.substring(i, i + s);
				conteoMotivos.put(subsecuencia, conteoMotivos.getOrDefault(subsecuencia, 0) + 1);
			}
		}

		String motivoFrecuente = null;
		int maxOcurrencias = 0;
		for (Map.Entry<String, Integer> entry : conteoMotivos.entrySet()) {
			String motivo = entry.getKey();
			int ocurrencias = entry.getValue();
			if (ocurrencias > maxOcurrencias) {
				maxOcurrencias = ocurrencias;
				motivoFrecuente = motivo;
			}
		}
		return motivoFrecuente;
	}

	/**
	 * Retrieves the database of sequences.
	 *
	 * @return A map containing the database of sequences with IDs.
	 */
	public HashMap<Integer, String> getDatabase() {
		return database;
	}

	/**
	 * Loads data from a file into the database.
	 */
	public void cargarDesdeArchivo() {
		try {
			database = Archivo.cargarDatos();
		} catch (IOException e) {
			System.out.println("Error al cargar los datos desde el archivo: " + e.getMessage());
		}
	}

	/**
	 * Saves the database to a file.
	 */
	public void guardarEnArchivo() {
		try {
			Archivo.guardarDatos(database);
		} catch (IOException e) {
			System.out.println("Error al guardar los datos en el archivo: " + e.getMessage());
		}
	}

	/**
	 * Retrieves sequences along with their entropy values.
	 *
	 * @return A map where the key is the sequence ID and the value is the entropy
	 *         of the sequence.
	 */
	public Map<Integer, Double> getSecuenciasConEntropia() {
		Map<Integer, Double> secuenciasConEntropia = new HashMap<>();
		for (Map.Entry<Integer, String> entry : database.entrySet()) {
			double entropia = calcularEntropia(entry.getValue());
			secuenciasConEntropia.put(entry.getKey(), entropia);
		}
		return secuenciasConEntropia;
	}
}