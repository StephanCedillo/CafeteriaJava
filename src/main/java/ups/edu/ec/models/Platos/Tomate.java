/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ups.edu.ec.models.Platos;

/**
 *
 * @author stephancedillo
 */
import javax.swing.ImageIcon;
import ups.edu.ec.models.DecoradorPlato;
import ups.edu.ec.models.Plato;
public class Tomate extends DecoradorPlato {

    private String nombre;
    private double costo;
    private ImageIcon imagen;

    public Tomate(Plato componente) {
        super(componente);
        this.nombre = "Tomate";
        this.costo = 1.10;
        this.imagen = new ImageIcon(getClass().getResource("/assets/tomate.jpg"));
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
