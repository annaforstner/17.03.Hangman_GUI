import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Hangman_Teil1 extends JFrame {
    private JTextArea wordle;
    private JTextField buchstabe;
    private JButton btnHinzu;
    private JLabel history;
    private JLabel killCount;
    private JPanel death;
    private JLabel bilder;

    private int fehler = 0;
    private int maxFehler = 9;
    private int picCount = 1;

    public Hangman_Teil1() {
        setTitle("Hangman");
        setContentPane(death);


        // zusätzliche Dinger
        Random randomNumber = new Random();
        ArrayList<String> bstbnListe = new ArrayList<>();



        String[] worter = {"deklaration", "spiegel", "tastatur", "rechtschreibfehler", "dachdecker", "schnittstelle",
                "spinnennetz", "fehlerbehebung"}; // Wörter müssen kleingeschrieben werden
        int wort = randomNumber.nextInt(worter.length);

        String s = worter[wort];
        String ding = "_".repeat(s.length()); // this I found out through chatGPT
        StringBuilder aktuellerStand = new StringBuilder(ding);
        wordle.setText(aktuellerStand.toString());

        // der Versuch Bilder einzufügen
        ImageIcon neuesBild = new ImageIcon("hangman/hangman"+picCount+".png");
        Image image = neuesBild.getImage();
        Image neuesImage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        neuesBild = new ImageIcon(neuesImage);
        bilder.setIcon(neuesBild);



        btnHinzu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String letter = buchstabe.getText().trim().toLowerCase();
                bstbnListe.add(letter);

                if(letter.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Das Feld ist leer. Bitte einen Buchstaben eingeben.", "Fehler!", JOptionPane.ERROR_MESSAGE);
                }

                if (letter.length() != 1) {
                    JOptionPane.showMessageDialog(null, "Bitte nur einen Buchstaben eingeben!", "Fehler!", JOptionPane.ERROR_MESSAGE);
                    buchstabe.setText("");
                }

                char bstb1 = letter.charAt(0);

                String alterStand = aktuellerStand.toString(); // Vorheriger Stand des Wortes
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == bstb1) {
                        aktuellerStand.setCharAt(i, s.charAt(i));
                    }
                }

                if (alterStand.equals(aktuellerStand.toString())) { // Wenn sich das Wort nicht geändert hat, war der Buchstabe falsch
                    fehler++;
                    picCount++;
                    ImageIcon neuesBild = new ImageIcon("hangman/hangman"+picCount+".png");
                    Image image = neuesBild.getImage();
                    Image neuesImage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                    neuesBild = new ImageIcon(neuesImage);
                    bilder.setIcon(neuesBild);
                    JOptionPane.showMessageDialog(null, "Buchstabe nicht gefunden!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }

                wordle.setText(aktuellerStand.toString());
                killCount.setText("Fehler: " + fehler);

                if (fehler >= maxFehler) {
                    JOptionPane.showMessageDialog(null, "Game Over! Das Wort war: " + s, "Verloren!", JOptionPane.ERROR_MESSAGE);
                    btnHinzu.setEnabled(false);
                } else if (!aktuellerStand.toString().contains("_")) {
                    JOptionPane.showMessageDialog(null, "Herzlichen Glückwunsch! Du hast das Wort erraten: " + s, "Gewonnen!", JOptionPane.INFORMATION_MESSAGE);
                    btnHinzu.setEnabled(false);
                }

                // Aktualisiere das Wort im UI
                buchstabe.setText("");
                history.setText(bstbnListe.toString());
            }
        });

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Hangman_Teil1();
    }
}
