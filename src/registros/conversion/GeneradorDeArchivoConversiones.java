package registros.conversion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeneradorDeArchivoConversiones {
    private static final String FILE_NAME = "conversiones.json";
    private List<ConversionRegistro> conversiones;

    public GeneradorDeArchivoConversiones() {
        this.conversiones = new ArrayList<>();
        // Cargar las conversiones existentes si el archivo ya existe
        cargarConversionesExistentes();
    }

    // Método para agregar una conversión a la lista
    public void registrarConversion(ConversionRegistro conversion) {
        conversiones.add(conversion);
    }

    // Método para cargar las conversiones existentes desde el archivo JSON
    private void cargarConversionesExistentes() {
        Gson gson = new Gson();
        try (FileReader lector = new FileReader(FILE_NAME)) {
            // Leer el archivo JSON existente y convertirlo en una lista de Registros.Conversion.ConversionRegistro
            Type conversionListType = new TypeToken<ArrayList<ConversionRegistro>>() {}.getType();
            List<ConversionRegistro> conversionesPrevias = gson.fromJson(lector, conversionListType);
            if (conversionesPrevias != null) {
                conversiones.addAll(conversionesPrevias);  // Agregar las conversiones previas a la lista actual
            }
        } catch (IOException e) {
            System.out.println("No se encontraron conversiones anteriores. Se creará un nuevo archivo.");
        }
    }

    // Método para guardar todas las conversiones (anteriores y nuevas) en el archivo JSON
    public void guardarConversiones() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter escritor = new FileWriter(FILE_NAME, false)) {  // Sobrescribir el archivo con la lista completa
            escritor.write(gson.toJson(conversiones));  // Escribir la lista completa de conversiones
        }
    }
}
