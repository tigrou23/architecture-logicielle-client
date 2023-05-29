package client;

import codage.Codage;

import java.io.*;
import java.net.Socket;

import java.io.BufferedReader;
import java.util.Properties;

//TODO : Devons nous implémenter Runnable et faire des Threads (je ne vois pas pk) ? À voir avec le prof

public class Client {
    private BufferedReader clavier;
    private Socket socket;
    private BufferedReader sin;
    private PrintWriter sout;

    //pour run sur Intellij : private final static String CONFIG_PATH = "src/ressources/config.properties";
    //pour run sur ordinateur : private final static String CONFIG_PATH = "config.properties";
    private final static String CONFIG_PATH = "src/ressources/config.properties";

    public Client(int port) throws IOException {
        clavier = new BufferedReader(new InputStreamReader(System.in));
        socket = null;
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream(CONFIG_PATH);
        properties.load(inputStream);
        try {
            socket = new Socket(properties.getProperty("server.address"), port);
            sin = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            sout = new PrintWriter (socket.getOutputStream ( ), true);
            System.out.println("Connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());
        }
        catch (IOException e) {
            System.err.println("Impossible de se connecter au serveur");
            System.exit(1);
        }
        /*try { if (socket != null) socket.close(); } //TODO : ici ça fait planter le programme, à étudier
        catch (IOException e2) { ; }*/
    }

    public void lancementActivite() throws IOException {

        String ligne;

        //*** Bienvenue dans le client ... + question ***
        ligne = Codage.decode(getSin().readLine());
        System.out.println(ligne);

        //TODO : faire une sortie à l'initiative du client (touche clavier)
        while (true){
            //reponse
            ligne = getClavier().readLine();
            getSout().println(Codage.encode(ligne));

            //question ou fin
            ligne = Codage.decode(getSin().readLine());
            System.out.println(ligne);

            if(ligne.contains("fin - ")){
                break;
            }
        }
        fermer();
    }

    public void fermer() throws IOException {
        clavier.close();
        sin.close();
        sout.close();
        socket.close();
    }

    public BufferedReader getClavier() {
        return clavier;
    }

    public BufferedReader getSin() {
        return sin;
    }

    public PrintWriter getSout() {
        return sout;
    }

    public static void main(String[] args) throws IOException {
        try {
            if (args.length != 1) {
                System.err.println("Usage: java Client <port du serveur>");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Usage: java Client <port du serveur>");
            System.exit(1);
        }
        Client client = new Client(Integer.parseInt(args[0]));
        client.lancementActivite();
    }

}