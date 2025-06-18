/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sena.edu.co.yugioh;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 *
 * @author Aprendiz
 */
public class GameYuGiOh extends javax.swing.JFrame {
    private static final String API_URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?name=";
    private int player1Life = 8000;
    private int player2Life = 8000;
    private YuGiOhCard[] player1Cards = new YuGiOhCard[3];
    private YuGiOhCard[] player2Cards = new YuGiOhCard[3];
    private String player1Choice = "";
    private String player2Choice = "";
    private final String[] cardPool = {
        "Blue-Eyes White Dragon", "Dark Magician", "Red-Eyes Black Dragon",
        "Elemental HERO Sparkman", "Kuriboh", "Celtic Guardian",
        "Summoned Skull", "Gaia The Fierce Knight", "Curse of Dragon",
        "Time Wizard", "Beaver Warrior", "Fissure", "Mirror Force", 
        "Trap Hole", "Mystical Space Typhoon", "Pot of Greed",
        "Raigeki", "Monster Reborn", "Sangan", "Witch of the Black Forest"
    };

    // Variables de interfaz adicionales
    private JButton attackButton;
    private JButton defendButton;
    private JLabel[] playerCardLabels = new JLabel[3];
    private JLabel[] opponentCardLabels = new JLabel[3];
    private JTextArea logTextArea;
    private JScrollPane logScrollPane;
    private JLabel playerLifeLabel;
    private JLabel opponentLifeLabel;
    private JLabel gameStatusLabel;
    
    // Variables para las imágenes de las cartas
    private JLabel[] playerCardImages = new JLabel[3];
    private JLabel[] opponentCardImages = new JLabel[3];

    public GameYuGiOh() {
        initComponents(); // Llama al método generado automáticamente
        setupCustomComponents(); // Configura componentes adicionales
        setupGame(); // Configura la lógica del juego
    }

    private void setupCustomComponents() {
        // Crear componentes adicionales que no están en el diseñador
        logTextArea = new JTextArea(10, 30);
        logTextArea.setEditable(false);
        logTextArea.setBackground(new Color(240, 240, 240));
        logTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        logScrollPane = new JScrollPane(logTextArea);
        logScrollPane.setBounds(50, 450, 300, 150);
        getContentPane().add(logScrollPane);
        
        // Labels para mostrar vida
        playerLifeLabel = new JLabel("Tu Vida: 8000 LP");
        playerLifeLabel.setBounds(50, 400, 150, 30);
        playerLifeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        playerLifeLabel.setForeground(Color.BLUE);
        getContentPane().add(playerLifeLabel);
        
        opponentLifeLabel = new JLabel("Oponente: 8000 LP");
        opponentLifeLabel.setBounds(50, 20, 150, 30);
        opponentLifeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        opponentLifeLabel.setForeground(Color.RED);
        getContentPane().add(opponentLifeLabel);
        
        // Label para estado del juego
        gameStatusLabel = new JLabel("¡Preparándose para la batalla!");
        gameStatusLabel.setBounds(400, 450, 300, 30);
        gameStatusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gameStatusLabel.setForeground(new Color(139, 69, 19));
        getContentPane().add(gameStatusLabel);
        
        // Crear labels para imágenes de cartas del jugador
        for (int i = 0; i < 3; i++) {
            playerCardImages[i] = new JLabel();
            playerCardImages[i].setBounds(50 + (i * 160), 250, 140, 100);
            playerCardImages[i].setBorder(createCardBorder());
            playerCardImages[i].setBackground(Color.WHITE);
            playerCardImages[i].setOpaque(true);
            getContentPane().add(playerCardImages[i]);
        }
        
        // Crear labels para imágenes de cartas del oponente
        for (int i = 0; i < 3; i++) {
            opponentCardImages[i] = new JLabel();
            opponentCardImages[i].setBounds(50 + (i * 160), 60, 140, 100);
            opponentCardImages[i].setBorder(createCardBorder());
            opponentCardImages[i].setBackground(Color.WHITE);
            opponentCardImages[i].setOpaque(true);
            getContentPane().add(opponentCardImages[i]);
        }
        
        // Redimensionar la ventana para acomodar todos los elementos
        setSize(800, 650);
        setLocationRelativeTo(null);
    }
    
    private Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createLoweredBevelBorder()
        );
    }

    private void setupGame() {
        attackButton = jButton2;
        defendButton = jButton1;
        
        // Configurar arrays de labels para texto de cartas
        playerCardLabels[0] = jLabel5;
        playerCardLabels[1] = jLabel6;
        playerCardLabels[2] = jLabel7;
        
        opponentCardLabels[0] = jLabel2;
        opponentCardLabels[1] = jLabel3;
        opponentCardLabels[2] = jLabel4;
        
        // Reposicionar labels de texto
        jLabel5.setBounds(50, 360, 140, 40);
        jLabel6.setBounds(210, 360, 140, 40);
        jLabel7.setBounds(370, 360, 140, 40);
        
        // Configurar botones
        attackButton.setBounds(550, 280, 100, 40);
        attackButton.setText("ATACAR");
        attackButton.setBackground(new Color(220, 20, 20));
        attackButton.setForeground(Color.WHITE);
        attackButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        defendButton.setBounds(550, 330, 100, 40);
        defendButton.setText("DEFENDER");
        defendButton.setBackground(new Color(20, 20, 220));
        defendButton.setForeground(Color.WHITE);
        defendButton.setFont(new Font("Arial", Font.BOLD, 12));

        attackButton.addActionListener(e -> makeChoice("attack"));
        defendButton.addActionListener(e -> makeChoice("defend"));

        // Configurar fondo
        getContentPane().setBackground(new Color(139, 69, 19));
        
        startNewRound();
    }

    private void startNewRound() {
        player1Choice = "";
        player2Choice = "";
        attackButton.setEnabled(true);
        defendButton.setEnabled(true);
        gameStatusLabel.setText("¡Elige tu acción!");
        
        logTextArea.append("\n=== NUEVA RONDA ===\n");
        updateLifeDisplay();
        
        // Limpiar imágenes anteriores
        clearCardImages();
        
        loadRandomCards(true);
        loadRandomCards(false);
    }
    
    private void clearCardImages() {
        for (int i = 0; i < 3; i++) {
            playerCardImages[i].setIcon(null);
            playerCardImages[i].setText("Cargando...");
            playerCardImages[i].setHorizontalAlignment(SwingConstants.CENTER);
            
            opponentCardImages[i].setIcon(null);
            opponentCardImages[i].setText("Cargando...");
            opponentCardImages[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
    
    private void updateLifeDisplay() {
        playerLifeLabel.setText("Tu Vida: " + player1Life + " LP");
        opponentLifeLabel.setText("Oponente: " + player2Life + " LP");
        
        // Cambiar color según la vida
        if (player1Life < 3000) {
            playerLifeLabel.setForeground(Color.RED);
        } else if (player1Life < 5000) {
            playerLifeLabel.setForeground(Color.ORANGE);
        } else {
            playerLifeLabel.setForeground(Color.BLUE);
        }
        
        if (player2Life < 3000) {
            opponentLifeLabel.setForeground(Color.RED);
        } else if (player2Life < 5000) {
            opponentLifeLabel.setForeground(Color.ORANGE);
        } else {
            opponentLifeLabel.setForeground(Color.RED);
        }
    }

    private void loadRandomCards(boolean isPlayer) {
        for (int i = 0; i < 3; i++) {
            int index = i;
            String randomCard = cardPool[(int) (Math.random() * cardPool.length)];
            new Thread(() -> {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(API_URL + randomCard.replace(" ", "%20")))
                            .build();
                    HttpResponse<String> response = client.send(request, 
                            HttpResponse.BodyHandlers.ofString());
                    
                    if (response.statusCode() == 200) {
                        JSONObject json = new JSONObject(response.body());
                        JSONArray data = json.getJSONArray("data");
                        if (data.length() > 0) {
                            JSONObject card = data.getJSONObject(0);
                            int attack = card.optInt("atk", (int) (Math.random() * 2000) + 1000);
                            int defense = card.optInt("def", (int) (Math.random() * 2000) + 1000);
                            String name = card.getString("name");
                            
                            // Obtener URL de la imagen
                            String imageUrl = null;
                            if (card.has("card_images")) {
                                JSONArray images = card.getJSONArray("card_images");
                                if (images.length() > 0) {
                                    imageUrl = images.getJSONObject(0).getString("image_url_small");
                                }
                            }

                            final String finalImageUrl = imageUrl;
                            SwingUtilities.invokeLater(() -> {
                                if (isPlayer) {
                                    player1Cards[index] = new YuGiOhCard(name, attack, defense, finalImageUrl);
                                    updateCardDisplay(playerCardLabels[index], playerCardImages[index], 
                                                    name, attack, defense, finalImageUrl);
                                } else {
                                    player2Cards[index] = new YuGiOhCard(name, attack, defense, finalImageUrl);
                                    updateCardDisplay(opponentCardLabels[index], opponentCardImages[index], 
                                                    name, attack, defense, finalImageUrl);
                                    if (index == 2) { // Última carta cargada
                                        simulateOpponentChoice();
                                    }
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Valores por defecto si falla la API
                    int attack = (int) (Math.random() * 2000) + 1000;
                    int defense = (int) (Math.random() * 2000) + 1000;

                    SwingUtilities.invokeLater(() -> {
                        if (isPlayer) {
                            player1Cards[index] = new YuGiOhCard(randomCard, attack, defense, null);
                            updateCardDisplay(playerCardLabels[index], playerCardImages[index], 
                                            randomCard, attack, defense, null);
                        } else {
                            player2Cards[index] = new YuGiOhCard(randomCard, attack, defense, null);
                            updateCardDisplay(opponentCardLabels[index], opponentCardImages[index], 
                                            randomCard, attack, defense, null);
                            if (index == 2) {
                                simulateOpponentChoice();
                            }
                        }
                    });
                }
            }).start();
        }
    }
    
    private void updateCardDisplay(JLabel textLabel, JLabel imageLabel, 
                                 String name, int attack, int defense, String imageUrl) {
        // Actualizar texto de la carta
        String displayText = "<html><center>" + name + "<br>ATK: " + attack + "<br>DEF: " + defense + "</center></html>";
        textLabel.setText(displayText);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        
        // Cargar imagen de la carta
        if (imageUrl != null) {
            new Thread(() -> {
                try {
                    URL url = new URL(imageUrl);
                    BufferedImage originalImage = ImageIO.read(url);
                    Image scaledImage = originalImage.getScaledInstance(140, 100, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(scaledImage);
                    
                    SwingUtilities.invokeLater(() -> {
                        imageLabel.setIcon(icon);
                        imageLabel.setText("");
                    });
                } catch (IOException e) {
                    SwingUtilities.invokeLater(() -> {
                        imageLabel.setText("<html><center>Carta<br>" + name.substring(0, Math.min(name.length(), 15)) + "</center></html>");
                        imageLabel.setFont(new Font("Arial", Font.BOLD, 10));
                    });
                }
            }).start();
        } else {
            imageLabel.setText("<html><center>Carta<br>" + name.substring(0, Math.min(name.length(), 15)) + "</center></html>");
            imageLabel.setFont(new Font("Arial", Font.BOLD, 10));
        }
    }

    private void makeChoice(String choice) {
        player1Choice = choice;
        attackButton.setEnabled(false);
        defendButton.setEnabled(false);
        String choiceText = choice.equals("attack") ? "ATACAR" : "DEFENDER";
        logTextArea.append("Tu elección: " + choiceText + "\n");
        gameStatusLabel.setText("Esperando al oponente...");
        
        if (!player2Choice.isEmpty()) {
            resolveBattle();
        }
    }

    private void simulateOpponentChoice() {
        Timer timer = new Timer(2000, (ActionEvent e) -> {
            player2Choice = Math.random() > 0.5 ? "attack" : "defend";
            String choiceText = player2Choice.equals("attack") ? "ATACAR" : "DEFENDER";
            logTextArea.append("Oponente elige: " + choiceText + "\n");
            
            if (!player1Choice.isEmpty()) {
                resolveBattle();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void resolveBattle() {
        gameStatusLabel.setText("¡Resolviendo batalla!");
        logTextArea.append("\n--- RESOLUCIÓN DE BATALLA ---\n");
        
        // Usar solo la primera carta para la batalla
        int playerPower = player1Choice.equals("attack") ?
                player1Cards[0].attack : player1Cards[0].defense;
        int opponentPower = player2Choice.equals("attack") ?
                player2Cards[0].attack : player2Cards[0].defense;

        logTextArea.append("Tu poder: " + playerPower + " (" + 
                          (player1Choice.equals("attack") ? "ATK" : "DEF") + ")\n");
        logTextArea.append("Oponente: " + opponentPower + " (" + 
                          (player2Choice.equals("attack") ? "ATK" : "DEF") + ")\n");

        if (player1Choice.equals("attack") && player2Choice.equals("attack")) {
            if (playerPower > opponentPower) {
                int damage = playerPower - opponentPower;
                player2Life -= damage;
                logTextArea.append("¡Ganas! Oponente pierde " + damage + " LP\n");
                gameStatusLabel.setText("¡Victoria en esta ronda!");
            } else if (opponentPower > playerPower) {
                int damage = opponentPower - playerPower;
                player1Life -= damage;
                logTextArea.append("¡Pierdes! Daño recibido: " + damage + " LP\n");
                gameStatusLabel.setText("¡Derrota en esta ronda!");
            } else {
                logTextArea.append("¡Empate!\n");
                gameStatusLabel.setText("¡Empate!");
            }
        } else if (player1Choice.equals("attack")) {
            if (playerPower > opponentPower) {
                int damage = playerPower - opponentPower;
                player2Life -= damage;
                logTextArea.append("¡Atacas con éxito! Daño: " + damage + " LP\n");
                gameStatusLabel.setText("¡Ataque exitoso!");
            } else {
                logTextArea.append("¡Defensa resistida!\n");
                gameStatusLabel.setText("¡Defensa exitosa del oponente!");
            }
        } else if (player2Choice.equals("attack")) {
            if (opponentPower > playerPower) {
                int damage = opponentPower - playerPower;
                player1Life -= damage;
                logTextArea.append("¡Defensa fallida! Daño: " + damage + " LP\n");
                gameStatusLabel.setText("¡Tu defensa falló!");
            } else {
                logTextArea.append("¡Defiendes con éxito!\n");
                gameStatusLabel.setText("¡Defensa exitosa!");
            }
        } else {
            logTextArea.append("¡Ambos defienden!\n");
            gameStatusLabel.setText("¡Ambos jugadores defienden!");
        }
        
        updateLifeDisplay();

        if (player1Life <= 0 || player2Life <= 0) {
            String message = player1Life <= 0 ? "¡HAS PERDIDO!" : "¡HAS GANADO!";
            logTextArea.append("\n" + message + "\n");
            gameStatusLabel.setText(message);
            
            int option = JOptionPane.showConfirmDialog(this,
                    message + "\n¿Quieres jugar otra vez?",
                    "Fin del juego",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        } else {
            Timer nextRoundTimer = new Timer(4000, e -> startNewRound());
            nextRoundTimer.setRepeats(false);
            nextRoundTimer.start();
        }
    }

    private void resetGame() {
        player1Life = 8000;
        player2Life = 8000;
        logTextArea.setText("¡Nuevo juego iniciado!\n");
        gameStatusLabel.setText("¡Preparándose para la batalla!");
        updateLifeDisplay();
        startNewRound();
    }

    static class YuGiOhCard {
        final String name;
        final int attack;
        final int defense;
        final String imageUrl;

        YuGiOhCard(String name, int attack, int defense, String imageUrl) {
            this.name = name;
            this.attack = attack;
            this.defense = defense;
            this.imageUrl = imageUrl;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("game");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, -1, -1));

        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, -1, -1));

        jLabel4.setText("jLabel4");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, -1, -1));

        jLabel5.setText("jLabel5");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, -1, -1));

        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 360, -1, -1));

        jLabel7.setText("jLabel7");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, -1, -1));

        jButton1.setText("Def");
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, -1, -1));

        jButton2.setText("Atk");
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, -1, -1));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
         /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameYuGiOh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GameYuGiOh().setVisible(true));
        //</editor-fold>
 
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
