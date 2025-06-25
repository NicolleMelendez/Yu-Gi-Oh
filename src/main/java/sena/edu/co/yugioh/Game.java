package sena.edu.co.yugioh;

import java.awt.Image;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Aprendiz
 */
public class Game extends javax.swing.JFrame {

    List<Integer> listCard = new ArrayList<>();
    
    List<CardGame> cardListJ1 = new ArrayList<>();
    List<CardGame> cardListM1 = new ArrayList<>();
    
    
    public void DuelYuGiApi(){
        inicialitationCard();
    }
    
    public void inicialitationCard(){
        listCard.add(46986414);
        listCard.add(40640057);
        listCard.add(89631139);
    }
    
    public int shuffle(){
        Random random = new Random();
        int i = random.nextInt(listCard.size());
        return listCard.get(i);
    }
    
    /**
     * Creates new form Game
     */
    public Game() {
        initComponents();
        // Inicializar el juego cuando se crea la ventana
        DuelYuGiApi();
        fill();
        showCardsInGUI();
    }

    
    public CardGame getCard(){
        String idCard = String.valueOf(shuffle());
        String url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?id=";
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + idCard)).build();
        

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject obj = new JSONObject(response.body());
                JSONArray data = obj.getJSONArray("data");
                JSONObject card = data.getJSONObject(0);
                
                int id = card.getInt("id");
                String name = card.getString("name");
                
                int atk = 0;
                int def = 0;
                
                if (card.has("atk")) {
                    atk = card.getInt("atk");
                }
                if (card.has("def")) {
                    def = card.getInt("def");
                }
                
                String imagen = "";
                JSONArray cardImages = card.getJSONArray("card_images");
                if(cardImages.length() > 0){
                    String urlImagen = cardImages.getJSONObject(0).getString("image_url_small");
                    imagen = urlImagen;
                }
                
                CardGame cardGame = new CardGame(id, name, atk, def, imagen);
                return cardGame;
                
            }
            
            
        } catch (Exception e) {
            // Error silencioso - la GUI manejará cartas nulas
        }
        return null;
    }
    
    public void fill()
    {
        for (int i = 0; i < 3; i++) {
            CardGame card1 = getCard();
            CardGame card2 = getCard();
            if(card1 != null) cardListJ1.add(card1);
            if(card2 != null) cardListM1.add(card2);
        }
    }
    
    // Método eliminado - ya no usamos la consola
    // Todo se muestra en la GUI
    
    // Nuevo método para mostrar las cartas en la GUI
    public void showCardsInGUI() {
        // Mostrar cartas del jugador 1 (parte superior)
        if (cardListJ1.size() > 0) {
            updateLabel(jLabel1, cardListJ1.get(0));
        }
        if (cardListJ1.size() > 1) {
            updateLabel(jLabel2, cardListJ1.get(1));
        }
        if (cardListJ1.size() > 2) {
            updateLabel(jLabel3, cardListJ1.get(2));
        }
        
        // Mostrar cartas del oponente (parte inferior)
        if (cardListM1.size() > 0) {
            updateLabel(jLabel4, cardListM1.get(0));
        }
        if (cardListM1.size() > 1) {
            updateLabel(jLabel5, cardListM1.get(1));
        }
        if (cardListM1.size() > 2) {
            updateLabel(jLabel6, cardListM1.get(2));
        }
    }
    
    // Método para actualizar un JLabel con información de la carta
    private void updateLabel(javax.swing.JLabel label, CardGame card) {
        if (card == null) {
            label.setText("Sin carta");
            return;
        }
        
        // Establecer el texto con información de la carta
        String cardInfo = "<html><center><b>" + card.getName() + "</b><br>" + 
                         "ATK: " + card.getAtk() + " | DEF: " + card.getDef() + "</center></html>";
        label.setText(cardInfo);
        
        // Intentar cargar la imagen
        try {
            if (card.getImg() != null && !card.getImg().isEmpty()) {
                URL imageUrl = new URL(card.getImg());
                ImageIcon imageIcon = new ImageIcon(imageUrl);
                // Redimensionar la imagen para que quepa en el label
                Image image = imageIcon.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(image);
                label.setIcon(imageIcon);
                // Mantener el texto debajo de la imagen
                label.setText("<html><center>" + card.getName() + "</center></html>");
                label.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            }
        } catch (Exception e) {
            // Error silencioso al cargar imagen - se muestra solo texto
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel1)
                .addGap(188, 188, 188)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(175, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 83, 718, 280));

        jLabel4.setText("jLabel4");

        jLabel5.setText("jLabel5");

        jLabel6.setText("jLabel6");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel4)
                .addGap(211, 211, 211)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(135, 135, 135))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(202, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 730, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
