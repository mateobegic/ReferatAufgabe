package ReferatAufgabe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.TreeMap;
//import java.util.Map;

public class IntroScreen extends JFrame {
    
    private JLabel titleLabel;
    private JLabel textLabel;
    private JButton startButton;

    public IntroScreen() {
        super("Willkommen zu Pong Game"); // Setze den Fenster Titel
        
        /**
         * Erstelllung das Titel-Labels, gür den Intro Screen
         * Hierbei sagt man auch Welche Textart es haben soll, welche größe und wo es plaziert sein soll
         **/ 
        titleLabel = new JLabel("Willkommen zu PONG - Dem Klassiker unter den Videospielen!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

         
        //Mithilfe von html wird der Haupttext eingepflegt und wie der Titel formatiert
        String text = "<html><div style='text-align: center;'>" +
        "Tauche ein in einen Oldtimer, einem der ersten und einflussreichsten Videospiele aller zeiten, Pong. 1972 wurde Pong von Atari veröffentlicht und ist auf eine Art und Weise bekannt für die revolution von Spielen. Pong simuliert ein Duell zwischen zwei Spielern, die jeweils einen 'Schläger' steuern und versuchen, den Ball am Gegner vorbeizuschlagen. Mit seinen minimalistischen Grafiken und dem eingängigen Gameplay bietet PONG sowohl Anfängern als auch erfahrenen Spielern eine zeitlose Unterhaltung. Bist du bereit, die Herausforderung anzunehmen und die Legende von PONG fortzuführen? Greif zu deinem Schläger und zeig, was du drauf hast - das Spiel beginnt jetzt!" +
        "</div></html>";
        textLabel     = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);

        /**
         * Hier erstellen wir das "Starte das Pong Spiel" button
         * Anbei sagen wir was passieren soll, wenn das Button geklickt wurde
         */
        startButton = new JButton("Starte das Pong Spiel!");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PongTab pongTab     = new PongTab();
                pongTab.setVisible(true);
                IntroScreen.this.setVisible(false); //Nachdem das Spiel gestartet wird, verstecken wir das Intro Screen
            }
        });

        // Setze das Layout des Intro Screens mit den entsprechenden Komponenten
        setLayout(new BorderLayout());

        // Erstelle ein Panel(Container) für den Titel und Zentriere es
        JPanel  titelPanel = new JPanel(new BorderLayout());
        titelPanel.add(titleLabel, BorderLayout.CENTER);

        // Füge die Komponenten zu den Fenster hinzu
        add(titelPanel, BorderLayout.NORTH);
        add(textLabel,BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);

        // Setze die Fenstereigenschaften wie Größe und Position
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new IntroScreen(); // Erstelle einen Intro Screen und Zeige ihn
            }
        });
    }
}

