package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GeneticSequenceManager {
	private HashMap<Integer, String> database;
	private Random random;

	public GeneticSequenceManager() {
		database = new HashMap<>();
		random = new Random();
	}

	public int obtenerLongitudMaximaSecuencia() {
		return database.values().stream().mapToInt(String::length).max().orElse(0);
	}

	public Map<String, Integer> obtenerTodosLosMotifs(int s) {
		HashMap<String, Integer> conteoMotifs = new HashMap<>();
		for (String secuencia : database.values()) {
			if (secuencia.length() < s) {
				continue;
			}
			for (int i = 0; i <= secuencia.length() - s; i++) {
				String subsecuencia = secuencia.substring(i, i + s);
				conteoMotifs.put(subsecuencia, conteoMotifs.getOrDefault(subsecuencia, 0) + 1);
			}
		}
		return conteoMotifs;
	}

	private double[] generarProbabilidadesAleatorias() {
		double pA = random.nextDouble();
		double pC = random.nextDouble();
		double pG = random.nextDouble();
		double pT = random.nextDouble();

		double suma = pA + pC + pG + pT;
		return new double[] { pA / suma, pC / suma, pG / suma, pT / suma };
	}

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

	public int validarNumeroDeSecuencias(int n) {
		if (n < 1000 || n > 2000000) {
			throw new IllegalArgumentException("El número de secuencias debe estar entre 1000 y 2000000.");
		}
		return n;
	}

	public int validarTamañoSecuencias(int m) {
		if (m < 5 || m > 100) {
			throw new IllegalArgumentException("El tamaño de las secuencias debe estar entre 5 y 100.");
		}
		return m;
	}

	public int validarTamañoMotif(int s) {
		if (s < 4 || s > 10) {
			throw new IllegalArgumentException("El tamaño del motif debe estar entre 4 y 10.");
		}
		return s;
	}

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

	public void filtrarPorEntropia(double umbral) {
		database.entrySet().removeIf(entry -> calcularEntropia(entry.getValue()) < umbral);
	}

	public Map<String, Integer> detectarMotif(int s) {
		return obtenerTodosLosMotifs(s);
	}

	public HashMap<Integer, String> getDatabase() {
		return database;
	}

	public void cargarDesdeArchivo() {
		try {
			database = Archivo.cargarDatos();
		} catch (IOException e) {
			System.out.println("Error al cargar los datos desde el archivo: " + e.getMessage());
		}
	}

	public void guardarEnArchivo() {
		try {
			Archivo.guardarDatos(database);
		} catch (IOException e) {
			System.out.println("Error al guardar los datos en el archivo: " + e.getMessage());
		}
	}

	public Map<Integer, Double> getSecuenciasConEntropia() {
		Map<Integer, Double> secuenciasConEntropia = new HashMap<>();
		for (Map.Entry<Integer, String> entry : database.entrySet()) {
			double entropia = calcularEntropia(entry.getValue());
			secuenciasConEntropia.put(entry.getKey(), entropia);
		}
		return secuenciasConEntropia;
	}
}