package exceptions;

public class ClientRegistrationException extends ClientException {
    public ClientRegistrationException(String message) {
        super(message);
    }

    public ClientRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}