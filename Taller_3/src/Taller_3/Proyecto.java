package Taller_3;

import java.util.ArrayList;
import java.util.List;

public class Proyecto {
	
    private String id;
    private String nombre;
    private String responsable;
    private List<Tarea> tareas;
    
    
    public Proyecto(String id, String nombre, String responsable) {
        this.id = id;
        this.nombre = nombre;
        this.responsable = responsable;
        this.tareas = new ArrayList<>();
    }
    
    
    public String getId() { 
    	return id; 
    }
    
    public String getNombre() { 
    	return nombre; 
    }
    
    public String getResponsable() { 
    	return responsable; 
    }
    
    public List<Tarea> getTareas() { 
    	return tareas; 
    }
    
    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }
    
    public void eliminarTarea(String tareaId) {
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId().equals(tareaId)) {
                tareas.remove(i);
                return;
            }
        }
    }
    
    public Tarea buscarTarea(String tareaId) {
        for (int i = 0; i < tareas.size(); i++) {
            Tarea t = tareas.get(i);
            if (t.getId().equals(tareaId)) {
                return t;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return id + "|" + nombre + "|" + responsable;
    }
}