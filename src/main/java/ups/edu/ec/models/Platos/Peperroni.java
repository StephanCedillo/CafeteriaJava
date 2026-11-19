/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ups.edu.ec.models.Platos;

import javax.swing.ImageIcon;
import ups.edu.ec.models.DecoradorPlato;
import ups.edu.ec.models.Plato;

/**
 *
 * @author stephancedillo
 */
public class Peperroni extends DecoradorPlato {

    private String nombre;
    private double costo;
    private ImageIcon imagen;

    public Peperroni(Plato componente) {
        super(componente);
        this.nombre = "Peperroni";
        this.costo = 1.10;
        this.imagen = new ImageIcon(getClass().getResource("/assets/peperroni.jpg"));
    }

       @Override
    public double costoPropio() {
        return costo; 
    }

    @Override
    public String nombrePropio() {
        return nombre; 
    
    }
    @Override
    public ImageIcon getImagen() {
        return imagen;
    }
}
