package principal;

import com.google.gson.Gson;
import modelo.Exchange;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultarExchange {

    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/53623b676e5aa9831469aa93/latest/";

    // Método de la API y consulta
    public Exchange consultarTasaCambio(String moneda) {
        URI direccion = URI.create(BASE_URL + moneda);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), Exchange.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("No se pudo obtener la tasa de cambio para " + moneda);
        }
    }
}
