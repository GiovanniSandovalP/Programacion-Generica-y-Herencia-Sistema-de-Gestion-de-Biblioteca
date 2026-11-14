package front_end;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si no está disponible, se usa el look and feel por defecto de Java.
        }

        SwingUtilities.invokeLater(() -> new FrmBiblioteca().setVisible(true));
    }
}
