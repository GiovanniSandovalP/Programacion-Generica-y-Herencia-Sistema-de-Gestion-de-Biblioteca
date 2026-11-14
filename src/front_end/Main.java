package front_end;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Si el look and feel del sistema no está disponible, se usa el de Java.
        }

        SwingUtilities.invokeLater(() -> new FrmBiblioteca().setVisible(true));
    }
}