package exceptions;

public class VehicleUpdateException extends Exception {
    public VehicleUpdateException(String message) {
        super(message);
    }

    public VehicleUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}