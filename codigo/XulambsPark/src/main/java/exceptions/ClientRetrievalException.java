package exceptions;

public class ClientRetrievalException extends ClientException {
    public ClientRetrievalException(String message) {
        super(message);
    }

    public ClientRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}