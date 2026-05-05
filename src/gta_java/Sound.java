package gta_java;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30]; // Arreglo para almacenar las rutas de tus sonidos

    public Sound() {
        // Asigna índices a los sonidos
        soundURL[0] = getClass().getResource("/res/sound/powerup.wav"); // Sonido para el corazón
        soundURL[1] = getClass().getResource("/res/sound/fondo2.wav"); // Música de fondo
    }

    public void loop() { 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }
}