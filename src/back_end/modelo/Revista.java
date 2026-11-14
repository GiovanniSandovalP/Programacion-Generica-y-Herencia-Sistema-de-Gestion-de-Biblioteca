package back_end.modelo;

import java.util.Objects;

public class Revista extends Trabajo {

    private int numeroEdicion;

    public Revista(String titulo, String autor, int anioPublicacion, int numeroEdicion) {
        super(titulo, autor, anioPublicacion);
        setNumeroEdicion(numeroEdicion);
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(int numeroEdicion) {
        if (numeroEdicion <= 0) {
            throw new IllegalArgumentException("El numero de edición debe ser mayor a cero");
        }
        this.numeroEdicion = numeroEdicion;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Revista otra = (Revista) obj;
        return numeroEdicion == otra.numeroEdicion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeroEdicion);
    }

    @Override
    public String toString() {
        return super.toString() + " | Edicion: " + numeroEdicion;
    }
}
