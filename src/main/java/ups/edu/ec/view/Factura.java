/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ups.edu.ec.view;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import ups.edu.ec.models.Plato;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
/**
 *
 * @author stephancedillo
 */
public class Factura extends javax.swing.JPanel {

    /**
     * Creates new form Factura
     */
    private List<Plato> platosPedidos;

    public Factura(List<Plato> platosPedidos) {
        this.platosPedidos = platosPedidos;

        initComponents();
        aplicarEstilosFlatLaf();
    }
private static final Color CAFE        = new Color(0x6F4E37);
private static final Color CREMA       = new Color(0xFFF3F0);
private static final Color BEIGE       = new Color(0xE8D8C4);
private static final Color BORDE       = new Color(0xD2B48C);
private static final Color TEXTO       = new Color(0x3B2A20);
private static final String IVA_TEXTO  = "IVA (15 %)"; // ajusta a tu tasa

private void aplicarEstilosFlatLaf() {
    // 1. Fondos
    jPanel1.setBackground(CREMA);
    jPanel3.setBackground(CREMA);
    jPanel8.setBackground(CREMA);

    // 2. Título centrado (reemplaza el GroupLayout con gap fijo de 196 px)
    jPanel2.setBackground(CAFE);
    jPanel2.removeAll();
    jPanel2.setLayout(new BorderLayout());
    jPanel2.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
    jLabel1.setText("Factura Final");
    jLabel1.setForeground(Color.WHITE);
    jLabel1.setFont(fuente(Font.BOLD, 16f));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jPanel2.add(jLabel1, BorderLayout.CENTER);

    // 3. Tabla
    estilizarTabla();

    // 4. Totales
    jLabel8.setText("Subtotal");
    jLabel6.setText(IVA_TEXTO);
    jLabel7.setText("Total");
    for (JLabel l : new JLabel[]{jLabel6, jLabel8}) {
        l.setForeground(TEXTO);
        l.setFont(fuente(Font.PLAIN, 13f));
    }
    jLabel7.setForeground(CAFE);
    jLabel7.setFont(fuente(Font.BOLD, 16f));

    estilizarCampoTotal(txtSubtotal, false);
    estilizarCampoTotal(txtIva, false);
    estilizarCampoTotal(txtTotal, true);

    jPanel8.setBorder(BorderFactory.createCompoundBorder(
            new FlatLineBorder(new Insets(1, 1, 1, 1), BORDE, 1f, 16),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
}

private void estilizarTabla() {

    JTableHeader header = tblFactura.getTableHeader();
    header.setReorderingAllowed(false);
    header.putClientProperty(FlatClientProperties.STYLE,
            "background: #6F4E37; foreground: #FFFFFF; font: bold 13;"
            + "height: 32; separatorColor: #6F4E37; bottomSeparatorColor: #6F4E37;"
            + "hoverBackground: #6F4E37; pressedBackground: #6F4E37;"
            + "cellMargins: 4,8,4,8");

    javax.swing.table.TableCellRenderer base = header.getDefaultRenderer();
    header.setDefaultRenderer((t, value, sel, focus, row, col) -> {
        java.awt.Component c = base.getTableCellRendererComponent(t, value, sel, focus, row, col);
        if (c instanceof JLabel lbl) {
            lbl.setHorizontalAlignment((col == 2 || col == 3) ? SwingConstants.RIGHT : SwingConstants.LEFT);
        }
        return c;
    });

    tblFactura.setRowHeight(30);
    tblFactura.setShowVerticalLines(true);
    tblFactura.setShowHorizontalLines(false);
    tblFactura.setIntercellSpacing(new java.awt.Dimension(0, 1));
    tblFactura.setBackground(Color.WHITE);
    tblFactura.setForeground(TEXTO);
    tblFactura.setFont(fuente(Font.PLAIN, 13f));
    tblFactura.putClientProperty(FlatClientProperties.STYLE,
            "gridColor: #EFE3D3; selectionBackground: #E8D8C4; selectionForeground: #3B2A20");

   
    DefaultTableCellRenderer moneda = new DefaultTableCellRenderer() {
        @Override
        protected void setValue(Object v) {
            setText(v instanceof Number n ? String.format("$ %.2f", n.doubleValue()) : "");
        }
    };
    moneda.setHorizontalAlignment(SwingConstants.RIGHT);
    tblFactura.setDefaultRenderer(Double.class, moneda);


    jScrollPane1.setBorder(BorderFactory.createLineBorder(BORDE));
    jScrollPane1.getViewport().setBackground(Color.WHITE);
}

private void estilizarCampoTotal(JTextField campo, boolean destacado) {
    campo.setEnabled(true);         
    campo.setEditable(false);        
    campo.setHorizontalAlignment(SwingConstants.RIGHT);
    campo.setText("0.00");
    campo.setForeground(destacado ? CAFE : TEXTO);
    campo.setFont(fuente(destacado ? Font.BOLD : Font.PLAIN, destacado ? 16f : 13f));
    campo.putClientProperty(FlatClientProperties.STYLE,
            "arc: 12; borderWidth: 1; borderColor: #D2B48C;"
            + "background: " + (destacado ? "#F3E7D8" : "#FFFFFF") + ";"
           );
}

// Fuente base del sistema (evita Rockwell/Hiragino/Krungthep, que solo existen en Mac)
private Font fuente(int estilo, float tamano) {
    return UIManager.getFont("Label.font").deriveFont(estilo, tamano);
}

    public List<Plato> getPlatosPedidos() {
        return platosPedidos;
    }

    public JTable getTblFactura() {
        return tblFactura;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFactura = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        txtIva = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Krungthep", 0, 13)); // NOI18N
        jLabel1.setText("Factura Final");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblFactura.setToolTipText("");
        tblFactura.setEnabled(false);
        jScrollPane1.setViewportView(tblFactura);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Hiragino Mincho ProN", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("IVA %10");

        jLabel7.setFont(new java.awt.Font("Hiragino Mincho ProN", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Total");

        jLabel8.setFont(new java.awt.Font("Hiragino Mincho ProN", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Subtotal");

        txtSubtotal.setBackground(new java.awt.Color(255, 255, 255));
        txtSubtotal.setFont(new java.awt.Font("Hiragino Mincho ProN", 0, 10)); // NOI18N
        txtSubtotal.setForeground(new java.awt.Color(0, 0, 0));
        txtSubtotal.setText("jTextField1");
        txtSubtotal.setEnabled(false);

        txtIva.setBackground(new java.awt.Color(255, 255, 255));
        txtIva.setFont(new java.awt.Font("Hiragino Mincho ProN", 0, 10)); // NOI18N
        txtIva.setForeground(new java.awt.Color(0, 0, 0));
        txtIva.setText("jTextField1");
        txtIva.setEnabled(false);

        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Hiragino Mincho ProN", 0, 10)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(0, 0, 0));
        txtTotal.setText("jTextField1");
        txtTotal.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFactura;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
