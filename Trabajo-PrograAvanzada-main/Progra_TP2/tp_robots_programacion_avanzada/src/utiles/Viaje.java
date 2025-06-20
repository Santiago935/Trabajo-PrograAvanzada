package utiles;

import grafos.*;


public class Viaje {
    private Nodo destino;
    private int pasosRestantes;
    private double bateriaFinal;
    private Pedido pedido;
    private boolean ida; // true = hacia el Cofre solicitante, false = al Cofre receptor

    public Viaje(Nodo destino, int pasos, double bateriaFinal, Pedido pedido, boolean ida) {
        this.destino = destino;
        this.pasosRestantes = pasos;
        this.bateriaFinal = bateriaFinal;
        this.pedido = pedido;
        this.ida = ida;
    }

    // Getters
    public Nodo getDestino() { return destino; }
    public int getPasosRestantes() { return pasosRestantes; }
    public double getBateriaFinal() { return bateriaFinal; }
    public Pedido getPedido() { return pedido; }
    public boolean isIda() { return ida; }

    public void avanzar() {
        pasosRestantes--;
    }

    public boolean llego() {
        return pasosRestantes <= 0;
    }
    
}

