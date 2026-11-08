import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GestorArchivos {
    private static final String BINARIO = "recetas.dat";
    private static final String XML = "recetas.xml";
    private static final String JSON = "configuracion.json";

    public static boolean existeFicheroBinario() {
        File fichero = new File(BINARIO);
        return fichero.exists();
    }

    public static List<Recetas> leerBinario() {
        List<Recetas> lista = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BINARIO))) {
            lista = (List<Recetas>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el archivo binario.");
        }
        return lista;
    }

    public static void escribirBinario(List<Recetas> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BINARIO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo binario.");
        }
    }

    public static boolean exportarXML(List<Recetas> lista) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();

            Element root = documento.createElement("recetas");
            documento.appendChild(root);

            for (Recetas r : lista) {
                Element receta = documento.createElement("receta");
                receta.setAttribute("id", String.valueOf(r.getId()));

                Element nombre = documento.createElement("nombre");
                nombre.setTextContent(r.getNombre());
                receta.appendChild(nombre);

                Element categoria = documento.createElement("categoria");
                categoria.setTextContent(r.getCategoria());
                receta.appendChild(categoria);

                Element tiempo = documento.createElement("tiempoPreparacion");
                tiempo.setTextContent(String.valueOf(r.getTiempoPreparacion()));
                receta.appendChild(tiempo);

                Element dificultad = documento.createElement("dificultad");
                dificultad.setTextContent(r.getDificultad());
                receta.appendChild(dificultad);

                root.appendChild(receta);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File(XML));
            transformer.transform(source, result);

            return true;
        } catch (Exception e) {
            System.out.println("Error al generar el documento XML.");
            return false;
        }
    }

    public static void guardarConfigJSON(Configuracion config) {
        String contenido = "{\n" +
                "  \"ultimaOpcionMenu\": " + config.getUltimaOpcionMenu() + ",\n" +
                "  \"totalRecetasAlmacenadas\": " + config.getTotalRecetasAlmacenadas() + "\n" +
                "}";

        try (PrintWriter pw = new PrintWriter(new FileWriter(JSON))) {
            pw.write(contenido);
        } catch (IOException e) {
            System.out.println("Error al guardar la configuracion JSON.");
        }
    }

    public static Configuracion cargarConfigJSON() {
        File f = new File(JSON);
        if (!f.exists()) {
            return new Configuracion();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            int opcion = 0;
            int total = 0;

            while ((linea = br.readLine()) != null) {
                if (linea.contains("ultimaOpcionMenu")) {
                    String[] partes = linea.split(":");
                    opcion = Integer.parseInt(partes[1].replace(",", "").trim());
                } else if (linea.contains("totalRecetasAlmacenadas")) {
                    String[] partes = linea.split(":");
                    total = Integer.parseInt(partes[1].trim());
                }
            }
            return new Configuracion(opcion, total);
        } catch (Exception e) {
            System.out.println("Error al cargar la configuracion JSON.");
            return new Configuracion();
        }
    }
}