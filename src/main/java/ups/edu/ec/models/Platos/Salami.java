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
public class Salami extends DecoradorPlato {

    private String nombre;
    private double costo;
    private ImageIcon imagen;

    public Salami(Plato componente) {
        super(componente);
        this.nombre = "Salami";
        this.costo = 3.00;
        this.imagen = new ImageIcon(getClass().getResource("/assets/salami.jpg"));
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
