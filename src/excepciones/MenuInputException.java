package excepciones;

public class MenuInputException extends Exception {

   private String mensaje;

   public MenuInputException(String mensaje) {
      this.mensaje = mensaje;
   }

   public String getMessage() {
      return this.mensaje;
   }


}
