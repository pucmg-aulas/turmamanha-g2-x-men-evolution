package model;

import model.ParkingSpot;
import model.SpotType;
import model.Vehicle;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpotTest {

    @Test
    public void testParkingSpotCreation() {
        ParkingSpot spot = new ParkingSpot("1", "Spot 1", SpotType.REGULAR);
        assertEquals("1", spot.getId());
        assertEquals(SpotType.REGULAR, spot.getType());
        assertFalse(spot.isOccupied());
    }

    @Test
    public void testOccupyAndVacateSpot() {
        ParkingSpot spot = new ParkingSpot("1","Spot 1", SpotType.REGULAR);
        Vehicle vehicle = new Vehicle("ABC-1234", "Model X", "Red", "Miguel", "12345678900");
        spot.occupy(vehicle);
        assertTrue(spot.isOccupied());
        assertEquals(vehicle, spot.getVehicle());
        assertNotNull(spot.getStartTime());

        spot.vacate();
        assertFalse(spot.isOccupied());
        assertNull(spot.getVehicle());
        assertNull(spot.getStartTime());
    }
}
