/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ups.edu.ec.models;

/**
 *
 * @author stephancedillo
 */


public abstract class DecoradorPlato implements Plato {

    protected final Plato componente;
    private static int decoradores = 0;

    public DecoradorPlato(Plato componente) {
        if (componente == null) {
            throw new IllegalArgumentException("Plato Obligatorio");
        }
        decoradores += 1;
        this.componente = componente;
    }

//Para definir dentro de la clase
    public abstract double costoPropio();
    public abstract String nombrePropio();

    @Override
    public String getNombre() {
      
        return  nombrePropio(); 
    }

    @Override
    public Double getCosto() {
        return componente.getCosto() + costoPropio();
    }

    public Plato getComponente() {
        return componente;
    }

    public int getCantidadDecoradores() {
        return decoradores;
    }
}
