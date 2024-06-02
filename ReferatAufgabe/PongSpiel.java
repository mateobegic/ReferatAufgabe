package ReferatAufgabe;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class PongSpiel extends JPanel implements ActionListener {
    // Konstanten für das Spielfeld, die Paddel (Spieler) und den Ball definieren
    private static int WIDTH            = 800;
    private static int HEIGHT           = 600;
    private static int PADDEL_WIDTH     = 10;
    private static int PADDEL_HEIGHT    = 100;
    private static int BALL_SIZE        = 20;
    private static int PADDEL_SPEED     = 5;
    private static int BORDER_THICKNESS = 10;
    private static int PADDEL_MARGIN    = 20; // Margin between paddle and border
    private static int MAX_GAMES        = 5;
    private static int MAX_SCORE        = 7;
    
    private String[] lastFiveGames;// Array für das Speichern der letzten fünf Spiele
    private int CurrentGameNumber; // Zähler der zuständig ist, die aktuelle Spielnummer zu errmitteln

    // Position und Geschwindigkeit vom Ball
    private int BallPosX                = WIDTH / 2 - BALL_SIZE /2;
    private int BallPosY                = HEIGHT / 2 - BALL_SIZE / 2;
    private int BallSpeedX              = 5;
    private int BallSpeedY              = 5;

    // Positionen der Paddel und Punktestände der Spieler
    private int player1Y                = HEIGHT /2 - PADDEL_HEIGHT/2;
    private int player2Y                = HEIGHT /2 - PADDEL_HEIGHT/2;
    private int scorePlayer1            = 0;
    private int scorePlayer2            = 0;

    // Status der gedrückten Tasten, default auf false gesetzt
    private boolean upPressedButton     = false;
    private boolean downPressedButton   = false;
    private boolean wPressedButton      = false;
    private boolean sPressedButton      = false;

    private Timer gameTimer; // Timer für das Spiel

    public PongSpiel() 
    {
        setBackground(Color.BLACK); // Setze Hintergrunfarbe auf Schwarz
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e)
            {
                // Reagiere auf Tastendrücke
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        upPressedButton = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        downPressedButton   = true;
                        break;
                    case KeyEvent.VK_W:
                        wPressedButton      = true;
                        break;
                    case KeyEvent.VK_S:
                        sPressedButton      = true;
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e)
            {
                // Reagiere auf das Loslassen der Tasten
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        upPressedButton = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        downPressedButton = false;
                        break;
                    case KeyEvent.VK_W:
                        wPressedButton = false;
                        break;
                    case KeyEvent.VK_S:
                        sPressedButton = false;
                        break;
                }
            }
        });
        // Initialisiere den Timer, das Array und die aktuelle Spielernummer
        gameTimer = new Timer(16, this);
        lastFiveGames = new String[MAX_GAMES]; 
        CurrentGameNumber = 0; 
    }

    public void startPongGame()
    {
        // Starte das Spiel
        gameTimer.start();
    }

    public void update()
    {
        // Aktuallisiere die Positionen und den Status im Spiel
        if(upPressedButton && player2Y > BORDER_THICKNESS)
        {
            player2Y  -= PADDEL_SPEED;
        }
        if (downPressedButton && player2Y < HEIGHT - BORDER_THICKNESS - PADDEL_HEIGHT) 
        {
            player2Y  += PADDEL_SPEED;
        }
        if (wPressedButton && player1Y > BORDER_THICKNESS)
        {
            player1Y    -= PADDEL_SPEED;
        }
        if (sPressedButton && player1Y < HEIGHT - BORDER_THICKNESS - PADDEL_HEIGHT)
        {
            player1Y    += PADDEL_SPEED;
        }

        // Bewege den Ball in X und Y Richtung
        BallPosX += BallSpeedX;
        BallPosY += BallSpeedY;
        
        // Ball prallt an den oberen und unteren Rändern ab
        if (BallPosY <=0 || BallPosY >= HEIGHT - BALL_SIZE)
        {
            BallSpeedY *= -1;
        }

        // Ball Prallt an den Paddeln (Spieler) ab 
        if(BallPosX <= PADDEL_MARGIN + PADDEL_WIDTH && BallPosY + BALL_SIZE >= player1Y && BallPosY <= player1Y + PADDEL_HEIGHT)
        {
            BallSpeedX  *= -1;
        }
        if (BallPosX >= WIDTH - PADDEL_MARGIN - PADDEL_WIDTH - BALL_SIZE && BallPosY + BALL_SIZE >= player2Y && BallPosY <= player2Y + PADDEL_HEIGHT)
        {
            BallSpeedX *= -1;   
        }

        /**
         * Ball Verlässt das Spielfeld auf der linken Seite, bzw geht an den Spieler vorbei
         * der Spieler 2 kriegt Punkt
         * Wenn der Punktestand von Spieler 2 dem Maximalen Punktestand entspricht endet das Spiel, wenn nicht geht der Ball an Anfangsposition
         */
        if (BallPosX <= 0)
        {
            scorePlayer2++;
            if (scorePlayer2 >= MAX_SCORE) {
                endGame();
            }
            else
            {
                resetBall();
            }   
        }

        /**
         * Ball Verlässt das Spielfeld auf der rechten Seite, bzw geht an den Spieler vorbei
         * der Spieler 1 kriegt Punkt
         * Wenn der Punktestand von Spieler 1 dem Maximalen Punktestand entspricht endet das Spiel, wenn nicht geht der Ball an Anfangsposition
         */
        else if (BallPosX >= WIDTH - BALL_SIZE)
        {
            scorePlayer1 ++;
            if (scorePlayer1 >= MAX_SCORE)
            {
                endGame();   
                scorePlayer1 = 0;
            }
            else
            {
                resetBall();
            }
        }

    }

    private void resetBall() {
        // Setze die Position des Balls in die Mitte von Spielfeld zurück
        BallPosX = WIDTH / 2 - BALL_SIZE / 2;
        BallPosY = HEIGHT / 2 - BALL_SIZE / 2;
    }

    public void endGame()
    {   
        // Speichere das Ergebnis des Aktuellen spiels
        lastFiveGames[CurrentGameNumber]    = "Player 1: " + scorePlayer1 + " | "  + scorePlayer2 + "-Player 2: ";
        CurrentGameNumber                   = (CurrentGameNumber + 1)% MAX_GAMES;

        // Zeige die Ergebnisse der letzten 5 Spielen an
        displayLastGames();

        // Setze die Punktestände der Spieler zurück
        scorePlayer1 = 0;
        scorePlayer2 = 0;

        resetBall();
    }

    public void displayLastGames()
    {
        StringBuilder resultsOfLastGames    = new StringBuilder("Last 5 Games:\n");

        // Zeige die letzten 5 Spiele an
        for (int i = 0; i < MAX_GAMES; i++)
        {
            if (lastFiveGames[i] !=null)
            {
                resultsOfLastGames.append(lastFiveGames[i]).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, resultsOfLastGames.toString(), "Game Results", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Aktuallisiere das Spiel und zeichne es neu
        update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        
        // Zeichne die Linie die sich im Mitte des Spielfelds befindet
        for (int i = 0; i < HEIGHT; i += 30) 
        {
            g.fillRect(WIDTH/ 2 -1, i, 2, 10);
        }

        // Zeichne die Ränder des Spielfelds
        g.fillRect(0, 0, WIDTH, BORDER_THICKNESS); //Oberer Rand
        g.fillRect(0, HEIGHT - BORDER_THICKNESS, WIDTH, BORDER_THICKNESS); //Unterer Rand
        g.fillRect(0, 0, BORDER_THICKNESS, HEIGHT); //Linker Rand
        g.fillRect(WIDTH - BORDER_THICKNESS, 0, BORDER_THICKNESS, HEIGHT); //Rechter Rand

        // Zeichne die Paddel (Spieler)
        g.fillRect(BORDER_THICKNESS + PADDEL_MARGIN, player1Y, PADDEL_WIDTH, PADDEL_HEIGHT);
        g.fillRect(WIDTH - PADDEL_WIDTH - BORDER_THICKNESS - PADDEL_MARGIN, player2Y, PADDEL_WIDTH, PADDEL_HEIGHT);
        
        // Zeichne den Ball 
        g.fillOval(BallPosX, BallPosY,BALL_SIZE,BALL_SIZE);

        // Zeichne den Punktestand
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + scorePlayer1 +" : " + scorePlayer2, WIDTH / 2 - 50, BORDER_THICKNESS + 20);
    }
}

