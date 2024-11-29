package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    @Test
    public void testParkingLotCreation() {
        ParkingLot lot = new ParkingLot("Main Lot", 100, SpotType.REGULAR);
        assertEquals("Main Lot", lot.getName());
        assertEquals(100, lot.getNumberOfSpots());
        assertEquals(SpotType.REGULAR, lot.getDefaultSpotType());
    }

    @Test
    public void testAddAndRetrieveSpot() {
        ParkingLot lot = new ParkingLot("Main Lot");
        ParkingSpot spot = new ParkingSpot("1", "Spot 1", SpotType.REGULAR);
        lot.getSpots().put(spot.getId(), spot);
        assertEquals(spot, lot.getSpot("1"));
    }
}