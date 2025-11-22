package Taller_3;

public class Usuario {
    private String nombre;
    private String contraseña;
    private String rol;
    
    public Usuario(String nombre, String contraseña, String rol) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    
   
    public String getNombre() { 
    	return nombre; 
    }
    public String getContraseña() { 
    	return contraseña; 
    }
    public String getRol() { 
    	return rol; 
    }
    
    @Override
    public String toString() {
        return nombre + "|" + contraseña + "|" + rol;
    }
}