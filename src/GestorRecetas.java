import java.util.ArrayList;
import java.util.List;

public class GestorRecetas {
    private List<Recetas> catalogo;

    public GestorRecetas() {
        catalogo = new ArrayList<>();
        comprobarYcargar();
    }

    private void comprobarYcargar() {
        if (!GestorArchivos.existeFicheroBinario()) {
            System.out.println("El archivo 'recetas.dat' no existe. Creando catálogo inicial...");
            crearCatalogoInicial();
        } else {
            catalogo = GestorArchivos.leerBinario();
        }
    }

    public void crearCatalogoInicial() {
        catalogo = new ArrayList<>();
        catalogo.add(new Recetas(1, "Tortilla de patata", "Tradicional", 30, "Fácil"));
        catalogo.add(new Recetas(2, "Paella valenciana", "Arroces", 60, "Media"));
        catalogo.add(new Recetas(3, "Gazpacho andaluz", "Sopas", 15, "Fácil"));
        catalogo.add(new Recetas(4, "Tiramisú", "Postres", 45, "Media"));

        GestorArchivos.escribirBinario(catalogo);
    }

    public void listarRecetas() {
        if (catalogo.isEmpty()) {
            System.out.println("No hay recetas en el catálogo.");
        } else {
            for (Recetas r : catalogo) {
                System.out.println(r);
            }
        }
    }

    public Recetas buscarPorId(int id) {
        for (Recetas r : catalogo) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public boolean cambiarTiempo(int id, int nuevoTiempo) {
        Recetas r = buscarPorId(id);
        if (r != null) {
            r.setTiempoPreparacion(nuevoTiempo);
            GestorArchivos.escribirBinario(catalogo);
            return true;
        }
        return false;
    }

    public boolean insertarReceta(Recetas nueva) {
        if (buscarPorId(nueva.getId()) != null) {
            return false;
        }
        catalogo.add(nueva);
        GestorArchivos.escribirBinario(catalogo);
        return true;
    }

    public boolean exportarAXML() {
        return GestorArchivos.exportarXML(catalogo);
    }

    public int getCantidadRecetas() {
        return catalogo.size();
    }
}