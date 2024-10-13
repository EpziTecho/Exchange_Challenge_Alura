package registros.conversion;

public class ConversionRegistro {
    private String monedaOrigen;
    private String monedaDestino;
    private double valorConvertido;
    private double tasaCambio;
    private String timestamp;

    public ConversionRegistro(String monedaOrigen, String monedaDestino, double valorConvertido, double tasaCambio, String timestamp) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.valorConvertido = valorConvertido;
        this.tasaCambio = tasaCambio;
        this.timestamp = timestamp;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public double getValorConvertido() {
        return valorConvertido;
    }

    public double getTasaCambio() {
        return tasaCambio;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
