package client;

import codage.Codage;

import java.io.IOException;

public class ClientEmprunt extends Client{
    private static final int port = 1001;

    public ClientEmprunt() throws IOException {
        super(port);
    }

    public void lancer() throws IOException {

        System.out.println("*** Bienvenue dans le client d'emprunt de document ***");

        String ligne;

        //TODO: renseigner le numéro de port dans la ligne de commande
        //TODO: faire un cycle permanent
        //TODO: while true (break) jusqu'à l'arrêt (TD4) soit avec le clavier ou fermeture de la connexion pour ne pas écrire toutes les questions
        //TODO: faire un seul fichier Client.
        //TODO: faire un batch (ligne de commande) pour lancer le client en renseignant le port dans la ligne

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
        ClientEmprunt client = new ClientEmprunt();
        client.lancer();
    }
}
