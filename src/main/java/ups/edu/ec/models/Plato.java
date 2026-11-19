/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ups.edu.ec.models;

import javax.swing.ImageIcon;

/**
 *
 * @author stephancedillo
 */
public interface Plato {
    String getNombre();
    Double getCosto();
    ImageIcon getImagen();
  
    double costoPropio();
     String nombrePropio();
};
