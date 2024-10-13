package principal;

import modelo.Exchange;
import registros.conversion.ConversionRegistro;
import registros.errores.ErrorRegistro;
import registros.conversion.GeneradorDeArchivoConversiones;
import registros.errores.GeneradorDeArchivoErrores;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsultarExchange consultarExchange = new ConsultarExchange();
        GeneradorDeArchivoErrores generadorDeArchivoErrores = new GeneradorDeArchivoErrores();
        GeneradorDeArchivoConversiones generadorDeArchivoConversiones = new GeneradorDeArchivoConversiones();
        boolean salir = false;

        while (!salir) {
            mostrarMenu();  // Mostrar el menú
            int opcion = -1;
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Capturar la entrada inválida antes de limpiar el scanner
                String entradaInvalida = scanner.next();  // Capturar el valor que causó el error
                // Limpiar el scanner después de capturar la entrada inválida
                scanner.nextLine();
                registrarError(generadorDeArchivoErrores, "Entrada no válida en el menú. Solo se permiten números enteros.", entradaInvalida);
                System.out.println("Error: Solo se permiten números enteros.");
                continue;  // Volver a mostrar el menú
            }
            // Evaluamos la opción del usuario
            switch (opcion) {
                case 1:
                    convertirMoneda(consultarExchange, "USD", "ARS", scanner, generadorDeArchivoErrores, generadorDeArchivoConversiones);
                    break;
                case 2:
                    convertirMoneda(consultarExchange, "ARS", "USD", scanner, generadorDeArchivoErrores, generadorDeArchivoConversiones);
                    break;
                case 3:
                    convertirMoneda(consultarExchange, "USD", "BRL", scanner, generadorDeArchivoErrores, generadorDeArchivoConversiones);
                    break;
                case 4:
                    convertirMoneda(consultarExchange, "BRL", "USD", scanner, generadorDeArchivoErrores, generadorDeArchivoConversiones);
                    break;
                case 5:
                    convertirMoneda(consultarExchange, "USD", "COP", scanner, generadorDeArchivoErrores, generadorDeArchivoConversiones);
                    break;
                case 6:
                    convertirMoneda(consultarExchange, "COP", "USD", scanner, generadorDeArchivoErrores, generadorDeArchivoConversiones);
                    break;
                case 7:
                    salir = true;
                    System.out.println("Gracias por usar el conversor de moneda. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción fuera de rango. Por favor, elija una opción válida.");
            }
        }
        // Guardar los errores al final del programa
        try {
            generadorDeArchivoErrores.guardarErrores();
            generadorDeArchivoConversiones.guardarConversiones();
        } catch (IOException e) {
            System.out.println("Error al guardar los errores o conversiones: " + e.getMessage());
        }
        scanner.close();
    }

    // Método para mostrar el menú
    public static void mostrarMenu() {
        System.out.println("********************************************");
        System.out.println("** Bienvenido/a al Conversor de Moneda =] **");
        System.out.println("********************************************");
        System.out.println("1) Dólar (USD) => Peso argentino (ARS)");
        System.out.println("2) Peso argentino (ARS) => Dólar (USD)");
        System.out.println("3) Dólar (USD) => Real brasileño (BRL)");
        System.out.println("4) Real brasileño (BRL) => Dólar (USD)");
        System.out.println("5) Dólar (USD) => Peso colombiano (COP)");
        System.out.println("6) Peso colombiano (COP) => Dólar (USD)");
        System.out.println("7) Salir");
        System.out.println("********************************************");
        System.out.print("Elija una opción válida: ");
    }

    // Método para registrar los errores en el archivo JSON
    public static void registrarError(GeneradorDeArchivoErrores generador, String mensajeError, String entradaUsuario) {
        if (entradaUsuario == null || entradaUsuario.trim().isEmpty()) {
            entradaUsuario = "Entrada vacía";  // Asignar un valor por defecto si la entrada es vacía
        }
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        ErrorRegistro error = new ErrorRegistro(mensajeError, entradaUsuario, timestamp);
        generador.registrarError(error);
    }

    // Método para registrar las conversiones en el archivo JSON
    public static void registrarConversion(GeneradorDeArchivoConversiones generador, String monedaOrigen, String monedaDestino, double valorConvertido, double tasaCambio) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        ConversionRegistro conversion = new ConversionRegistro(monedaOrigen, monedaDestino, valorConvertido, tasaCambio, timestamp);
        generador.registrarConversion(conversion);
    }

    // Método para mostrar la tasa de cambio entre dos monedas y realizar la conversión
    public static void convertirMoneda(ConsultarExchange consultarExchange, String monedaOrigen, String monedaDestino, Scanner scanner, GeneradorDeArchivoErrores generadorDeArchivoErrores, GeneradorDeArchivoConversiones generadorDeArchivoConversiones) {
        Exchange exchange = consultarExchange.consultarTasaCambio(monedaOrigen);
        if (exchange != null) {
            Double tasa = exchange.conversion_rates().get(monedaDestino); // Obtener la tasa de cambio para la moneda destino

            if (tasa != null) {
                System.out.println("La tasa de cambio de " + monedaOrigen + " a " + monedaDestino + " es: " + tasa);
                System.out.print("Ingrese el valor a convertir de " + monedaOrigen + ": ");
                try {
                    double valor = scanner.nextDouble();  // Validar que el valor ingresado sea un número decimal
                    double valorConvertido = valor * tasa; // Realizar la conversión
                    // Imprimir el resultado en el formato solicitado
                    System.out.printf("El valor %.2f [%s] corresponde al valor final de =>> %.2f [%s]\n",
                            valor, monedaOrigen, valorConvertido, monedaDestino);
                    registrarConversion(generadorDeArchivoConversiones, monedaOrigen, monedaDestino, valorConvertido, tasa); // Registrar la conversión
                } catch (InputMismatchException e) {
                    // Registrar el error de la conversión y limpiar el buffer
                    registrarError(generadorDeArchivoErrores, "Error de conversión. Solo se permiten números decimales.", scanner.next());
                    System.out.println("Error: Solo se permiten números decimales.");
                    scanner.nextLine();  // Limpiar el buffer después de la captura
                }
            } else {
                System.out.println("No se encontró la tasa de cambio para " + monedaDestino);
                // Registrar el error de que no se encontró la tasa de cambio
                registrarError(generadorDeArchivoErrores, "No se encontró la tasa de cambio para " + monedaDestino, monedaOrigen + " a " + monedaDestino);
            }
        } else {
            System.out.println("Error al consultar la tasa de cambio.");
            // Registrar el error de consulta a la API
            registrarError(generadorDeArchivoErrores, "Error al consultar la tasa de cambio", monedaOrigen);
        }
    }
}
