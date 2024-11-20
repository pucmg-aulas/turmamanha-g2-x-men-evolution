package view;

import controller.ClientController;
import model.Client;

public class ClientView {
    private ClientController clientController;

    public ClientView(ClientController clientController) {
        this.clientController = clientController;
    }

    public Client show() {
        return clientController.show();
    }
}