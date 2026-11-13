package back_end;

import java.util.Objects;

public class Libro extends Trabajo {

    private int numeroPaginas;

    public Libro(String titulo, String autor, int anioPublicacion, int numeroPaginas) {
        super(titulo, autor, anioPublicacion);
        setNumeroPaginas(numeroPaginas);
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        if (numeroPaginas <= 0) {
            throw new IllegalArgumentException("El numero de páginas debe ser mayor a cero");
        }
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Libro otro = (Libro) obj;
        return numeroPaginas == otro.numeroPaginas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeroPaginas);
    }

    @Override
    public String toString() {
        return super.toString() + " | Paginas: " + numeroPaginas;
    }
}
