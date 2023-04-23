package client;

import java.io.IOException;

public class ClientEmprunt extends Client{
    private static final int port = 1001;

    public ClientEmprunt() throws IOException {
        super(port);
    }

    public void lancer() throws IOException {

        //TODO: faire le client emprunt

        fermer();
    }

    public static void main(String[] args) throws IOException {
        ClientEmprunt client = new ClientEmprunt();
        client.lancer();
    }
}
