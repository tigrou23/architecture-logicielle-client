package codage;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Codage {
    public Codage() {
    }

    public static byte[] encode(String texte) {
        File file = new File(texte);
        byte[] data;
        byte[] dataCopie;
        try (FileInputStream fis = new FileInputStream(file)) {
            dataCopie = new byte[(int) file.length()];
            fis.read(dataCopie);
            int marqueurTexte = 255;
            data = new byte[dataCopie.length + 1];
            System.arraycopy(dataCopie, 0, data, 1, dataCopie.length);
            data[0] = (byte) marqueurTexte;
        } catch (IOException e) {
            dataCopie = texte.getBytes();
            int marqueurTexte = 0;
            data = new byte[dataCopie.length + 1];
            System.arraycopy(dataCopie, 0, data, 1, dataCopie.length);
            data[0] = (byte) marqueurTexte;
        }
        return data;
    }

    public static String decode(byte[] data, int taille) {return new String(data,0,taille);}

    public static void lectureMusique(byte[] data, long temps) throws IOException {
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

            // Lire les données musicales pendant la durée spécifiée
            byte[] buffer = new byte[4096];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            long elapsedTime;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
                elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= temps * 1000) {
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
}
