package exceptions;

public class VehicleRegistrationException extends Exception {
    public VehicleRegistrationException(String message) {
        super(message);
    }

    public VehicleRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}