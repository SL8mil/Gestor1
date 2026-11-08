import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorRecetas gestor = new GestorRecetas();
        Configuracion config = GestorArchivos.cargarConfigJSON();

        System.out.println("=== GESTOR DE RECETAS ===");
        if (config.getUltimaOpcionMenu() != 0) {
            System.out.println("Última opción usada previamente: " + config.getUltimaOpcionMenu());
            System.out.println("Recetas guardadas previamente: " + config.getTotalRecetasAlmacenadas());
        }

        int opc = 0;

        while (opc != 6) {
            System.out.println("\n1. Ver recetas");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Cambiar tiempo de preparación");
            System.out.println("4. Añadir receta");
            System.out.println("5. Exportar a XML");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            try {
                opc = Integer.parseInt(sc.nextLine());

                switch (opc) {
                    case 1:
                        gestor.listarRecetas();
                        break;
                    case 2:
                        System.out.print("Introduce el ID: ");
                        int idBuscado = Integer.parseInt(sc.nextLine());
                        Recetas encontrada = gestor.buscarPorId(idBuscado);
                        if (encontrada != null) {
                            System.out.println("Resultado: " + encontrada);
                        } else {
                            System.out.println("No se ha encontrado ninguna receta con ese ID.");
                        }
                        break;
                    case 3:
                        System.out.print("ID de la receta a editar: ");
                        int idEdit = Integer.parseInt(sc.nextLine());
                        System.out.print("Nuevo tiempo en minutos: ");
                        int tNuevo = Integer.parseInt(sc.nextLine());
                        if (gestor.cambiarTiempo(idEdit, tNuevo)) {
                            System.out.println("Tiempo actualizado correctamente.");
                        } else {
                            System.out.println("No existe esa receta.");
                        }
                        break;
                    case 4:
                        System.out.print("ID: ");
                        int idNew = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre: ");
                        String nom = sc.nextLine();
                        System.out.print("Categoría: ");
                        String cat = sc.nextLine();
                        System.out.print("Tiempo: ");
                        int t = Integer.parseInt(sc.nextLine());
                        System.out.print("Dificultad: ");
                        String dif = sc.nextLine();

                        Recetas rec = new Recetas(idNew, nom, cat, t, dif);
                        if (gestor.insertarReceta(rec)) {
                            System.out.println("Receta añadida correctamente.");
                        } else {
                            System.out.println("Ese ID ya existe.");
                        }
                        break;
                    case 5:
                        if (gestor.exportarAXML()) {
                            System.out.println("¡El documento 'recetas.xml' ha sido creado correctamente en el disco!");
                        }
                        break;
                    case 6:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

                if (opc >= 1 && opc <= 6) {
                    config.setUltimaOpcionMenu(opc);
                    config.setTotalRecetasAlmacenadas(gestor.getCantidadRecetas());
                    GestorArchivos.guardarConfigJSON(config);
                }

            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un número entero.");
            }
        }

        sc.close();
    }
}