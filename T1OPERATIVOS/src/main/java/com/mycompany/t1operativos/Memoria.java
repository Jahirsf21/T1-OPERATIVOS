package com.mycompany.t1operativos;

import java.util.List;

/**
 * Clase que representa la memoria principal de la Mini PC.
 *
 * Divide la memoria en un espacio reservado para el kernel y un espacio de
 * usuario donde se cargan y almacenan las instrucciones de los programas.
 *
 * @author deislher sánchez funez
 */
public class Memoria {
    private String[][] memoria;
    private int tamañoTotal;
    private int tamañoKernel;
    private int inicioUsuario;
    private int cantidadInstrucciones;

    /**
     * Construye una memoria con el tamaño total y el espacio reservado para el kernel indicados.
     *
     * @param tamañoTotal cantidad total de posiciones de memoria; debe ser al menos 128.
     * @param tamañoKernel cantidad de posiciones reservadas para el kernel; debe ser mayor que cero y menor que el tamaño total.
     * @throws IllegalArgumentException si el tamaño total o el tamaño reservado para el kernel no son válidos.
     */
    public Memoria(int tamañoTotal, int tamañoKernel) {
        if (tamañoTotal < 128) {
            throw new IllegalArgumentException("El tamaño mínimo es 128");
        }
        if (tamañoKernel <= 0 || tamañoKernel >= tamañoTotal) {
            throw new IllegalArgumentException("El tamaño reservado para el kernel no es válido.");
        }
        this.tamañoTotal = tamañoTotal;
        this.inicioUsuario = tamañoKernel;
        this.memoria = new String[tamañoTotal][];
        this.cantidadInstrucciones = 0;
    }

    /**
     * Limpia el espacio de usuario y carga en él las instrucciones de un programa de manera consecutiva.
     *
     * @param instrucciones instrucciones que se desean almacenar en memoria.
     * @throws IllegalArgumentException si el programa supera la capacidad de la memoria de usuario.
     */
    public void cargarPrograma(List<String[]> instrucciones) {
        int capacidadUsuario = tamañoTotal - inicioUsuario;
        if (instrucciones.size() > capacidadUsuario) {
            throw new IllegalArgumentException("El programa es demasiado grande para la memoria de usuario.");
        }
        limpiarUsuario();
        for (int i = 0; i < instrucciones.size(); i++) {
            int posicion = inicioUsuario + i;
            memoria[posicion] = instrucciones.get(i);
        }
        cantidadInstrucciones = instrucciones.size();
    }

    /**
     * Lee el contenido almacenado en una posición de memoria.
     *
     * @param posicion dirección de memoria que se desea consultar.
     * @return el contenido de la posición, o {@code null} si está vacía.
     * @throws IndexOutOfBoundsException si la posición está fuera de la memoria.
     */
    public String[] leer(int posicion) {
        validarDireccion(posicion);
        return memoria[posicion];
    }

    /**
     * Escribe un valor en una posición perteneciente al espacio de usuario.
     *
     * @param posicion dirección de memoria donde se desea escribir.
     * @param valor valor que se desea almacenar.
     * @throws IndexOutOfBoundsException si la posición está fuera de la memoria.
     * @throws IllegalArgumentException si la posición pertenece al espacio reservado para el kernel.
     */
    public void escribirUsuario(int posicion, String valor) {
        validarDireccion(posicion);
        if (posicion < inicioUsuario) {
            throw new IllegalArgumentException("No se puede escribir en el espacio reservado para el Kernel.");
        }
        memoria[posicion] = valor;
    }

    /**
     * Elimina el contenido del espacio de usuario y reinicia la cantidad de instrucciones cargadas.
     */
    public void limpiarUsuario() {
        for (int i = inicioUsuario; i < tamañoTotal; i++) {
            memoria[i] = null;
        }
        cantidadInstrucciones = 0;
    }

    /**
     * Comprueba que una posición se encuentre dentro de los límites de la
     * memoria.
     *
     * @param posicion dirección de memoria que se desea validar.
     * @throws IndexOutOfBoundsException si la posición está fuera de la memoria.
     */
    private void validarDireccion(int posicion) {
        if (posicion < 0 || posicion >= tamañoTotal) {
            throw new IndexOutOfBoundsException("Posición de memoria inválida: " + posicion);
        }
    }

    /**
     * Indica si una posición pertenece al espacio reservado para el kernel.
     *
     * @param posicion dirección de memoria que se desea comprobar.
     * @return {@code true} si la posición pertenece al kernel.
     * @throws IndexOutOfBoundsException si la posición está fuera de la memoria.
     */
    public boolean esDireccionKernel(int posicion) {
        validarDireccion(posicion);
        return posicion < inicioUsuario;
    }

    /** @return la cantidad total de posiciones de memoria. */
    public int getTamañoTotal() {
        return tamañoTotal;
    }

    /** @return la cantidad de posiciones reservadas para el kernel. */
    public int getTamañoKernel() {
        return tamañoKernel;
    }

    /** @return la primera posición disponible para la memoria de usuario. */
    public int getInicioUsuario() {
        return inicioUsuario;
    }

    /** @return la cantidad de instrucciones del programa cargado. */
    public int getCantidadInstrucciones() {
        return cantidadInstrucciones;
    }

    /**
     * Obtiene la posición de la última instrucción del programa cargado.
     *
     * @return la última posición ocupada por el programa, o {@code -1} si no
     *     hay instrucciones cargadas.
     */
    public int getFinPrograma() {
        if (cantidadInstrucciones == 0) {
            return -1;
        }
        return inicioUsuario + cantidadInstrucciones - 1;
    }

    /** @return la cantidad de posiciones disponibles para la memoria de usuario. */
    public int getCapacidadUsuario() {
        return tamañoTotal - inicioUsuario;
    }

}
