package com.mycompany.t1operativos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Carga archivos ensamblador con extensión {@code .asm} y convierte cada línea
 * en una instrucción validada mediante el {@link Parser}
 *
 * @author deislher sánchez funez
 */
public class CargadorArchivos {
    private Parser parser;

    public CargadorArchivos() {
        this.parser = new Parser();
    }

    /**
     * Lee y procesa las instrucciones de un archivo ensamblador. Las líneas
     * vacías se omiten y el procesamiento se detiene si alguna instrucción no
     * cumple con el formato esperado.
     *
     * @param rutaArchivo ruta del archivo {@code .asm} que se desea cargar.
     * @return una lista con las instrucciones procesadas; cada arreglo contiene el operador, el registro y el valor.
     * @throws IOException si ocurre un error al abrir o leer el archivo.
     * @throws IllegalArgumentException si la ruta es inválida, el archivo no tiene extensión {@code .asm}, contiene una instrucción inválida o no contiene instrucciones.
     */
    public List<String[]> cargarArchivo(String rutaArchivo) throws IOException {
        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar un archivo.");
        }
        if (!rutaArchivo.toLowerCase().endsWith(".asm")) {
            throw new IllegalArgumentException("El archivo debe tener extensión .asm");
        }
        List<String[]> instrucciones = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numeroLinea = 0;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.trim().isEmpty()) {
                    continue;
                }
                ResultadoParser resultado = parser.procesarInstruccion(linea);
                if (!resultado.esValido()) {
                    throw new IllegalArgumentException("Error en la línea " + numeroLinea + ": " + resultado.getMensajeError());
                }
                instrucciones.add(resultado.getInstruccion());
            }
        }
        if (instrucciones.isEmpty()) {
            throw new IllegalArgumentException("El archivo no contiene instrucciones.");
        }
        return instrucciones;
    }
}
