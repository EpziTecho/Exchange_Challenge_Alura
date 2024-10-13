package registros.errores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeneradorDeArchivoErrores {
    private static final String FILE_NAME = "errores.json";
    private List<ErrorRegistro> errores;

    public GeneradorDeArchivoErrores() {
        this.errores = new ArrayList<>();
        // Cargar los errores existentes si el archivo ya existe
        cargarErroresExistentes();
    }
    // Método para agregar un error a la lista
    public void registrarError(ErrorRegistro error) {
        errores.add(error);
    }
    // Método para cargar los errores existentes desde el archivo JSON
    private void cargarErroresExistentes() {
        Gson gson = new Gson();
        try (FileReader lector = new FileReader(FILE_NAME)) {
            // Leer el archivo y convertirlo en una lista de Registros.Errores.ErrorRegistro
            Type errorListType = new TypeToken<ArrayList<ErrorRegistro>>() {}.getType();
            List<ErrorRegistro> erroresPrevios = gson.fromJson(lector, errorListType);
            if (erroresPrevios != null) {
                errores.addAll(erroresPrevios);  // Agregar los errores previos a la lista actual
            }
        } catch (IOException e) {
              System.out.println("No se encontraron errores anteriores. Se creará un nuevo archivo.");
        }
    }

    // Método para guardar todos los errores (anteriores y nuevos) en el archivo JSON
    public void guardarErrores() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter escritor = new FileWriter(FILE_NAME, false)) {  // Sobrescribir el archivo con la lista completa
            escritor.write(gson.toJson(errores));  // Escribir la lista completa de errores
        }
    }
}
