package controller;

import model.GeneticSequenceManager;
import view.VistaConsola;

import java.util.InputMismatchException;
import java.util.Map;

public class Controller {

	private GeneticSequenceManager manager;
	private VistaConsola view;

	public Controller() {
		manager = new GeneticSequenceManager();
		view = new VistaConsola();
	}

	public void ejecutar() {
		while (true) {
			mostrarMenuPrincipal();
		}
	}

	private void mostrarMenuPrincipal() {
		System.out.println("\n--- Menú Principal ---");
		System.out.println("1. Usar base de datos actual");
		System.out.println("2. Generar nuevas secuencias");
		System.out.println("3. Salir");

		int opcion = view.leerDatoEntero("Seleccione una opción: ");

		switch (opcion) {
		case 1:
			manager.cargarDesdeArchivo();
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

	private void mostrarSubmenuBaseDatos() {
		while (true) {
			System.out.println("\n--- Menú de Base de Datos ---");
			System.out.println("1. Obtener e imprimir secuencias");
			System.out.println("2. Filtrar por entropía");
			System.out.println("3. Buscar motif");
			System.out.println("4. Volver al menú principal");

			int opcion = view.leerDatoEntero("Seleccione una opción: ");

			switch (opcion) {
			case 1:
				Map<Integer, Double> entropiasDesdeArchivo = manager.getSecuenciasConEntropia();
				view.mostrarSecuenciasConEntropia(manager.getDatabase(), entropiasDesdeArchivo);
				break;

			case 2:
				filtrarPorEntropia();
				break;

			case 3:
				buscarMotif();
				break;

			case 4:
				return;

			default:
				view.mostrarInformacion("Opción inválida. Por favor, seleccione una opción válida.");
				break;
			}
		}
	}

	private void generarNuevasSecuencias() {
		int n, m;

		while (true) {
			try {
				n = view.leerDatoEntero("Ingrese el número de secuencias (1000 <= n <= 2000000): ");
				manager.validarNumeroDeSecuencias(n);
				break;
			} catch (IllegalArgumentException e) {
				view.mostrarInformacion(e.getMessage());
			}
		}

		while (true) {
			try {
				m = view.leerDatoEntero("Ingrese el tamaño de las secuencias (5 <= m <= 100): ");
				manager.validarTamañoSecuencias(m);
				break;
			} catch (IllegalArgumentException e) {
				view.mostrarInformacion(e.getMessage());
			}
		}

		manager.generarSecuencias(n, m);
		manager.guardarEnArchivo();
		Map<Integer, Double> entropias = manager.getSecuenciasConEntropia();
		view.mostrarSecuenciasConEntropia(manager.getDatabase(), entropias);
	}

	private void filtrarPorEntropia() {
		double umbralEntropia;
		while (true) {
			try {
				umbralEntropia = view
						.leerDatoDouble("Ingrese el umbral de entropía para filtrar (mínimo: 0,0 máximo: 1,9): ");
				if (umbralEntropia >= 0.0 && umbralEntropia <= 1.9) {
					break;
				} else {
					view.mostrarInformacion("El valor del umbral de entropía debe estar entre 0,0 y 1,9.");
				}
			} catch (InputMismatchException e) {

				System.out.println("Entrada no válida, decimales se leen con coma ',' no con punto '.'.");
				view.limpiarBuffer();
			}
		}

		manager.filtrarPorEntropia(umbralEntropia);
		manager.guardarEnArchivo();
		view.mostrarInformacion("Secuencias filtradas por entropía:");
		Map<Integer, Double> entropiasFiltradas = manager.getSecuenciasConEntropia();
		view.mostrarSecuenciasConEntropia(manager.getDatabase(), entropiasFiltradas);
	}

	private void buscarMotif() {
		int s;
		while (true) {
			try {
				s = view.leerDatoEntero("Ingrese el tamaño del motif a buscar (4 <= s <= 10): ");
				manager.validarTamañoMotif(s);

				// Verificar si el tamaño del motif es mayor que el tamaño máximo de las cadenas
				int longitudMaxima = manager.obtenerLongitudMaximaSecuencia();
				if (s > longitudMaxima) {
					view.mostrarInformacion(
							"El tamaño del motif es mayor que la longitud de las secuencias en la base de datos (longitud máxima: "
									+ longitudMaxima + ").");
					return;
				}

				// Obtener todos los motifs de tamaño s y sus repeticiones
				Map<String, Integer> todosLosMotifs = manager.detectarMotif(s);
				if (todosLosMotifs.isEmpty()) {
					view.mostrarInformacion("No se encontraron motifs de tamaño " + s + ".");
					return;
				}

				// Mostrar la lista de todos los motifs y sus repeticiones
				view.mostrarInformacion("Lista de todos los motifs de tamaño " + s + " y sus repeticiones:");
				for (Map.Entry<String, Integer> entry : todosLosMotifs.entrySet()) {
					view.mostrarInformacion("Motif: " + entry.getKey() + " - Repeticiones: " + entry.getValue());
				}

				// Encontrar el motif más frecuente
				String motifFrecuente = null;
				int maxOcurrencias = 0;
				for (Map.Entry<String, Integer> entry : todosLosMotifs.entrySet()) {
					if (entry.getValue() > maxOcurrencias) {
						maxOcurrencias = entry.getValue();
						motifFrecuente = entry.getKey();
					}
				}

				// Mostrar el motif más frecuente
				view.mostrarInformacion("El motif más frecuente de tamaño " + s + " es: " + motifFrecuente
						+ " y se repite " + maxOcurrencias + " veces.");

				return;
			} catch (IllegalArgumentException e) {
				view.mostrarInformacion(e.getMessage());
			}
		}
	}

}
