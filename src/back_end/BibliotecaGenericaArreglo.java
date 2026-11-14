package back_end;

import back_end.modelo.Trabajo;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaGenericaArreglo<T extends Trabajo> {

    private final T[] elementos;
    private int cantidad;

    @SuppressWarnings("unchecked")
    public BibliotecaGenericaArreglo(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a cero");
        }
        this.elementos = (T[]) new Trabajo[capacidad];
        this.cantidad = 0;
    }

    public void agregarTrabajo(T trabajo, int posicion) {
        validarPosicion(posicion);
        if (trabajo == null) {
            throw new IllegalArgumentException("El trabajo no puede ser nulo.");
        }
        if (elementos[posicion] != null) {
            throw new IllegalStateException("La posición " + posicion + " ya está ocupada");
        }
        elementos[posicion] = trabajo;
        cantidad++;
    }

    public T eliminarTrabajo(int posicion) {
        validarPosicion(posicion);
        T eliminado = elementos[posicion];
        if (eliminado == null) {
            throw new IllegalStateException("La posición " + posicion + " está vacía");
        }
        elementos[posicion] = null;
        cantidad--;
        return eliminado;
    }

    public T mostrarTrabajo(int posicion) {
        validarPosicion(posicion);
        return elementos[posicion];
    }

    public List<Trabajo> mostrarTodosTrabajos() {
        List<Trabajo> lista = new ArrayList<>();
        for (T elemento : elementos) {
            if (elemento != null) {
                lista.add(elemento);
            }
        }
        return lista;
    }

    public void vaciarBiblioteca() {
        for (int i = 0; i < elementos.length; i++) {
            elementos[i] = null;
        }
        cantidad = 0;
    }

    public boolean isEmpty() {
        return cantidad == 0;
    }

    public int getCapacidad() {
        return elementos.length;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaOcupada(int posicion) {
        validarPosicion(posicion);
        return elementos[posicion] != null;
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= elementos.length) {
            throw new IndexOutOfBoundsException(
                    "Posición fuera de rango. Debe estar entre 0 y " + (elementos.length - 1));
        }
    }
}
