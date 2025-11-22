package Taller_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static Scanner sc;
    private static SistemaImpl sistema;
    
    public static void main(String[] args) throws FileNotFoundException {
        sc = new Scanner(System.in);
        
        // Crear sistema primero
        sistema = SistemaImpl.getInstancia();
        
        // Cargar datos desde archivos y pasarlos al sistema
        leerUsuarios();
        leerProyectos();
        leerTareas();
        
        // Sistema de login
        if (!login()) {
            System.out.println("Login fallido. Saliendo del sistema.");
            return;
        }
        
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1) Menú Administrador");
            System.out.println("2) Menú Usuario");
            System.out.println("3) Salir");
            System.out.print("Ingrese Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            
            switch(opcion) {
                case 1:
                    if (sistema.getUsuarioLogueado().getRol().equals("Administrador")) {
                        menuAdministrador();
                    } else {
                        System.out.println("No tiene permisos de administrador");
                    }
                    break;
                case 2:
                    menuUsuario();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Ingrese una opción correcta");
            }
        } while(opcion != 3);
    }
    
    /**
     * Maneja el proceso de login del usuario
     */
    private static boolean login() {
        System.out.println("=== LOGIN ===");
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = sc.nextLine();
        
        return sistema.login(usuario, contraseña);
    }
    
    /**
     * Menú para usuarios regulares
     */
    private static void menuUsuario() {
        int opcion;
        String usuarioActual = sistema.getUsuarioLogueado().getNombre();
        
        do {
            System.out.println("\n=== MENÚ USUARIO ===");
            System.out.println("1) Ver proyectos disponibles");
            System.out.println("2) Ver tareas asignadas");
            System.out.println("3) Actualizar estado de una tarea");
            System.out.println("4) Aplicar Visitor sobre tareas");
            System.out.println("5) Volver al menú principal");
            System.out.print("Ingrese opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
    
            switch(opcion) {
                case 1:
                    verProyectosDisponibles();
                    break;
                case 2:
                    verTareasAsignadas(usuarioActual);
                    break;
                case 3:
                    actualizarEstadoTarea();
                    break;
                case 4:
                    sistema.aplicarVisitorTareas();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while(opcion != 5);
    }
    
    /**
     * Menú para administradores
     */
    private static void menuAdministrador() {
        int opcion;
        
        do {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1) Ver lista completa de proyectos y tareas");
            System.out.println("2) Agregar o eliminar un proyecto");
            System.out.println("3) Agregar o eliminar una tarea en un proyecto");
            System.out.println("4) Asignar prioridades con Strategy");
            System.out.println("5) Generar reporte de proyectos");
            System.out.println("6) Volver al menú principal");
            System.out.print("Ingrese opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            
            switch(opcion) {
                case 1:
                    verProyectosYTareasCompleto();
                    break;
                case 2:
                    menuGestionProyectos();
                    break;
                case 3:
                    menuGestionTareas();
                    break;
                case 4:
                    menuStrategy();
                    break;
                case 5:
                    sistema.generarReporte();
                    break;
                case 6:
                    System.out.println("Volviendo al menú principal");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while(opcion != 6);
    }
    
    /**
     * Muestra los proyectos disponibles
     */
    private static void verProyectosDisponibles() {
        System.out.println("\n=== PROYECTOS DISPONIBLES ===");
        Proyecto[] proyectos = sistema.getProyectos();
        for (Proyecto p : proyectos) {
            System.out.println(p.getId() + " - " + p.getNombre() + 
                             " (Responsable: " + p.getResponsable() + ")");
        }
    }
    
    /**
     * Muestra las tareas asignadas a un usuario específico
     */
    private static void verTareasAsignadas(String usuario) {
        System.out.println("\n=== TAREAS ASIGNADAS A " + usuario + " ===");
        Tarea[] tareas = sistema.getTareasAsignadas(usuario);
        for (Tarea t : tareas) {
            System.out.println("Proyecto: " + t.getProyecto() + " - Tarea: " + t.getId() +
                             " - " + t.getDescripcion() + " [" + t.getEstado() + "]");
        }
    }
    
    /**
     * Permite actualizar el estado de una tarea
     */
    private static void actualizarEstadoTarea() {
        System.out.print("ID del proyecto: ");
        String proyectoId = sc.nextLine();
        System.out.print("ID de la tarea: ");
        String tareaId = sc.nextLine();
        System.out.print("Nuevo estado (Pendiente/En progreso/Completada): ");
        String nuevoEstado = sc.nextLine();
        
        sistema.actualizarEstadoTarea(proyectoId, tareaId, nuevoEstado);
        System.out.println("Estado actualizado");
    }
    
    /**
     * Muestra la lista completa de proyectos y sus tareas
     */
    private static void verProyectosYTareasCompleto() {
        System.out.println("\n=== LISTA COMPLETA DE PROYECTOS Y TAREAS ===");
        Proyecto[] proyectos = sistema.getProyectos();
        for (Proyecto p : proyectos) {
            System.out.println("\nPROYECTO: " + p.getNombre() + " (" + p.getId() + ")");
            System.out.println("Responsable: " + p.getResponsable());
            System.out.println("Tareas:");
            for (Tarea t : p.getTareas()) {
                System.out.println("  " + t.getId() + " - " + t.getTipo() + 
                                 ": " + t.getDescripcion() + " [" + t.getEstado() + "]");
            }
        }
    }
    
    /**
     * Menú para gestionar proyectos (agregar/eliminar)
     */
    private static void menuGestionProyectos() {
        System.out.println("\n1) Agregar proyecto");
        System.out.println("2) Eliminar proyecto");
        System.out.print("Seleccione: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        
        if (opcion == 1) {
            System.out.print("Nombre del proyecto: ");
            String nombre = sc.nextLine();
            System.out.print("Responsable: ");
            String responsable = sc.nextLine();
            sistema.agregarProyecto(nombre, responsable);
            System.out.println("Proyecto agregado");
        } else if (opcion == 2) {
            System.out.print("ID del proyecto a eliminar: ");
            String id = sc.nextLine();
            sistema.eliminarProyecto(id);
            System.out.println("Proyecto eliminado");
        }
    }
    
    /**
     * Menú para gestionar tareas (agregar/eliminar)
     */
    private static void menuGestionTareas() {
        System.out.println("\n1) Agregar tarea");
        System.out.println("2) Eliminar tarea");
        System.out.print("Seleccione: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        
        if (opcion == 1) {
            System.out.print("ID del proyecto: ");
            String proyectoId = sc.nextLine();
            System.out.print("Tipo (Bug/Feature/Documentacion): ");
            String tipo = sc.nextLine();
            System.out.print("Descripción: ");
            String descripcion = sc.nextLine();
            System.out.print("Estado inicial: ");
            String estado = sc.nextLine();
            System.out.print("Responsable: ");
            String responsable = sc.nextLine();
            System.out.print("Complejidad (Alta/Media/Baja): ");
            String complejidad = sc.nextLine();
            System.out.print("Fecha (YYYY-MM-DD): ");
            String fecha = sc.nextLine();
            
            sistema.agregarTarea(proyectoId, tipo, descripcion, estado, responsable, complejidad, fecha);
            System.out.println("Tarea agregada");
        } else if (opcion == 2) {
            System.out.print("ID del proyecto: ");
            String proyectoId = sc.nextLine();
            System.out.print("ID de la tarea: ");
            String tareaId = sc.nextLine();
            sistema.eliminarTarea(proyectoId, tareaId);
            System.out.println("Tarea eliminada");
        }
    }
    
    /**
     * Menú para seleccionar estrategia de ordenamiento
     */
    private static void menuStrategy() {
        System.out.println("\nSeleccione estrategia de ordenamiento:");
        System.out.println("1) Por fecha");
        System.out.println("2) Por complejidad");
        System.out.println("3) Por tipo");
        System.out.print("Seleccione: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        
        switch(opcion) {
            case 1:
                sistema.ordenarTareas("fecha");
                break;
            case 2:
                sistema.ordenarTareas("complejidad");
                break;
            case 3:
                sistema.ordenarTareas("tipo");
                break;
            default:
                System.out.println("Opción no válida");
        }
    }
    
    // ========== LECTURA DE ARCHIVOS ==========
    
    /**
     * Lee las tareas desde el archivo y las pasa al sistema
     */
    private static void leerTareas() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("tareas.txt"));
        while(fileScanner.hasNextLine()) {
            String linea = fileScanner.nextLine();
            String[] datos = linea.split("\\|"); 
            if (datos.length == 8) {
                String proyecto = datos[0];
                String id = datos[1];
                String tipo = datos[2];
                String descripcion = datos[3];
                String estado = datos[4];
                String responsable = datos[5];
                String complejidad = datos[6];
                String fecha = datos[7];
                
                // Pasar los datos al sistema para que cree la tarea
                sistema.agregarTareaDesdeArchivo(proyecto, id, tipo, descripcion, estado, responsable, complejidad, fecha);
            }
        }
        fileScanner.close();
    }
    
    /**
     * Lee los proyectos desde el archivo y los pasa al sistema
     */
    private static void leerProyectos() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("proyectos.txt"));
        while(fileScanner.hasNextLine()) {
            String linea = fileScanner.nextLine();
            String[] datos = linea.split("\\|"); 
            if (datos.length == 3) {
                String id = datos[0];
                String nombre = datos[1];
                String responsable = datos[2];
                
                // Pasar los datos al sistema para que cree el proyecto
                sistema.agregarProyectoDesdeArchivo(id, nombre, responsable);
            }
        }
        fileScanner.close();
    }
    
    /**
     * Lee los usuarios desde el archivo y los pasa al sistema
     */
    private static void leerUsuarios() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("usuarios.txt"));
        while(fileScanner.hasNextLine()) {
            String linea = fileScanner.nextLine();
            String[] datos = linea.split("\\|"); 
            if (datos.length == 3) {
                String nombre = datos[0];
                String contraseña = datos[1];
                String rol = datos[2];
                
                // Pasar los datos al sistema para que cree el usuario
                sistema.agregarUsuarioDesdeArchivo(nombre, contraseña, rol);
            }
        }
        fileScanner.close();
    }
}