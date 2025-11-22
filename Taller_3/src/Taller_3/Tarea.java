package Taller_3;

public class Tarea {
	
    private String proyecto;
    private String id;
    private String tipo;
    private String descripcion;
    private String estado;
    private String responsable;
    private String complejidad;
    private String fecha;
    
    public Tarea(String proyecto, String id, String tipo, String descripcion, String estado, String responsable, String complejidad, String fecha) {
    	
        this.proyecto = proyecto;
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.responsable = responsable;
        this.complejidad = complejidad;
        this.fecha = fecha;
    }
    
    public String getProyecto() { 
    	return proyecto; 
    }
    
    public String getId() { 
    	return id; 
    }
    
    public String getTipo() { 
    	return tipo; 
    }
    
    public String getDescripcion() { 
    	return descripcion; 
    }
    
    public String getEstado() { 
    	return estado; 
    }
    
    public String getResponsable() { 
    	return responsable; 
    }
    
    public String getComplejidad() { 
    	return complejidad; 
    }
    
    public String getFecha() { 
    	return fecha; 
    }
    
   
    public void setEstado(String estado) { 
    	this.estado = estado; 
    }
    
    public void setResponsable(String responsable) { 
    	this.responsable = responsable; 
    }
    
    @Override
    public String toString() {
        return proyecto + "|" + id + "|" + tipo + "|" + descripcion + "|" + 
               estado + "|" + responsable + "|" + complejidad + "|" + fecha;
    }
}
