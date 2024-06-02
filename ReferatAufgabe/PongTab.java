package ReferatAufgabe;

import javax.swing.JFrame;

public class PongTab extends JFrame {
    public PongTab()
    {
        
        super("Pong Game"); // Setze den Titel des Fensters auf "Pong Game"
        PongSpiel pongSpielLogik    = new PongSpiel(); //Eine Instanz der PongSpiel Klasse, die die Spiellogik enthält
        setContentPane(pongSpielLogik); //Setze den Inhal des Fensters auf die Spiel-Logik
        setSize(1600, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Schließe Fenster wenn der Benutzer auf das Schließen Symbol klickt
        setLocationRelativeTo(null); // Das Fenster in Center auf dem Bildschirm plazieren
        pongSpielLogik.startPongGame(); // Starte das Spiel   
    }
}
