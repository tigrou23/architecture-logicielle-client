package client;

import java.io.*;
import java.net.Socket;

import java.io.BufferedReader;
import java.util.Properties;

//TODO : je suis pas sur de l'archi. Je pense qu'on doit faire des Threads. À voir avec le prof

public abstract class Client {
    private BufferedReader clavier;
    private Socket socket;
    private BufferedReader sin;
    private PrintWriter sout;
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

    public void fermer() throws IOException {
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

}