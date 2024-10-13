package registros.errores;

public class ErrorRegistro {
    private String errorMessage;
    private String userInput;
    private String timestamp;

    public ErrorRegistro(String errorMessage, String userInput, String timestamp) {
        this.errorMessage = errorMessage;
        this.userInput = userInput;
        this.timestamp = timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getUserInput() {
        return userInput;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
