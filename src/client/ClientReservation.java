package client;

import java.io.IOException;

public class ClientReservation extends Client{
    private static final int port = 1002;

    public ClientReservation() throws IOException {
        super(port);
    }

    public void lancer() throws IOException {


        /*
        ici on fait notre échanges avec le serveur
         */

        fermer();
    }

    public static void main(String[] args) throws IOException {
        ClientRetour client = new ClientRetour();
        client.lancer();
    }
}
