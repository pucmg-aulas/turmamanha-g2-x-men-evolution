package model;

import model.Client;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testClientCreation() {
        UUID id = UUID.randomUUID();
        Client client = new Client("Miguel", id, "12345678900");
        assertEquals("Miguel", client.getName());
        assertEquals(id, client.getId());
        assertEquals("12345678900", client.getCpf());
    }

    @Test
    public void testAddVehicle() {
        UUID id = UUID.randomUUID();
        Client client = new Client("Miguel", id, "12345678900");
        Vehicle vehicle = new Vehicle("ABC-1234", "Model X", "Red", "Miguel", "12345678900");
        client.addVehicle(vehicle);
        assertEquals(1, client.getVehicles().size());
        assertEquals(vehicle, client.getVehicles().get(0));
    }
}
