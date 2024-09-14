package controller;

import model.GeneticSequenceManager;
import view.VistaConsola;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Controller {

	private GeneticSequenceManager model;
	private VistaConsola view;
	private Scanner scanner;

	/**
	 * Initializes the Controller with the GeneticSequenceManager model,
	 * VistaConsola view, and a Scanner for user input.
	 */
	public Controller() {
		model = new GeneticSequenceManager();
		view = new VistaConsola();
		scanner = new Scanner(System.in);
	}

	/**
	 * Starts the application and repeatedly displays the main menu.
	 */
	public void ejecutar() {
		while (true) {
			mostrarMenuPrincipal();
		}
	}

	/**
	 * Displays the main menu and processes user input.
	 */
	private void mostrarMenuPrincipal() {
		System.out.println("\n--- Menú Principal ---");
		System.out.println("1. Usar base de datos actual");
		System.out.println("2. Generar nuevas secuencias");
		System.out.println("3. Salir");
		System.out.print("Seleccione una opción: ");

		int opcion = scanner.nextInt();
		scanner.nextLine(); // Consumir el salto de línea

		switch (opcion) {
		case 1:
			model.cargarDesdeArchivo();
			System.out.println("Base de datos cargada desde el archivo.");
			mostrarSubmenuBaseDatos();
			break;

		case 2:
			generarNuevasSecuencias();
			break;

		case 3:
			System.out.println("Saliendo del programa...");
			System.exit(0);

		default:
			view.mostrarInformacion("Opción inválida. Por favor, seleccione una opción válida.");
			break;
		}
	}

	/**
	 * Displays the database menu and processes user input.
	 */
	private void mostrarSubmenuBaseDatos() {
		while (true) {
			System.out.println("\n--- Menú de Base de Datos ---");
			System.out.println("1. Obtener e imprimir secuencias");
			System.out.println("2. Filtrar por entropía");
			System.out.println("3. Buscar motivo");
			System.out.println("4. Volver al menú principal");
			System.out.print("Seleccione una opción: ");

			int opcion = scanner.nextInt();
			scanner.nextLine(); // Consumir el salto de línea

			switch (opcion) {
			case 1:
				Map<Integer, Double> entropiasDesdeArchivo = model.getSecuenciasConEntropia();
				view.mostrarSecuenciasConEntropia(model.getDatabase(), entropiasDesdeArchivo);
				break;

			case 2:
				filtrarPorEntropia();
				break;

			case 3:
				buscarMotivo();
				break;

			case 4:
				return;

			default:
				view.mostrarInformacion("Opción inválida. Por favor, seleccione una opción válida.");
				break;
			}
		}
	}

	/**
	 * Prompts the user to input parameters and generates new sequences.
	 */
	private void generarNuevasSecuencias() {
		int n, m;

		while (true) {
			try {
				n = view.leerDatoEntero("Ingrese el número de secuencias (1000 <= n <= 2000000): ");
				model.validarNumeroDeSecuencias(n);
				break;
			} catch (IllegalArgumentException e) {
				view.mostrarInformacion(e.getMessage());
			}
		}

		while (true) {
			try {
				m = view.leerDatoEntero("Ingrese el tamaño de las secuencias (5 <= m <= 100): ");
				model.validarTamañoSecuencias(m);
				break;
			} catch (IllegalArgumentException e) {
				view.mostrarInformacion(e.getMessage());
			}
		}

		model.generarSecuencias(n, m);
		model.guardarEnArchivo();
		Map<Integer, Double> entropias = model.getSecuenciasConEntropia();
		view.mostrarSecuenciasConEntropia(model.getDatabase(), entropias);
	}

	/**
	 * Prompts the user to input an entropy threshold and filters sequences based on
	 * it.
	 */
	private void filtrarPorEntropia() {
		double umbralEntropia;
		while (true) {
			try {
				System.out.print("Ingrese el umbral de entropía para filtrar (mínimo: 0.0, máximo: 1.9): ");
				umbralEntropia = scanner.nextDouble();
				if (umbralEntropia >= 0.0 && umbralEntropia <= 1.9) {
					break;
				} else {
					view.mostrarInformacion("El valor del umbral de entropía debe estar entre 0.0 y 1.9.");
				}
			} catch (InputMismatchException e) {
				// Mensaje de error cuando la entrada es incorrecta (letras o formato inválido)
				System.out.println("Entrada no válida, decimales se leen con coma ',' no con punto '.'.");
				scanner.nextLine(); // Limpiar el buffer de entrada para que no cause un bucle infinito
			}
		}

		model.filtrarPorEntropia(umbralEntropia);
		model.guardarEnArchivo();
		view.mostrarInformacion("Secuencias filtradas por entropía:");
		Map<Integer, Double> entropiasFiltradas = model.getSecuenciasConEntropia();
		view.mostrarSecuenciasConEntropia(model.getDatabase(), entropiasFiltradas);
	}

	/**
	 * Prompts the user to input the motif size and searches for the most frequent
	 * motif.
	 */
	private void buscarMotivo() {
		int s;
		while (true) {
			try {
				s = view.leerDatoEntero("Ingrese el tamaño del motivo a buscar (4 <= s <= 10): ");
				model.validarTamañoMotivo(s);
				break;
			} catch (IllegalArgumentException e) {
				view.mostrarInformacion(e.getMessage());
			}
		}
		String motivoFrecuente = model.detectarMotivo(s);
		view.mostrarInformacion("El motivo más frecuente de tamaño " + s + " es: " + motivoFrecuente);
	}
}
