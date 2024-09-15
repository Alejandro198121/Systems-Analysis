package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VistaConsola {

	private Scanner scanner;

	public VistaConsola() {
		scanner = new Scanner(System.in);
	}

	public void limpiarBuffer() {
		scanner.nextLine();
	}

	public void mostrarInformacion(String mensaje) {
		System.out.println(mensaje);
	}

	public double leerDatoDouble(String mensaje) {
		System.out.print(mensaje);
		return scanner.nextDouble();
	}

	public int leerDatoEntero(String mensaje) {
		System.out.print(mensaje);
		return scanner.nextInt();
	}

	public void mostrarSecuenciasConEntropia(HashMap<Integer, String> secuencias, Map<Integer, Double> entropias) {
		for (Integer id : secuencias.keySet()) {
			String secuencia = secuencias.get(id);
			double entropia = entropias.get(id);
			System.out.println("ID: " + id + " - Secuencia: " + secuencia + " - Entropía: " + entropia);
		}
	}

	public String leerDatoString(String mensaje) {
		System.out.print(mensaje);
		return scanner.nextLine();
	}

}
