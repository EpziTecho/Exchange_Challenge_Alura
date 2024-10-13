# Conversor de Monedas

Este proyecto es un **Conversor de Monedas** que interactúa con una API de tasas de cambio para realizar conversiones entre diferentes divisas. Además, registra las conversiones exitosas y los errores generados durante la ejecución del programa. Todo esto se almacena en archivos JSON para un fácil acceso y análisis posterior.

## Características

- Convierte entre diversas monedas utilizando tasas de cambio actualizadas a través de una API.
- Registra automáticamente las conversiones exitosas, incluyendo las tasas de cambio utilizadas y la fecha de la conversión.
- Maneja los errores de entrada del usuario y los almacena en un archivo JSON para su revisión.
- La aplicación guarda tanto los errores como las conversiones en archivos JSON, permitiendo la persistencia de los datos entre ejecuciones.

## Monedas Disponibles para Conversión

- Dólar estadounidense (USD) ⇄ Peso argentino (ARS)
- Dólar estadounidense (USD) ⇄ Real brasileño (BRL)
- Dólar estadounidense (USD) ⇄ Peso colombiano (COP)

## Requisitos Previos

- **Java 8** o una versión posterior instalada.
- **Gson** (librería para manejar JSON), se utiliza en el proyecto para serializar y deserializar los datos a/desde archivos JSON.

### Librerías

- [Gson](https://github.com/google/gson) (incluida en el proyecto)

## Estructura del Proyecto

El proyecto está compuesto por las siguientes clases:

### 1. **`Principal`**: 
Clase principal que contiene el menú interactivo y el flujo principal del programa. Maneja las opciones seleccionadas por el usuario y llama a los métodos para realizar conversiones y manejar errores.

### 2. **`ConsultarExchange`**:
Clase responsable de interactuar con la API de tasas de cambio y devolver las tasas de cambio para las monedas solicitadas.

### 3. **`GeneradorDeArchivoErrores`**:
Clase que gestiona la carga, el registro y el guardado de errores en un archivo JSON (`errores.json`). Registra los errores de entrada del usuario y los almacena con una marca de tiempo para análisis futuro.

### 4. **`ErrorRegistro`**:
Clase que representa un error. Contiene el mensaje de error, la entrada del usuario que causó el error y la fecha/hora en la que ocurrió.

### 5. **`GeneradorDeArchivoConversiones`**:
Clase que gestiona la carga, el registro y el guardado de conversiones exitosas en un archivo JSON (`conversiones.json`). Almacena detalles de cada conversión, incluyendo la moneda origen, moneda destino, el valor convertido, la tasa de cambio y la fecha/hora de la conversión.

### 6. **`ConversionRegistro`**:
Clase que representa una conversión exitosa. Contiene los detalles de la conversión, como la moneda origen, moneda destino, valor convertido, tasa de cambio y la fecha/hora en que se realizó.

## Funcionamiento del Programa

1. **Menú Principal**: El programa muestra un menú de opciones para convertir entre diferentes monedas. El usuario selecciona una opción y luego ingresa el monto que desea convertir.
   
2. **Conversión**: Tras la selección, el programa obtiene las tasas de cambio desde la API y realiza la conversión, mostrando el resultado al usuario.

3. **Registro de Conversiones**: Si la conversión es exitosa, se almacena en el archivo `conversiones.json`, guardando la moneda origen, destino, el valor convertido y la tasa de cambio utilizada.

4. **Manejo de Errores**: Si el usuario ingresa un valor no válido (por ejemplo, texto en lugar de números), el error se captura y se almacena en el archivo `errores.json` con detalles de la entrada incorrecta y el mensaje de error.

5. **Almacenamiento**: Los errores y las conversiones se almacenan en archivos JSON para su consulta posterior, y se cargan automáticamente al inicio del programa para no perder registros.
