package front_end;

import back_end.BibliotecaGenericaArreglo;
import back_end.modelo.Articulo;
import back_end.modelo.Libro;
import back_end.modelo.Revista;
import back_end.modelo.Trabajo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrmBiblioteca extends JFrame {

    private static final int CAPACIDAD = 100;

    private final BibliotecaGenericaArreglo<Trabajo> biblioteca =
            new BibliotecaGenericaArreglo<>(CAPACIDAD);

    private JComboBox<String> cmbTipo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAnio;
    private JTextField txtPosicion;
    private JTextField txtPaginas;
    private JTextField txtEdicion;
    private JTextField txtConferencia;

    private JButton btnAgregar;
    private JButton btnConsultar;
    private JButton btnMostrarTodos;
    private JButton btnEliminar;
    private JButton btnVaciar;

    private JTextArea txtSalida;
    private JLabel lblCapacidad;

    public FrmBiblioteca() {
        super("Sistema de Gestión de Biblioteca Genérica");
        initComponents();
        actualizarCamposTipo();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 10));

        add(crearPanelFormulario(), BorderLayout.NORTH);
        add(crearPanelSalida(), BorderLayout.CENTER);
        add(crearPanelAcciones(), BorderLayout.SOUTH);

        cmbTipo.addActionListener(e -> actualizarCamposTipo());
        btnAgregar.addActionListener(e -> agregarTrabajo());
        btnMostrarTodos.addActionListener(e -> refrescarListado());

        setSize(880, 660);
        setMinimumSize(new Dimension(780, 580));
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panel.add(crearPanelDatosGenerales());
        panel.add(crearPanelDatosEspecificos());
        return panel;
    }

    private JPanel crearPanelDatosGenerales() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos generales"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);

        cmbTipo = new JComboBox<>(new String[]{"Libro", "Revista", "Articulo"});
        txtTitulo = new JTextField(20);
        txtAutor = new JTextField(20);
        txtAnio = new JTextField(20);
        txtPosicion = new JTextField(20);

        agregarFila(panel, gbc, 0, "Tipo de publicación:", cmbTipo);
        agregarFila(panel, gbc, 1, "Título:", txtTitulo);
        agregarFila(panel, gbc, 2, "Autor:", txtAutor);
        agregarFila(panel, gbc, 3, "Año de publicación:", txtAnio);
        agregarFila(panel, gbc, 4, "Posición:", txtPosicion);

        return panel;
    }

    private JPanel crearPanelDatosEspecificos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos específicos"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);

        txtPaginas = new JTextField(20);
        txtEdicion = new JTextField(20);
        txtConferencia = new JTextField(20);

        agregarFila(panel, gbc, 0, "Número de páginas:", txtPaginas);
        agregarFila(panel, gbc, 1, "Número de edición:", txtEdicion);
        agregarFila(panel, gbc, 2, "Nombre de conferencia:", txtConferencia);

        JLabel lblAyuda = new JLabel("Solo se habilita el campo del tipo seleccionado.");
        lblAyuda.setFont(lblAyuda.getFont().deriveFont(Font.PLAIN, 11f));
        lblAyuda.setForeground(Color.GRAY);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblAyuda, gbc);

        return panel;
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila,
            String etiqueta, JComponent campo) {
        gbc.gridy = fila;

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campo, gbc);
    }

    private JScrollPane crearPanelSalida() {
        txtSalida = new JTextArea(14, 60);
        txtSalida.setEditable(false);
        txtSalida.setLineWrap(true);
        txtSalida.setWrapStyleWord(true);
        txtSalida.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        txtSalida.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        txtSalida.setText("Bienvenido. Seleccione un tipo de publicación y complete los datos.");

        JScrollPane scroll = new JScrollPane(txtSalida);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 10),
                BorderFactory.createTitledBorder("Publicaciones")));
        return scroll;
    }

    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAgregar = new JButton("Agregar trabajo");
        btnConsultar = new JButton("Consultar por posición");
        btnMostrarTodos = new JButton("Mostrar todos");
        btnEliminar = new JButton("Eliminar por posición");
        btnVaciar = new JButton("Vaciar biblioteca");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnMostrarTodos);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVaciar);

        lblCapacidad = new JLabel("Capacidad del arreglo: " + CAPACIDAD);
        lblCapacidad.setHorizontalAlignment(SwingConstants.CENTER);
        lblCapacidad.setFont(lblCapacidad.getFont().deriveFont(Font.PLAIN, 11f));
        lblCapacidad.setForeground(Color.GRAY);

        panel.add(panelBotones, BorderLayout.CENTER);
        panel.add(lblCapacidad, BorderLayout.SOUTH);
        return panel;
    }

    private void actualizarCamposTipo() {
        String tipo = (String) cmbTipo.getSelectedItem();
        boolean esLibro = "Libro".equals(tipo);
        boolean esRevista = "Revista".equals(tipo);
        boolean esArticulo = "Articulo".equals(tipo);

        txtPaginas.setEnabled(esLibro);
        txtEdicion.setEnabled(esRevista);
        txtConferencia.setEnabled(esArticulo);

        if (!esLibro) {
            txtPaginas.setText("");
        }
        if (!esRevista) {
            txtEdicion.setText("");
        }
        if (!esArticulo) {
            txtConferencia.setText("");
        }
    }

    private void agregarTrabajo() {
        try {
            Trabajo trabajo = construirTrabajo();
            int posicion = leerEntero(txtPosicion, "posición");

            biblioteca.agregarTrabajo(trabajo, posicion);
            refrescarListado();
            limpiarCamposCaptura();
            mostrarInfo("Trabajo agregado correctamente en la posición " + posicion + ".");
        } catch (IllegalArgumentException | IllegalStateException | IndexOutOfBoundsException e) {
            mostrarError(e.getMessage());
        }
    }

    private Trabajo construirTrabajo() {
        String titulo = leerTexto(txtTitulo, "título");
        String autor = leerTexto(txtAutor, "autor");
        int anio = leerEntero(txtAnio, "año de publicación");
        String tipo = (String) cmbTipo.getSelectedItem();

        if ("Libro".equals(tipo)) {
            return new Libro(titulo, autor, anio, leerEntero(txtPaginas, "número de páginas"));
        }
        if ("Revista".equals(tipo)) {
            return new Revista(titulo, autor, anio, leerEntero(txtEdicion, "número de edición"));
        }
        return new Articulo(titulo, autor, anio, leerTexto(txtConferencia, "nombre de conferencia"));
    }

    private String leerTexto(JTextField campo, String nombre) {
        String valor = campo.getText().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombre + " no puede estar vacío.");
        }
        return valor;
    }

    private int leerEntero(JTextField campo, String nombre) {
        String valor = campo.getText().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombre + " no puede estar vacío.");
        }
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + nombre + " debe ser un número entero válido.");
        }
    }

    private void refrescarListado() {
        StringBuilder sb = new StringBuilder();
        sb.append("Capacidad: ").append(biblioteca.getCapacidad())
                .append(" | Ocupadas: ").append(biblioteca.getCantidad())
                .append("\n\n");

        int encontrados = 0;
        for (int i = 0; i < biblioteca.getCapacidad(); i++) {
            Trabajo trabajo = biblioteca.mostrarTrabajo(i);
            if (trabajo != null) {
                sb.append("[").append(i).append("] ").append(trabajo).append("\n");
                encontrados++;
            }
        }

        if (encontrados == 0) {
            sb.append("La biblioteca está vacía.");
        }

        txtSalida.setText(sb.toString());
        txtSalida.setCaretPosition(0);
        lblCapacidad.setText("Capacidad del arreglo: " + biblioteca.getCapacidad()
                + " | Ocupadas: " + biblioteca.getCantidad());
    }

    private void limpiarCamposCaptura() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAnio.setText("");
        txtPosicion.setText("");
        txtPaginas.setText("");
        txtEdicion.setText("");
        txtConferencia.setText("");
        txtTitulo.requestFocusInWindow();
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
