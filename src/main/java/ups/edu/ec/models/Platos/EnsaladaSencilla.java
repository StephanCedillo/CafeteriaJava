/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ups.edu.ec.models.Platos;

import javax.swing.ImageIcon;
import ups.edu.ec.models.Plato;

/**
 *
 * @author stephancedillo
 */
public class EnsaladaSencilla  implements Plato{

    private String nombre;
    private double costo;
    private ImageIcon imagen;
    
    private String ruta;

    public EnsaladaSencilla() {
        this.nombre = "Ensalada Sencilla";
        this.costo = 3.00;
        this.imagen = new ImageIcon(getClass().getResource("/assets/ensalada.jpg"));
    }
    
      @Override
    public Double getCosto() {
        return costo; 
    }

    @Override
    public String getNombre() {
        return nombre; 
    
    }
    @Override
    public ImageIcon getImagen() {
        return imagen;
    }
       @Override
    public double costoPropio() {
      return costo;
    }

    @Override
    public String nombrePropio() {
      return nombre;
    }
}
