package model;

import model.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    public void testVehicleCreation() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Model X", "Red", "Miguel", "12345678900");
        assertEquals("ABC-1234", vehicle.getPlaca());
        assertEquals("Model X", vehicle.getModel());
        assertEquals("Red", vehicle.getColor());
        assertEquals("Miguel", vehicle.getOwner());
        assertEquals("12345678900", vehicle.getCpf());
    }

    @Test
    public void testVehicleOwner() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Model X", "Red", "Miguel", "12345678900");
        assertEquals("Miguel", vehicle.getOwner());
    }
}
