package Taller_3;

public interface Sistema {
	
    // Para login
	
    boolean login(String usuario, String contraseña);
    void logout();
    
    // para proyectos
    
    void agregarProyecto(String nombre, String responsable);
    void eliminarProyecto(String id);
    Proyecto[] getProyectos();  
    Proyecto buscarProyecto(String id);
    
    // Tareas
    
    void agregarTarea(String proyectoId, String tipo, String descripcion, String estado, String responsable, String complejidad, String fecha);
    void eliminarTarea(String proyectoId, String tareaId);
    void actualizarEstadoTarea(String proyectoId, String tareaId, String nuevoEstado);
    
    // Reportes
    
    void generarReporte();
    Tarea[] getTareasAsignadas(String usuario); 
    
    // Patrones de diseño
    
    void ordenarTareas(String estrategia);
    void aplicarVisitorTareas();
    
    Usuario getUsuarioLogueado();


}
