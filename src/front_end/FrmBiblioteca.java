package front_end;

import back_end.BibliotecaGenericaArreglo;
import back_end.modelo.Articulo;
import back_end.modelo.Libro;
import back_end.modelo.Revista;
import back_end.modelo.Trabajo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class FrmBiblioteca extends JFrame {

    private static final int CAPACIDAD = 100;

    private final BibliotecaGenericaArreglo<Trabajo> biblioteca =
            new BibliotecaGenericaArreglo<>(CAPACIDAD);

    private final DefaultListModel<Trabajo> modeloLista = new DefaultListModel<>();
    private final List<Integer> posiciones = new ArrayList<>();

    private JList<Trabajo> listaTrabajos;
    private JTextArea txtDetalle;
    private JLabel lblNavegacion;
    private JLabel lblCapacidad;
    private JButton btnAnterior;
    private JButton btnSiguiente;

    public FrmBiblioteca() {
        super("Sistema de Gestión de Biblioteca Genérica");
        initComponents();
        reconstruirLista();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 680);
        setMinimumSize(new Dimension(860, 600));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new BorderLayout(12, 12));
        add(crearPanelLista(), BorderLayout.WEST);
        add(crearPanelCentro(), BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelLista() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 0));

        JLabel titulo = new JLabel("Publicaciones");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));

        listaTrabajos = new JList<>(modeloLista);
        listaTrabajos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTrabajos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                Trabajo trabajo = (Trabajo) value;
                int posicion = index >= 0 && index < posiciones.size()
                        ? posiciones.get(index) : index;

                setText("[" + posicion + "] " + trabajo.getClass().getSimpleName()
                        + " - " + trabajo.getTitulo());
                return this;
            }
        });
        listaTrabajos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarSeleccion();
            }
        });

        JScrollPane scroll = new JScrollPane(listaTrabajos);
        scroll.setPreferredSize(new Dimension(300, 480));
        scroll.setBorder(BorderFactory.createTitledBorder("Elementos guardados"));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelCentro() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 12));

        JButton btnAgregar = new JButton("+");
        btnAgregar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 96));
        btnAgregar.setPreferredSize(new Dimension(260, 200));
        btnAgregar.setBackground(new Color(248, 248, 248));
        btnAgregar.setForeground(new Color(90, 90, 90));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorder(BorderFactory.createLineBorder(
                new Color(160, 160, 160), 2, true));
        btnAgregar.setToolTipText("Agregar publicación");
        btnAgregar.addActionListener(e -> agregarTrabajo());

        JPanel panelAgregar = new JPanel(new GridBagLayout());
        panelAgregar.add(btnAgregar);
        panel.add(panelAgregar, BorderLayout.CENTER);

        txtDetalle = new JTextArea(10, 45);
        txtDetalle.setEditable(false);
        txtDetalle.setLineWrap(true);
        txtDetalle.setWrapStyleWord(true);
        txtDetalle.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        txtDetalle.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        JScrollPane scrollDetalle = new JScrollPane(txtDetalle);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder("Detalle / estado"));
        scrollDetalle.setPreferredSize(new Dimension(500, 220));
        panel.add(scrollDetalle, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));

        JPanel navegacion = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        btnAnterior = new JButton("←");
        btnSiguiente = new JButton("→");
        lblNavegacion = new JLabel("0 / 0");
        lblNavegacion.setFont(lblNavegacion.getFont().deriveFont(Font.BOLD, 13f));

        btnAnterior.addActionListener(e -> irAnterior());
        btnSiguiente.addActionListener(e -> irSiguiente());

        navegacion.add(btnAnterior);
        navegacion.add(lblNavegacion);
        navegacion.add(btnSiguiente);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnConsultar = new JButton("Consultar por posición");
        JButton btnMostrarTodos = new JButton("Mostrar todos");
        JButton btnEliminar = new JButton("Eliminar por posición");
        JButton btnVaciar = new JButton("Vaciar biblioteca");

        btnConsultar.addActionListener(e -> consultarPorPosicion());
        btnMostrarTodos.addActionListener(e -> mostrarTodos());
        btnEliminar.addActionListener(e -> eliminarPorPosicion());
        btnVaciar.addActionListener(e -> vaciarBiblioteca());

        acciones.add(btnConsultar);
        acciones.add(btnMostrarTodos);
        acciones.add(btnEliminar);
        acciones.add(btnVaciar);

        lblCapacidad = new JLabel("Capacidad: " + CAPACIDAD + " | Ocupadas: 0");
        lblCapacidad.setHorizontalAlignment(SwingConstants.CENTER);
        lblCapacidad.setFont(lblCapacidad.getFont().deriveFont(Font.PLAIN, 11f));
        lblCapacidad.setForeground(Color.GRAY);

        panel.add(navegacion);
        panel.add(acciones);
        panel.add(lblCapacidad);
        return panel;
    }

    private void agregarTrabajo() {
        if (biblioteca.getCantidad() >= biblioteca.getCapacidad()) {
            mostrarError("La biblioteca está llena.");
            return;
        }

        JComboBox<String> cmbTipo =
                new JComboBox<>(new String[]{"Libro", "Revista", "Articulo"});
        JTextField fTitulo = new JTextField(16);
        JTextField fAutor = new JTextField(16);
        JTextField fAnio = new JTextField(16);
        JTextField fPosicion = new JTextField(16);
        JTextField fPaginas = new JTextField(16);
        JTextField fEdicion = new JTextField(16);
        JTextField fConferencia = new JTextField(16);

        fPosicion.setText(String.valueOf(primerLibre()));

        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);

        agregarFila(formulario, gbc, 0, "Tipo de publicación:", cmbTipo);
        agregarFila(formulario, gbc, 1, "Título:", fTitulo);
        agregarFila(formulario, gbc, 2, "Autor:", fAutor);
        agregarFila(formulario, gbc, 3, "Año de publicación:", fAnio);
        agregarFila(formulario, gbc, 4, "Posición:", fPosicion);
        agregarFila(formulario, gbc, 5, "Número de páginas:", fPaginas);
        agregarFila(formulario, gbc, 6, "Número de edición:", fEdicion);
        agregarFila(formulario, gbc, 7, "Nombre de conferencia:", fConferencia);

        Runnable actualizarCampos = () -> {
            String tipo = (String) cmbTipo.getSelectedItem();
            boolean esLibro = "Libro".equals(tipo);
            boolean esRevista = "Revista".equals(tipo);
            boolean esArticulo = "Articulo".equals(tipo);

            fPaginas.setEnabled(esLibro);
            fEdicion.setEnabled(esRevista);
            fConferencia.setEnabled(esArticulo);

            if (!esLibro) {
                fPaginas.setText("");
            }
            if (!esRevista) {
                fEdicion.setText("");
            }
            if (!esArticulo) {
                fConferencia.setText("");
            }
        };
        cmbTipo.addActionListener(e -> actualizarCampos.run());
        actualizarCampos.run();

        while (true) {
            int opcion = JOptionPane.showConfirmDialog(this, formulario,
                    "Agregar publicación", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (opcion != JOptionPane.OK_OPTION) {
                return;
            }

            try {
                String tipo = (String) cmbTipo.getSelectedItem();
                String titulo = leerTexto(fTitulo, "título");
                String autor = leerTexto(fAutor, "autor");
                int anio = leerEnteroPositivo(fAnio, "año de publicación");
                int posicion = leerPosicion(fPosicion);

                if (biblioteca.estaOcupada(posicion)) {
                    throw new IllegalStateException("La posición " + posicion
                            + " ya está ocupada. Elimine el trabajo antes de agregar otro.");
                }

                Trabajo nuevo = switch (tipo) {
                    case "Libro" -> new Libro(titulo, autor, anio,
                            leerEnteroPositivo(fPaginas, "número de páginas"));
                    case "Revista" -> new Revista(titulo, autor, anio,
                            leerEnteroPositivo(fEdicion, "número de edición"));
                    default -> new Articulo(titulo, autor, anio,
                            leerTexto(fConferencia, "nombre de conferencia"));
                };

                biblioteca.agregarTrabajo(nuevo, posicion);

                reconstruirLista();
                seleccionarPorPosicion(posicion);
                mostrarInfo("Publicación agregada en la posición " + posicion + ".");
                return;
            } catch (IllegalArgumentException | IllegalStateException
                    | IndexOutOfBoundsException e) {
                mostrarError(e.getMessage());
            }
        }
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

    private void reconstruirLista() {
        modeloLista.clear();
        posiciones.clear();

        for (int i = 0; i < biblioteca.getCapacidad(); i++) {
            Trabajo trabajo = biblioteca.mostrarTrabajo(i);
            if (trabajo != null) {
                posiciones.add(i);
                modeloLista.addElement(trabajo);
            }
        }

        actualizarEstadoNavegacion();
    }

    private void mostrarSeleccion() {
        int indice = listaTrabajos.getSelectedIndex();

        if (indice >= 0 && indice < posiciones.size()) {
            int posicion = posiciones.get(indice);
            txtDetalle.setText("Posición " + posicion + ":\n\n" + modeloLista.get(indice));
            txtDetalle.setCaretPosition(0);
        }

        actualizarEstadoNavegacion();
    }

    private void actualizarEstadoNavegacion() {
        int total = modeloLista.size();
        int indice = listaTrabajos.getSelectedIndex();

        lblNavegacion.setText((indice >= 0 ? indice + 1 : 0) + " / " + total);
        lblCapacidad.setText("Capacidad: " + biblioteca.getCapacidad()
                + " | Ocupadas: " + biblioteca.getCantidad());

        boolean hayElementos = total > 0;
        btnAnterior.setEnabled(hayElementos);
        btnSiguiente.setEnabled(hayElementos);
    }

    private void irAnterior() {
        if (modeloLista.isEmpty()) {
            return;
        }
        int indice = listaTrabajos.getSelectedIndex();
        indice = (indice <= 0) ? modeloLista.size() - 1 : indice - 1;
        seleccionarIndice(indice);
    }

    private void irSiguiente() {
        if (modeloLista.isEmpty()) {
            return;
        }
        int indice = listaTrabajos.getSelectedIndex();
        indice = (indice < 0 || indice >= modeloLista.size() - 1) ? 0 : indice + 1;
        seleccionarIndice(indice);
    }

    private void seleccionarIndice(int indice) {
        listaTrabajos.setSelectedIndex(indice);
        listaTrabajos.ensureIndexIsVisible(indice);
    }

    private void seleccionarPorPosicion(int posicion) {
        int indice = posiciones.indexOf(posicion);
        if (indice >= 0) {
            seleccionarIndice(indice);
        }
    }

    private void consultarPorPosicion() {
        Integer posicion = pedirPosicion("Posición a consultar:");
        if (posicion == null) {
            return;
        }

        Trabajo trabajo = biblioteca.mostrarTrabajo(posicion);
        if (trabajo == null) {
            mostrarInfo("La posición " + posicion + " está vacía.");
            return;
        }

        seleccionarPorPosicion(posicion);
        txtDetalle.setText("Posición " + posicion + ":\n\n" + trabajo);
        txtDetalle.setCaretPosition(0);
    }

    private void mostrarTodos() {
        List<Trabajo> todos = biblioteca.mostrarTodosTrabajos();

        StringBuilder sb = new StringBuilder();
        sb.append("Capacidad: ").append(biblioteca.getCapacidad())
                .append(" | Ocupadas: ").append(biblioteca.getCantidad())
                .append("\n\n");

        if (todos.isEmpty()) {
            sb.append("La biblioteca está vacía.");
        } else {
            reconstruirLista();
            for (int i = 0; i < todos.size(); i++) {
                int posicion = i < posiciones.size() ? posiciones.get(i) : i;
                sb.append("[").append(posicion).append("] ")
                        .append(todos.get(i)).append("\n");
            }
        }

        txtDetalle.setText(sb.toString());
        txtDetalle.setCaretPosition(0);
    }

    private void eliminarPorPosicion() {
        Integer posicion = pedirPosicion("Posición a eliminar:");
        if (posicion == null) {
            return;
        }

        if (biblioteca.mostrarTrabajo(posicion) == null) {
            mostrarInfo("La posición " + posicion + " está vacía.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el trabajo de la posición " + posicion + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Trabajo eliminado = biblioteca.eliminarTrabajo(posicion);
            reconstruirLista();

            int indice = 0;
            while (indice < posiciones.size() && posiciones.get(indice) < posicion) {
                indice++;
            }
            if (indice >= modeloLista.size()) {
                indice = modeloLista.size() - 1;
            }
            if (indice >= 0) {
                seleccionarIndice(indice);
            }

            txtDetalle.setText("Se eliminó:\n\n" + eliminado);
            mostrarInfo("Trabajo eliminado de la posición " + posicion + ".");
        } catch (IllegalArgumentException | IllegalStateException
                | IndexOutOfBoundsException e) {
            mostrarError(e.getMessage());
        }
    }

    private void vaciarBiblioteca() {
        if (biblioteca.isEmpty()) {
            mostrarInfo("La biblioteca ya está vacía.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar todas las publicaciones de la biblioteca?",
                "Confirmar vaciado", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        biblioteca.vaciarBiblioteca();
        reconstruirLista();
        txtDetalle.setText("");
        mostrarInfo("La biblioteca se vació correctamente.");
    }

    private Integer pedirPosicion(String mensaje) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(this,
                    mensaje + "\nDebe estar entre 0 y " + (biblioteca.getCapacidad() - 1),
                    "Posición", JOptionPane.QUESTION_MESSAGE);

            if (entrada == null) {
                return null;
            }

            try {
                int posicion = Integer.parseInt(entrada.trim());
                if (posicion < 0 || posicion >= biblioteca.getCapacidad()) {
                    throw new NumberFormatException();
                }
                return posicion;
            } catch (NumberFormatException e) {
                mostrarError("Ingrese una posición válida entre 0 y "
                        + (biblioteca.getCapacidad() - 1) + ".");
            }
        }
    }

    private int primerLibre() {
        for (int i = 0; i < biblioteca.getCapacidad(); i++) {
            if (biblioteca.mostrarTrabajo(i) == null) {
                return i;
            }
        }
        return 0;
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
            throw new IllegalArgumentException("El campo " + nombre + " debe ser un entero válido.");
        }
    }

    private int leerEnteroPositivo(JTextField campo, String nombre) {
        int valor = leerEntero(campo, nombre);
        if (valor <= 0) {
            throw new IllegalArgumentException("El campo " + nombre + " debe ser mayor a cero.");
        }
        return valor;
    }

    private int leerPosicion(JTextField campo) {
        int posicion = leerEntero(campo, "posición");
        if (posicion < 0 || posicion >= biblioteca.getCapacidad()) {
            throw new IndexOutOfBoundsException("Posición fuera de rango. Debe estar entre 0 y "
                    + (biblioteca.getCapacidad() - 1));
        }
        return posicion;
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