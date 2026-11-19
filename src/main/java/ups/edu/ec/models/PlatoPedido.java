/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ups.edu.ec.models;

import java.util.List;

/**
 *
 * @author stephancedillo
 */
public class PlatoPedido {
    private Plato plato;
    private List<Class<? extends DecoradorPlato>> decoradores;

    public PlatoPedido(Plato plato, List<Class<? extends DecoradorPlato>> decoradores) {
        this.plato = plato;
        this.decoradores = decoradores;
    }

    public Plato getPlato() {
        return plato;
    }

    public List<Class<? extends DecoradorPlato>> getDecoradores() {
        return decoradores;
    }
}