package model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Archivo {

	private static final String DIRECTORY_PATH = "data";
	private static final String FILE_PATH = DIRECTORY_PATH + File.separator + "secuencias.txt";

	/**
	 * Creates the 'data' directory if it does not exist.
	 */
	private static void asegurarDirectorio() {
		File directorio = new File(DIRECTORY_PATH);
		if (!directorio.exists()) {
			directorio.mkdirs(); // Crea el directorio y cualquier directorio padre necesario
		}
	}

	/**
	 * Saves the HashMap to a .txt file.
	 *
	 * @param database The HashMap to be saved.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void guardarDatos(HashMap<Integer, String> database) throws IOException {
		asegurarDirectorio(); // Asegura que el directorio exista
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
			for (Map.Entry<Integer, String> entry : database.entrySet()) {
				writer.write(entry.getKey() + ":" + entry.getValue());
				writer.newLine();
			}
		}
	}

	/**
	 * Loads the HashMap from a .txt file.
	 *
	 * @return The HashMap loaded from the file.
	 * @throws IOException If an I/O error occurs.
	 */
	public static HashMap<Integer, String> cargarDatos() throws IOException {
		HashMap<Integer, String> database = new HashMap<>();
		File archivo = new File(FILE_PATH);
		if (!archivo.exists()) {
			System.out.println("El archivo de datos no existe. Se generarán nuevas secuencias.");
			return database;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(":");
				if (parts.length == 2) {
					int id = Integer.parseInt(parts[0]);
					String secuencia = parts[1];
					database.put(id, secuencia);
				}
			}
		}
		return database;
	}
}
