package agenda;

import java.io.Serializable;

public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
    private String telefono;

    public Persona(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }
    
    
    public String getNombre() {
        return nombre+"\n";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono+"\n";
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    

}
