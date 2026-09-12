package com.mycompany.t1operativos;

/**
 * Clase para representar el resultado del análisis de una instrucción ensamblador.
 *
 * Contiene el estado de validación y, según el resultado, la instrucción
 * procesada o el mensaje que describe el error encontrado.
 *
 * @author deislher sánchez funez
 */
public class ResultadoParser {
    private boolean valido;
    private String[] instruccion;
    private String mensajeError;

    /**
     * Constructor de la clase ResultadoParser
     *
     * @param valido {@code true} si la instrucción es válida; {@code false} en caso contrario.
     * @param instruccion instrucción procesada, o {@code null} si no es válida.
     * @param mensajeError descripción del error, o {@code null} si la instrucción es válida.
     */
    public ResultadoParser(boolean valido, String[] instruccion, String mensajeError) {
        this.valido = valido;
        this.instruccion = instruccion;
        this.mensajeError = mensajeError;
    }

    /** @return {@code true} si la instrucción procesada es válida. */
    public boolean esValido() {
        return valido;
    }
    
    /** @return la instrucción procesada, o {@code null} si ocurrió un error. */
    public String[] getInstruccion() {
        return instruccion;
    }
    
    /** @return el mensaje de error, o {@code null} si la instrucción es válida. */
    public String getMensajeError() {
        return mensajeError;
    }
}
