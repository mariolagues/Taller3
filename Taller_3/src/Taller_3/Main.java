package Taller_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	private static Scanner sc;
	public static void main(String[] args) throws FileNotFoundException {
		leerUsuarios();
		leerProyectos();
		leerTareas();
		
		sc = new Scanner(System.in); 
		int opcion;
		
		do {
			System.out.println("Menú Principal");
			System.out.println("1) Menú administrador");
			System.out.println("2) Menú Usuario");
			System.out.print("Ingrese Opcion");
			opcion = sc.nextInt();
			sc.nextLine();
			
			switch(opcion) {
			case 1:
				menuAdministrador();
				break;
			case 2:
				menuUsuario();
				
			default:
				System.out.println("Ingrese una opcion correcta");
			}
			
		}while(opcion != 3);
		
	}
	private static void menuUsuario() {
		sc = new Scanner(System.in);
		int opcion;
		
		do {
			System.out.println("Menú Usuario");
			System.out.println("1) Ver proyectos disponibles");
			System.out.println("2) Ver tareas asignadas");
			System.out.println("3) Actualizar estado de una tarea");
			System.out.println("4) Aplicar Visitor sobre tareas");
			System.out.println("6) Volver al menú principal");
			System.out.print("Ingrese opcion: ");
			opcion = sc.nextInt();
			sc.nextLine();
	
			switch(opcion) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				System.out.println("Volvieldo al menú principal");
			
			}
			
		}while(opcion != 5);
		
	}
	private static void menuAdministrador() {
		sc = new Scanner(System.in);
		int opcion;
		
		do {
			System.out.println("Menú administador");
			System.out.println("1) Ver listas completa de proyectos y tareas");
			System.out.println("2) Agregar o eliminar un proyecto");
			System.out.println("3) Agregar o eliminar una tarea en un proyecto");
			System.out.println("4) Asignar prioridades con Strategy");
			System.out.println("5) Generar reporte de proyectos");
			System.out.println("6) Volver al menú principal");
			System.out.print("Ingrese opcion: ");
			opcion = sc.nextInt();
			sc.nextLine();
			
			switch(opcion) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				System.out.println("Volvieldo al menú principal");
			
			}
			
		}while(opcion != 5);
		
		
	}
		
	private static void leerTareas() throws FileNotFoundException {
		sc = new Scanner(new File("tareas.txt"));
		while(sc.hasNextLine()) {
			String datos[] = sc.nextLine().split(";");
			String proyecto = datos[0];
			String id = datos[1];
			String tipo = datos[2];
			String descripcion = datos[3];
			String estado = datos[4];
			String responsable = datos[5];
			String complejidad = datos[6];
			String fecha = datos[7];
			
			
		}
		
	}
	private static void leerProyectos() throws FileNotFoundException {
		sc = new Scanner(new File("proyectos.txt"));
		while(sc.hasNextLine()) {
			String datos[] = sc.nextLine().split(";");
			String id = datos[0];
			String nombre = datos[1];
			String responsable = datos[2];
			
		}
		
	}
	private static void leerUsuarios() throws FileNotFoundException {
		sc = new Scanner(new File("usuarios.txt"));
		while(sc.hasNextLine()) {
			String datos[] = sc.nextLine().split(";");
			String nombre = datos[0];
			String contraseña = datos[1];
			String rol = datos[2];
			
		}
		
	}

}

