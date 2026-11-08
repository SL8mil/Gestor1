import java.io.Serializable;

public class Recetas implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String categoria;
    private int tiempoPreparacion;
    private String dificultad;

    public Recetas(int id, String nombre, String categoria, int tiempoPreparacion, String dificultad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tiempoPreparacion = tiempoPreparacion;
        this.dificultad = dificultad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Receta: " + nombre + " (" + categoria + ") - Tiempo: " + tiempoPreparacion + " min - Dificultad: " + dificultad;
    }
}