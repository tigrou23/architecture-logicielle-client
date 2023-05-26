package client;

import codage.Codage;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO : Devons nous implémenter Runnable et faire des Threads (je ne vois pas pk) ? À voir avec le prof

public class Client {
    private BufferedReader clavier;
    private Socket socket;
    //private BufferedReader sin;
    private DataInputStream sin;
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
            //sin = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            sin = new DataInputStream(socket.getInputStream());
            //sin = socket.getInputStream();
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

/*    public void lancementActivite() throws IOException {

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
    }*/

    public void lancementActivite() throws IOException {

        String ligne;

        byte[] buffer1 = sin.readAllBytes();
        //*** Bienvenue dans le client ... + question ***
        byte[] buffer = new byte[1024]; // Définissez la taille du buffer de lecture
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = getSin().read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        ligne = outputStream.toString("UTF-8"); // Convertissez les bytes en une variable String avec encodage UTF-8 (ou un autre encodage selon vos besoins)
        System.out.println(ligne);


        //TODO : faire une sortie à l'initiative du client (touche clavier)
        while (true){
            //reponse
            ligne = getClavier().readLine();
            getSout().println(Codage.encode(ligne));

            //question ou fin
            lecture(getSin().readAllBytes());

            if(ligne.contains("fin - ")){
                break;
            }
        }
        fermer();
    }
    public static void lecture(byte[] data){
        if(data[data.length-1]==0){
            int durationSeconds = 30;
            try {
                // Créer un AudioInputStream à partir du tableau de bytes
                AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, false);
                AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(data), audioFormat, data.length);

                // Ouvrir le flux audio
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(audioFormat);

                // Démarrer la lecture
                line.start();

                // Lire les données musicales depuis l'AudioInputStream et les écrire dans la ligne audio
                byte[] buffer = new byte[4096];
                int bytesRead;

                long startTime = System.currentTimeMillis();
                long elapsedTime;
                while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                    line.write(buffer, 0, bytesRead);
                    elapsedTime = System.currentTimeMillis() - startTime;
                    if (elapsedTime >= durationSeconds * 1000) {
                        break;
                    }
                }

                // Attendre la fin de la lecture
                line.drain();
                line.close();
                audioInputStream.close();
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
        else{
            String texte = new String(data);
            System.out.println(texte);
        }
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

    public InputStream getSin() {
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