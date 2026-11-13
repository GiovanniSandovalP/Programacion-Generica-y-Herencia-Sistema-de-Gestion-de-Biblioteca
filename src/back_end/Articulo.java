package back_end;

import java.util.Objects;

public class Articulo extends Trabajo {

    private String nombreConferencia;

    public Articulo(String titulo, String autor, int anioPublicacion, String nombreConferencia) {
        super(titulo, autor, anioPublicacion);
        setNombreConferencia(nombreConferencia);
    }

    public String getNombreConferencia() {
        return nombreConferencia;
    }

    public void setNombreConferencia(String nombreConferencia) {
        if (nombreConferencia == null || nombreConferencia.isBlank()) {
            throw new IllegalArgumentException("El nombre de la conferencia no puede estar vacío.");
        }
        this.nombreConferencia = nombreConferencia.trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Articulo otro = (Articulo) obj;
        return Objects.equals(nombreConferencia, otro.nombreConferencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nombreConferencia);
    }

    @Override
    public String toString() {
        return super.toString() + " | Conferencia: " + nombreConferencia;
    }
}
