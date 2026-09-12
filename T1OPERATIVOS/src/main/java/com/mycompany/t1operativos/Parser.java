package com.mycompany.t1operativos;

/**
 * Clase que valida, procesa y traduce las instrucciones ensamblador de la Mini PC.
 *
 * Reconoce los operadores LOAD, STORE, MOV, SUB y ADD, así como los registros
 * AX, BX, CX y DX.
 *
 * @author deislher sánchez funez
 */
public class Parser {

    /**
     * Convierte una instrucción simple al formato interno de tres elementos.
     * El valor de una instrucción simple se establece en cero.
     *
     * @param instruccion arreglo que contiene la operación y el registro.
     * @return un arreglo con el operador, el registro y el valor {@code "0"}.
     */
    public String[] procesarInstruccionSimple(String[] instruccion) {
        String[] resultado = new String[3];
        String[] partesOperacion = instruccion[0].replaceAll("\\s+", " ").split(" ");
        resultado[0] = partesOperacion[0];
        resultado[1] = partesOperacion[1];
        resultado[2] = "0";
        return resultado;
    }

    /**
     * Convierte una instrucción de asignación al formato interno de tres
     * elementos.
     *
     * @param instruccion arreglo que contiene la operación, el registro y el valor de la asignación.
     * @return un arreglo con el operador, el registro y el valor.
     */
    public String[] procesarInstruccionAsignacion(String[] instruccion) {
        String[] resultado = new String[3];
        String[] partesOperacion = instruccion[0].replaceAll("\\s+", " ").split(" ");
        resultado[0] = partesOperacion[0];
        resultado[1] = partesOperacion[1];
        resultado[2] = instruccion[1].trim();
        return resultado;
    }

    /**
     * Valida el formato, el operador y el registro de una instrucción simple.
     *
     * @param instruccion partes de la instrucción que se desea validar.
     * @return {@code null} si la instrucción es válida; en caso contrario, un mensaje con la causa del error.
     */
    public String validarInstruccionSimple(String[] instruccion) {
        if (instruccion.length != 1) {
            return "Formato inválido: una instrucción simple debe tener el formato \"OPERADOR REGISTRO\".";
        }
        String[] partesOperacion = instruccion[0].replaceAll("\\s+", " ").split(" ");
        if (partesOperacion.length != 2) {
            return "Formato inválido: se esperaba \"OPERADOR REGISTRO\".";
        }
        String operador = partesOperacion[0];
        String registro = partesOperacion[1];
        if (!validarOperadorSimple(operador)) {
            return "Operador desconocido: \"" + operador + "\". Operadores válidos: LOAD, STORE, ADD, SUB.";
        }
        if (!validarRegistro(registro)) {
            return "Registro inválido: \"" + registro + "\". Registros válidos: AX, BX, CX, DX.";
        }
        return null;
    }

    /**
     * Valida una instrucción MOV y comprueba que su valor sea un entero dentro del rango de -127 a 127.
     *
     * @param instruccion partes de la instrucción que se desea validar.
     * @return {@code null} si la instrucción es válida; en caso contrario, un mensaje con la causa del error.
     */
    public String validarInstruccionAsignacion(String[] instruccion) {
        if (instruccion.length != 2) {
            return "Formato inválido: una instrucción de asignación debe tener el formato \"OPERADOR REGISTRO, VALOR\".";
        }
        String[] partesOperacion = instruccion[0].replaceAll("\\s+", " ").split(" ");
        if (partesOperacion.length != 2) {
            return "Formato inválido: se esperaba \"OPERADOR REGISTRO, VALOR\".";
        }
        String operador = partesOperacion[0];
        String registro = partesOperacion[1];
        if (!operador.equals("MOV")) {
            if (validarOperadorSimple(operador)) {
                return "El operador \"" + operador + "\" no admite valor.";
            } else {
                return "Operador desconocido: \"" + operador + "\". Operador válido: MOV.";
            }
        }
        if (!validarRegistro(registro)) {
            return "Registro inválido: \"" + registro + "\". Registros válidos: AX, BX, CX, DX.";
        }
        String valorTexto = instruccion[1].trim();
        int valor;
        try {
            valor = Integer.parseInt(valorTexto);
        } catch (NumberFormatException e) {
            return "El valor \"" + valorTexto + "\" no es un número entero válido.";
        }
        if (valor < -127 || valor > 127) {
            return "El valor " + valor + " está fuera del rango permitido (-127 a 127).";
        }
        return null;
    }

    /**
     * Comprueba si un operador corresponde a una instrucción sin valor.
     *
     * @param operador operador que se desea comprobar.
     * @return {@code true} si el operador es LOAD, STORE, SUB o ADD.
     */
    public boolean validarOperadorSimple(String operador) {
        switch (operador) {
            case "LOAD":
            case "STORE":
            case "SUB":
            case "ADD":
                return true;
            default:
                return false;
        }
    }

    /**
     * Comprueba si un operador es reconocido por la Mini PC.
     *
     * @param operador operador que se desea comprobar.
     * @return {@code true} si el operador es válido.
     */
    public boolean validarOperador(String operador) {
        switch (operador) {
            case "LOAD":
            case "STORE":
            case "MOV":
            case "SUB":
            case "ADD":
                return true;
            default:
                return false;
        }
    }

    /**
     * Obtiene el código binario de cuatro bits asociado a un operador.
     *
     * @param operador operador que se desea convertir.
     * @return el código binario del operador, o una cadena vacía si no existe.
     */
    public String convertirOperadorABinario(String operador) {
        String resultado = "";
        switch (operador) {
            case "LOAD":
                resultado = "0001";
                break;
            case "STORE":
                resultado = "0010";
                break;
            case "MOV":
                resultado = "0011";
                break;
            case "SUB":
                resultado = "0100";
                break;
            case "ADD":
                resultado = "0101";
                break;
        }
        return resultado;
    }

    /**
     * Comprueba si un registro es reconocido por la Mini PC.
     *
     * @param registro registro que se desea comprobar.
     * @return {@code true} si el registro es AX, BX, CX o DX.
     */
    public boolean validarRegistro(String registro) {
        switch (registro) {
            case "AX":
            case "BX":
            case "CX":
            case "DX":
                return true;
            default:
                return false;
        }
    }

    /**
     * Obtiene el código binario de cuatro bits asociado a un registro.
     *
     * @param registro registro que se desea convertir.
     * @return el código binario del registro, o una cadena vacía si no existe.
     */
    public String convertirRegistroABinario(String registro) {
        String resultado = "";
        switch (registro) {
            case "AX":
                resultado = "0001";
                break;
            case "BX":
                resultado = "0010";
                break;
            case "CX":
                resultado = "0011";
                break;
            case "DX":
                resultado = "0100";
                break;
        }
        return resultado;
    }

    /**
     * Convierte un número decimal a una representación de ocho bits formada por un bit de signo y siete bits para su magnitud.
     *
     * @param numero número decimal que se desea convertir.
     * @return la representación binaria de ocho bits del número.
     */
    public String convertirDecimalABinario(int numero) {
        boolean esNegativo = false;
        String resultado = "";
        if (numero == 0) {
            return "00000000";
        }
        if (numero < 0) {
            numero = Math.abs(numero);
            esNegativo = true;
        }
        while (numero > 0) {
            int residuo = numero % 2;
            resultado = Integer.toString(residuo) + resultado;
            numero = numero / 2;
        }
        while (resultado.length() < 7) {
            resultado = "0" + resultado;
        }
        if (esNegativo) {
            resultado = "1" + resultado;
        } else {
            resultado = "0" + resultado;
        }
        return resultado;
    }

    /**
     * Separa, valida y procesa una línea de código ensamblador.
     *
     * @param instruccion línea de código que se desea procesar.
     * @return el resultado del procesamiento, con la instrucción normalizada si es válida o con el mensaje de error correspondiente.
     */
    public ResultadoParser procesarInstruccion(String instruccion) {
        String[] partes = instruccion.trim().split(",");
        if (partes.length == 1) {
            String error = validarInstruccionSimple(partes);
            if (error == null) {
                return new ResultadoParser(true, procesarInstruccionSimple(partes), null);
            }
            return new ResultadoParser(false, null, error);
        }
        if (partes.length == 2) {
            String error = validarInstruccionAsignacion(partes);
            if (error == null) {
                return new ResultadoParser(true, procesarInstruccionAsignacion(partes), null);
            }
            return new ResultadoParser(false, null, error);
        }
        return new ResultadoParser(false, null, "Formato inválido: se esperaba \"OPERADOR REGISTRO\" o \"OPERADOR REGISTRO, VALOR\".");
    }

    /**
     * Traduce una instrucción procesada a su representación binaria.
     *
     * @param instruccion arreglo con el operador, el registro y el valor.
     * @return la instrucción binaria con sus campos separados por espacios.
     */
    public String traducirInstruccionABinario(String[] instruccion) {
        String operador = convertirOperadorABinario(instruccion[0]);
        String registro = convertirRegistroABinario(instruccion[1]);
        String valor = convertirDecimalABinario(Integer.parseInt(instruccion[2]));
        String resultado = operador + " " + registro + " " + valor;
        return resultado;
    }

    /**
     * Traduce una instrucción procesada a su representación en ensamblador.
     *
     * @param instruccion arreglo con el operador, el registro y el valor.
     * @return la instrucción en ensamblador con sus campos separados por espacios.
     */
    public String traducirInstruccion(String[] instruccion) {
        if (instruccion == null) {
            return "";
        }
        String operador = instruccion[0];
        String registro = instruccion[1];
        if ("MOV".equals(operador)) {
            String valor = instruccion[2];
            return operador + " " + registro + ", " + valor;
        }
        return operador + " " + registro;
    }
}
