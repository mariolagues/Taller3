package Taller_3;

import java.io.*;
import java.util.*;

public class SistemaImpl implements Sistema {
    private static SistemaImpl instancia;
    
    private Usuario usuarioLogueado;
    private Usuario[] usuarios;
    private Proyecto[] proyectos;
    private Tarea[] tareas;
    private int cantidadUsuarios;
    private int cantidadProyectos;
    private int cantidadTareas;
    private StrategyPrioridad estrategia;
    
    private SistemaImpl() {
        this.usuarios = new Usuario[50];
        this.proyectos = new Proyecto[50];
        this.tareas = new Tarea[200];
        this.cantidadUsuarios = 0;
        this.cantidadProyectos = 0;
        this.cantidadTareas = 0;
    }
    
    public static SistemaImpl getInstancia() {
        if(instancia == null) {
            instancia = new SistemaImpl();
        }
        return instancia;
    }
    
    // ========== MÉTODOS PARA CARGA DESDE ARCHIVOS ==========
    
    /**
     * Agrega un usuario desde archivo (sin validaciones de ID)
     */
    public void agregarUsuarioDesdeArchivo(String nombre, String contraseña, String rol) {
        if (cantidadUsuarios < usuarios.length) {
            usuarios[cantidadUsuarios] = new Usuario(nombre, contraseña, rol);
            cantidadUsuarios++;
        }
    }
    
    /**
     * Agrega un proyecto desde archivo (con ID específico del archivo)
     */
    public void agregarProyectoDesdeArchivo(String id, String nombre, String responsable) {
        if (cantidadProyectos < proyectos.length) {
            proyectos[cantidadProyectos] = new Proyecto(id, nombre, responsable);
            cantidadProyectos++;
        }
    }
    
    /**
     * Agrega una tarea desde archivo (con ID específico del archivo)
     */
    public void agregarTareaDesdeArchivo(String proyectoId, String id, String tipo, String descripcion, 
                                       String estado, String responsable, String complejidad, String fecha) {
        if (cantidadTareas < tareas.length) {
            Tarea tarea = new Tarea(proyectoId, id, tipo, descripcion, estado, responsable, complejidad, fecha);
            tareas[cantidadTareas] = tarea;
            cantidadTareas++;
            
            // Asociar tarea al proyecto correspondiente
            Proyecto proyecto = buscarProyecto(proyectoId);
            if (proyecto != null) {
                proyecto.agregarTarea(tarea);
            }
        }
    }
    
    // ========== LOGIN ==========
    
    /**
     * Realiza el login de un usuario
     */
    public boolean login(String usuario, String contraseña) {
        for (int i = 0; i < cantidadUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u.getNombre().equals(usuario) && u.getContraseña().equals(contraseña)) {
                usuarioLogueado = u;
                System.out.println("Bienvenido " + usuario + " (" + u.getRol() + ")");
                return true;
            }
        }
        return false;
    }
    
    /**
     * Cierra la sesión del usuario actual
     */
    public void logout() {
        usuarioLogueado = null;
        System.out.println("Sesión cerrada");
    }
    
    // ========== GESTIÓN DE PROYECTOS ==========
    
    /**
     * Agrega un nuevo proyecto con ID automático
     */
    public void agregarProyecto(String nombre, String responsable) {
        if (cantidadProyectos >= proyectos.length) {
            System.out.println("No se pueden agregar más proyectos");
            return;
        }
        
        int numero = cantidadProyectos + 1;
        String nuevoId;
        
        if (numero < 10) {
            nuevoId = "PR00" + numero;
        } else if (numero < 100) {
            nuevoId = "PR0" + numero;
        } else {
            nuevoId = "PR" + numero;
        }
        
        Proyecto proyecto = new Proyecto(nuevoId, nombre, responsable);
        proyectos[cantidadProyectos] = proyecto;
        cantidadProyectos++;
        guardarProyectos();
        System.out.println("Proyecto '" + nombre + "' creado con ID: " + nuevoId);
    }
    
    /**
     * Elimina un proyecto y todas sus tareas
     */
    public void eliminarProyecto(String id) {
        int indiceProyecto = -1;
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].getId().equals(id)) {
                indiceProyecto = i;
                break;
            }
        }
        
        if (indiceProyecto == -1) {
            System.out.println("Proyecto no encontrado");
            return;
        }
        
        // Eliminar tareas del proyecto
        for (int i = 0; i < cantidadTareas; i++) {
            if (tareas[i].getProyecto().equals(id)) {
                for (int j = i; j < cantidadTareas - 1; j++) {
                    tareas[j] = tareas[j + 1];
                }
                cantidadTareas--;
                i--;
            }
        }
        
        // Eliminar proyecto
        for (int i = indiceProyecto; i < cantidadProyectos - 1; i++) {
            proyectos[i] = proyectos[i + 1];
        }
        proyectos[cantidadProyectos - 1] = null;
        cantidadProyectos--;
        
        guardarProyectos();
        guardarTareas();
        System.out.println("Proyecto " + id + " eliminado con todas sus tareas");
    }
    
    /**
     * Obtiene todos los proyectos
     */
    public Proyecto[] getProyectos() {
        Proyecto[] copia = new Proyecto[cantidadProyectos];
        for (int i = 0; i < cantidadProyectos; i++) {
            copia[i] = proyectos[i];
        }
        return copia;
    }
    
    /**
     * Busca un proyecto por ID
     */
    public Proyecto buscarProyecto(String id) {
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].getId().equals(id)) {
                return proyectos[i];
            }
        }
        return null;
    }
    
    // ========== GESTIÓN DE TAREAS ==========
    
    /**
     * Agrega una nueva tarea con ID automático
     */
    public void agregarTarea(String proyectoId, String tipo, String descripcion, 
                           String estado, String responsable, String complejidad, String fecha) {
        if (cantidadTareas >= tareas.length) {
            System.out.println("No se pueden agregar más tareas");
            return;
        }
        
        Proyecto proyecto = buscarProyecto(proyectoId);
        if (proyecto != null) {
            int numero = cantidadTareas + 1;
            String nuevoId;
            
            if (numero < 10) {
                nuevoId = "T00" + numero;
            } else if (numero < 100) {
                nuevoId = "T0" + numero;
            } else {
                nuevoId = "T" + numero;
            }
            
            Tarea tarea = new Tarea(proyectoId, nuevoId, tipo, descripcion, 
                                   estado, responsable, complejidad, fecha);
            tareas[cantidadTareas] = tarea;
            cantidadTareas++;
            proyecto.agregarTarea(tarea);
            guardarTareas();
            System.out.println("Tarea '" + descripcion + "' agregada con ID: " + nuevoId);
        } else {
            System.out.println("No se encontró el proyecto: " + proyectoId);
        }
    }
    
    /**
     * Elimina una tarea específica
     */
    public void eliminarTarea(String proyectoId, String tareaId) {
        int indiceTarea = -1;
        for (int i = 0; i < cantidadTareas; i++) {
            if (tareas[i].getId().equals(tareaId) && tareas[i].getProyecto().equals(proyectoId)) {
                indiceTarea = i;
                break;
            }
        }
        
        if (indiceTarea == -1) {
            System.out.println("Tarea no encontrada");
            return;
        }
        
        // Eliminar del array general
        for (int i = indiceTarea; i < cantidadTareas - 1; i++) {
            tareas[i] = tareas[i + 1];
        }
        tareas[cantidadTareas - 1] = null;
        cantidadTareas--;
        
        // Eliminar del proyecto
        Proyecto proyecto = buscarProyecto(proyectoId);
        if (proyecto != null) {
            proyecto.eliminarTarea(tareaId);
        }
        
        guardarTareas();
        System.out.println("Tarea " + tareaId + " eliminada del proyecto " + proyectoId);
    }
    
    /**
     * Actualiza el estado de una tarea
     */
    public void actualizarEstadoTarea(String proyectoId, String tareaId, String nuevoEstado) {
        for (int i = 0; i < cantidadTareas; i++) {
            if (tareas[i].getId().equals(tareaId) && tareas[i].getProyecto().equals(proyectoId)) {
                tareas[i].setEstado(nuevoEstado);
                guardarTareas();
                System.out.println("Tarea " + tareaId + " actualizada a: " + nuevoEstado);
                return;
            }
        }
        System.out.println("No se encontró la tarea " + tareaId + " en el proyecto " + proyectoId);
    }
    
    // ========== REPORTES ==========
    
    /**
     * Genera un reporte completo de proyectos y tareas
     */
    public void generarReporte() {
        try {
            PrintWriter writer = new PrintWriter("reporte.txt");
            writer.println("= REPORTE DE PROYECTOS =");
            writer.println("Generado el: " + new Date().toString());
            writer.println();
            
            for (int i = 0; i < cantidadProyectos; i++) {
                Proyecto proyecto = proyectos[i];
                writer.println("PROYECTO: " + proyecto.getNombre() + " (" + proyecto.getId() + ")");
                writer.println("Responsable: " + proyecto.getResponsable());
                writer.println("Tareas:");
                
                int tareasProyecto = 0;
                for (int j = 0; j < cantidadTareas; j++) {
                    if (tareas[j].getProyecto().equals(proyecto.getId())) {
                        tareasProyecto++;
                        writer.println("  - " + tareas[j].getId() + ": " + tareas[j].getDescripcion() + 
                                     " [" + tareas[j].getEstado() + "] - Fecha: " + tareas[j].getFecha());
                    }
                }
                
                if (tareasProyecto == 0) {
                    writer.println("  (No hay tareas en este proyecto)");
                }
                writer.println();
            }
            writer.close();
            System.out.println("Reporte generado en reporte.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Error al generar el reporte");
        }
    }
    
    /**
     * Obtiene las tareas asignadas a un usuario específico
     */
    public Tarea[] getTareasAsignadas(String usuario) {
        int count = 0;
        for (int i = 0; i < cantidadTareas; i++) {
            if (tareas[i].getResponsable().equals(usuario)) {
                count++;
            }
        }
        
        Tarea[] asignadas = new Tarea[count];
        int index = 0;
        for (int i = 0; i < cantidadTareas; i++) {
            if (tareas[i].getResponsable().equals(usuario)) {
                asignadas[index] = tareas[i];
                index++;
            }
        }
        return asignadas;
    }
    
    // ========== PATRONES DE DISEÑO ==========
    
    /**
     * Ordena las tareas según la estrategia seleccionada
     */
    public void ordenarTareas(String estrategiaTipo) {
        System.out.println("Aplicando estrategia de ordenamiento...");
        
        if (estrategiaTipo.equals("fecha")) {
            estrategia = new StrategyFecha();
            System.out.println("Ordenando por fecha de creación...");
            
        } else if (estrategiaTipo.equals("complejidad")) {
            estrategia = new StrategyComplejidad();
            System.out.println("Ordenando por nivel de complejidad...");
            
        } else if (estrategiaTipo.equals("tipo")) {
            estrategia = new StrategyTipo();
            System.out.println("Ordenando por tipo de tarea...");
            
        } else {
            System.out.println("Incorrecto, ingrese nuevamente");
            return;
        }
        
        estrategia.ordenarTareas(tareas, cantidadTareas);
        
        System.out.println("  Tareas ordenadas  ");
        for (int i = 0; i < cantidadTareas; i++) {
            Tarea tarea = tareas[i];
            System.out.println("• " + tarea.getTipo() + " " + tarea.getId() + 
                              " - " + tarea.getDescripcion() + 
                              " [" + tarea.getEstado() + "] - " + tarea.getFecha());
        }
    }
    
    /**
     * Aplica el patrón Visitor para analizar las tareas
     */
    public void aplicarVisitorTareas() {
        System.out.println("Analizando las tareas: ");
        
        VisitorTareas visitor = new VisitorTareas();
        int contador = 0;
        
        for (int i = 0; i < cantidadTareas; i++) {
            contador++;
            Tarea tarea = tareas[i];
            System.out.println(contador + ". ");
            
            if (tarea.getTipo().equals("Bug")) {
                visitor.visitarBug(tarea);
            } else if (tarea.getTipo().equals("Feature")) {
                visitor.visitarFeature(tarea);
            } else if (tarea.getTipo().equals("Documentacion")) {
                visitor.visitarDocumentacion(tarea);
            } else {
                System.out.println("Tipo de tarea desconocido: " + tarea.getTipo());
            }
            System.out.println();
        }
        
        System.out.println("Analisis completado para " + contador + " tareas");
    }
    
    /**
     * Obtiene el usuario actualmente logueado
     */
    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
    
    // ========== GUARDADO DE ARCHIVOS ==========
    
    /**
     * Guarda los proyectos en el archivo
     */
    private void guardarProyectos() {
        try {
            PrintWriter writer = new PrintWriter("proyectos.txt");
            for (int i = 0; i < cantidadProyectos; i++) {
                writer.println(proyectos[i].toString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error al guardar proyectos");
        }
    }
    
    /**
     * Guarda las tareas en el archivo
     */
    private void guardarTareas() {
        try {
            PrintWriter writer = new PrintWriter("tareas.txt");
            for (int i = 0; i < cantidadTareas; i++) {
                Tarea t = tareas[i];
                writer.println(t.getProyecto() + "|" + t.getId() + "|" + t.getTipo() + "|" + 
                              t.getDescripcion() + "|" + t.getEstado() + "|" + 
                              t.getResponsable() + "|" + t.getComplejidad() + "|" + t.getFecha());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error al guardar tareas");
        }
    }
}