package client;

import java.io.IOException;

public class ClientReservation extends Client{
    private static final int port = 1000;

    public ClientReservation() throws IOException {
        super(port);
    }

    public void lancer() throws IOException {

        //TODO: faire le client reservation

        fermer();
    }

    public static void main(String[] args) throws IOException {
        ClientRetour client = new ClientRetour();
        client.lancer();
    }
}
