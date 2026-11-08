public class Configuracion {
    private int ultimaOpcionMenu;
    private int totalRecetasAlmacenadas;

    public Configuracion() {
        this.ultimaOpcionMenu = 0;
        this.totalRecetasAlmacenadas = 0;
    }

    public Configuracion(int ultimaOpcionMenu, int totalRecetasAlmacenadas) {
        this.ultimaOpcionMenu = ultimaOpcionMenu;
        this.totalRecetasAlmacenadas = totalRecetasAlmacenadas;
    }

    public int getUltimaOpcionMenu() {
        return ultimaOpcionMenu;
    }

    public void setUltimaOpcionMenu(int ultimaOpcionMenu) {
        this.ultimaOpcionMenu = ultimaOpcionMenu;
    }

    public int getTotalRecetasAlmacenadas() {
        return totalRecetasAlmacenadas;
    }

    public void setTotalRecetasAlmacenadas(int totalRecetasAlmacenadas) {
        this.totalRecetasAlmacenadas = totalRecetasAlmacenadas;
    }
}