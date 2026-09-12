package com.mycompany.t1operativos;

/**
 * Representa el Bloque de Control de Proceso (BCP) de un programa.
 *
 * Almacena la identificación, el estado, la prioridad, los límites de memoria
 * y el contexto de ejecución necesario para administrar un proceso.
 *
 * @author deislher sánchez funez
 */
public class BCP {
    private int idProceso;
    private String estado;
    private int prioridad;
    private int pc;
    private int inicioMemoria;
    private int finMemoria;
    private String[] ir;
    private int ac;
    private int ax;
    private int bx;
    private int cx;
    private int dx;
    private int instruccionesEjecutadas;

    /**
     * Construye un BCP para un proceso nuevo e inicializa sus registros en cero.
     *
     * @param idProceso identificador único del proceso.
     * @param prioridad prioridad asignada al proceso.
     * @param inicioMemoria primera posición de memoria asignada al proceso.
     * @param finMemoria última posición de memoria asignada al proceso.
     */
    public BCP(int idProceso, int prioridad, int inicioMemoria, int finMemoria) {
        this.idProceso = idProceso;
        this.estado = "NUEVO";
        this.prioridad = prioridad;
        this.pc = inicioMemoria;
        this.inicioMemoria = inicioMemoria;
        this.finMemoria = finMemoria;
        this.ir = null;
        this.ac = 0;
        this.ax = 0;
        this.bx = 0;
        this.cx = 0;
        this.dx = 0;
        this.instruccionesEjecutadas = 0;
    }

    /**
     * Guarda en el BCP el contexto actual de una CPU, incluidos el contador de
     * programa, el registro de instrucción, el acumulador y los registros de
     * propósito general.
     *
     * @param cpu CPU cuyo contexto se desea guardar.
     * @throws IllegalArgumentException si la CPU es {@code null}.
     */
    public void guardarContexto(CPU cpu) {
        if (cpu == null) {
            throw new IllegalArgumentException("El cpu no puede ser nulo.");
        }
        this.pc = cpu.getPc();
        String[] instruccionActual = cpu.getIr();
        if (instruccionActual == null) {
            this.ir = null;
        } else {
            this.ir = instruccionActual.clone();
        }
        this.ac = cpu.getAc();
        this.ax = cpu.getAx();
        this.bx = cpu.getBx();
        this.cx = cpu.getCx();
        this.dx = cpu.getDx();
    }

    /**
     * Incrementa en uno la cantidad de instrucciones ejecutadas por el proceso.
     */
    public void aumentarInstruccionesEjecutadas() {
        instruccionesEjecutadas++;
    }

    /**
     * Obtiene la instrucción guardada en el IR con formato ensamblador legible.
     *
     * @return la instrucción almacenada en el IR, o una cadena vacía si no hay
     *     una instrucción guardada.
     */
    public String getIrToString() {
        if (ir == null) {
            return "";
        }
        if ("MOV".equals(ir[0])) {
            return ir[0] + " " + ir[1] + ", " + ir[2];
        }
        return ir[0] + " " + ir[1];
    }


    /** Establece el estado del proceso como {@code NUEVO}. */
    public void setEstadoNuevo() {
        this.estado = "NUEVO";
    }


    /** Establece el estado del proceso como {@code LISTO}. */
    public void setEstadoListo() {
        this.estado = "LISTO";
    }

    /** Establece el estado del proceso como {@code EJECUTANDO}. */
    public void setEstadoEjecutando() {
        this.estado = "EJECUTANDO";
    }

    /** Establece el estado del proceso como {@code BLOQUEADO}. */
    public void setEstadoBloqueado() {
        this.estado = "BLOQUEADO";
    }

    /** Establece el estado del proceso como {@code TERMINADO}. */
    public void setEstadoTerminado() {
        this.estado = "TERMINADO";
    }

    /** @return el identificador del proceso. */
    public int getIdProceso() {
        return idProceso;
    }
    
    /** @return el estado actual del proceso. */
    public String getEstado() {
        return estado;
    }

    /** @return la prioridad asignada al proceso. */
    public int getPrioridad() {
        return prioridad;
    }

    /** @return el valor guardado del contador de programa (PC). */
    public int getPc() {
        return pc;
    }

    /** @return la primera posición de memoria asignada al proceso. */
    public int getInicioMemoria() {
        return inicioMemoria;
    }

    /** @return la última posición de memoria asignada al proceso. */
    public int getFinMemoria() {
        return finMemoria;
    }

    /**
     * Obtiene una copia de la instrucción guardada en el IR.
     *
     * @return una copia de la instrucción, o {@code null} si no hay una
     *     instrucción guardada.
     */
    public String[] getIr() {
        if (ir == null) {
            return null;
        }
        return ir.clone();
    }

    /** @return el valor guardado del acumulador (AC). */
    public int getAc() {
        return ac;
    }
    
    /** @return el valor guardado del registro AX. */
    public int getAx() {
        return ax;
    }
    
    /** @return el valor guardado del registro BX. */
    public int getBx() {
        return bx;
    }

    /** @return el valor guardado del registro CX. */
    public int getCx() {
        return cx;
    }
    
    /** @return el valor guardado del registro DX. */
    public int getDx() {
        return dx;
    }
    
    /** @return la cantidad de instrucciones ejecutadas por el proceso. */
    public int getInstruccionesEjecutadas() {
        return instruccionesEjecutadas;
    }
    
}
