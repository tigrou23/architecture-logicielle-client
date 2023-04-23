package client;

import codage.Codage;

import java.io.IOException;

public class ClientReservation extends Client{
    private static final int port = 1000;

    public ClientReservation() throws IOException {
        super(port);
    }

    public void lancer() throws IOException {

        System.out.println("*** Bienvenue dans le client de réservation de document ***");

        String ligne;
        ligne = Codage.decode(getSin().readLine());
        System.out.println(ligne);

        ligne = getClavier().readLine();
        getSout().println(Codage.encode(ligne));

        ligne = Codage.decode(getSin().readLine());
        System.out.println(ligne);

        ligne = getClavier().readLine();
        getSout().println(Codage.encode(ligne));

        System.out.println(Codage.decode(getSin().readLine()));

        fermer();
    }

    public static void main(String[] args) throws IOException {
        ClientReservation client = new ClientReservation();
        client.lancer();
    }
}
