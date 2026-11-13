package back_end;

import java.util.Objects;

public abstract class Trabajo {

    private String titulo;
    private String autor;
    private int anioPublicacion;

    public Trabajo(String titulo, String autor, int anioPublicacion) {
        setTitulo(titulo);
        setAutor(autor);
        setAnioPublicacion(anioPublicacion);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.titulo = titulo.trim();
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }
        this.autor = autor.trim();
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        if (anioPublicacion <= 0) {
            throw new IllegalArgumentException("El año de publicación debe ser mayor a cero.");
        }
        this.anioPublicacion = anioPublicacion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trabajo otro = (Trabajo) obj;
        return anioPublicacion == otro.anioPublicacion
                && Objects.equals(titulo, otro.titulo)
                && Objects.equals(autor, otro.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, autor, anioPublicacion);
    }

    @Override
    public String toString() {
        return String.format("%s | Título: %s | Autor: %s | Año: %d",
                getClass().getSimpleName(), titulo, autor, anioPublicacion);
    }
}
