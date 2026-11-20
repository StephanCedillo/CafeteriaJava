package ups.edu.ec.controllers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import ups.edu.ec.models.*;
import ups.edu.ec.models.Bebidas.*;
import ups.edu.ec.models.Platos.*;
import ups.edu.ec.view.*;

/**
 *
 * @author stephancedillo
 */
public class PrincipalController {

    private final VistaPrincipal ventana;
    private static int numeroOrden = 001;
    private boolean isModificando = false;
    private int indexModificado = -1;

    private List<PlatoPedido> platosPedidos;
    private Map<Plato, List<Class<? extends DecoradorPlato>>> platos;

    public PrincipalController(VistaPrincipal ventana) {
        this.ventana = ventana;
        platosPedidos = new ArrayList<>();
        platos = new HashMap<>();
        inicializar();
        agregarProductos();
        agregarEventosPrincipal();
        actualizar();
        configurarTablaEventos();
    }

    private void inicializar() {
        List<Class<? extends DecoradorPlato>> decoradoresEnsalada = new ArrayList<>();
        List<Class<? extends DecoradorPlato>> decoradoresPizza = new ArrayList<>();
        List<Class<? extends DecoradorPlato>> decoradoresCafe = new ArrayList<>();
        decoradoresCafe.add(Azucar.class);
        decoradoresCafe.add(Crema.class);
        decoradoresEnsalada.add(Pollo.class);
        decoradoresEnsalada.add(Vinagreta.class);
        decoradoresEnsalada.add(Tomate.class);
        decoradoresPizza.add(Peperroni.class);
        decoradoresPizza.add(Queso.class);
        decoradoresPizza.add(Salami.class);
        platos.put(new EnsaladaSencilla(), decoradoresEnsalada);
        platos.put(new PizzaSencilla(), decoradoresPizza);
        platos.put(new Cafe(), decoradoresCafe);

    }

    private void agregarProductos() {
        List<Producto> productosPaneles = new ArrayList<>();
        // 1. Crear un panel contenedor general 
        JPanel contenedorGeneral = new JPanel();
        contenedorGeneral.setLayout(new BoxLayout(contenedorGeneral, BoxLayout.Y_AXIS));

        //2.  Agregar Platos
        for (Map.Entry<Plato, List<Class<? extends DecoradorPlato>>> entry : platos.entrySet()) {
            Plato key = entry.getKey();
            List<Class<? extends DecoradorPlato>> value = entry.getValue();

            Producto panelPlato = new Producto(key, value);
            agregarEventosPanel(panelPlato);
            panelPlato.colocarImagenPrincipal(panelPlato.getLblIconPrincipal(), key.getImagen());

            panelPlato.getLblPrincipal().setText(key.nombrePropio());
            // para q salga con 2 decimales usar format
            panelPlato.getLblPrincipalCosto1().setText(String.format("$%.2f", key.costoPropio()));

            JLabel[] labels = {
                panelPlato.getLblIcon1(), panelPlato.getLblAdicional1(), panelPlato.getLblCosto1(),
                panelPlato.getLblIcon2(), panelPlato.getLblAdicional2(), panelPlato.getLblCosto2(),
                panelPlato.getLblIcon3(), panelPlato.getLblAdicional3(), panelPlato.getLblCosto3()
            };

            int i = 0;
            for (Class<? extends DecoradorPlato> clase : value) {
                try {
                    if (i >= labels.length) {
                        break;
                    }

                    DecoradorPlato decorador = clase.getDeclaredConstructor(Plato.class).newInstance(key);

                    labels[i].setIcon(decorador.getImagen());
                    panelPlato.colocarImagen(labels[i], decorador.getImagen());
                    labels[i + 1].setText(decorador.nombrePropio());
                    labels[i + 2].setText("$" + String.valueOf(decorador.costoPropio()));

                    i += 3;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            contenedorGeneral.add(panelPlato);

        }

        ventana.getScrollCatalogo().setViewportView(contenedorGeneral);
        ventana.getScrollCatalogo().revalidate();
        ventana.getScrollCatalogo().repaint();
    }

    Plato platoTemporal = null;
    List<Class<? extends DecoradorPlato>> decoradoresTemporales = new ArrayList<>();

    private void agregarEventosPanel(Producto panelPlato) {
        List<Class<? extends DecoradorPlato>> decoradoresPermitidos = platos.get(panelPlato.getPlato());

        panelPlato.getLblIconPrincipal().addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (platoTemporal != null) {
                    // platosPedidos.put(platoTemporal, decoradoresTemporales);
                    platosPedidos.add(new PlatoPedido(platoTemporal, decoradoresTemporales));

                    decoradoresTemporales = new ArrayList<>();
                    platoTemporal = null;
                }
                System.out.println(panelPlato.getPlato());
                platoTemporal = panelPlato.getPlato();
                isModificando = false;
                indexModificado = -1;
                actualizar();
            }
        });

        panelPlato.getLblIcon1().addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isModificando == true) {
                    platosPedidos.get(indexModificado).getDecoradores().add(panelPlato.getDecoradores().get(0));
                   actualizar();
                    return;
                }
                if (platoTemporal == null || !platoTemporal.equals(panelPlato.getPlato())) {
                    return;
                }
                for (Class<? extends DecoradorPlato> decoradoresPermitido : decoradoresPermitidos) {
                    if (decoradoresPermitido.equals(panelPlato.getDecoradores().get(0))) {
                        decoradoresTemporales.add(panelPlato.getDecoradores().get(0));
                        actualizar();

                        return;
                    }
                }

            }

        });
        panelPlato.getLblIcon2().addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isModificando == true) {
                    platosPedidos.get(indexModificado).getDecoradores().add(panelPlato.getDecoradores().get(0));
                    actualizar();
                    return;
                }
                if (platoTemporal == null || !platoTemporal.equals(panelPlato.getPlato())) {
                    return;
                }
                for (Class<? extends DecoradorPlato> decoradoresPermitido : decoradoresPermitidos) {
                    if (decoradoresPermitido.equals(panelPlato.getDecoradores().get(1))) {
                        decoradoresTemporales.add(panelPlato.getDecoradores().get(1));
                        actualizar();

                        return;
                    }
                }

            }
        });
        panelPlato.getLblIcon3().addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isModificando == true) {
                    platosPedidos.get(indexModificado).getDecoradores().add(panelPlato.getDecoradores().get(0));
                  actualizar();
                    return;
                }
                if (platoTemporal == null || !platoTemporal.equals(panelPlato.getPlato())) {
                    return;
                }
                if (decoradoresPermitidos.size() < 3) {
                    return;
                }
                for (Class<? extends DecoradorPlato> decoradoresPermitido : decoradoresPermitidos) {
                    if (decoradoresPermitido.equals(panelPlato.getDecoradores().get(2))) {
                        decoradoresTemporales.add(panelPlato.getDecoradores().get(2));
                        actualizar();

                        return;
                    }
                }

            }
        });
    }

    private void agregarEventosPrincipal() {
        ventana.getBtnEnviar().addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // ACORDARME DE AGREGAR FACTURA FINAL
                numeroOrden++;
                List<Plato> platosFinales = new ArrayList<>();
                Plato platoTempDecorado = platoTemporal;
                // 2. Procesar el plato temporal
                platosPedidos.add(new PlatoPedido(platoTemporal, decoradoresTemporales));
                for (PlatoPedido plato : platosPedidos) {
                    platoTempDecorado = agregarPlatoConstructor(plato.getPlato(), plato.getDecoradores());
                    platosFinales.add(platoTempDecorado);

                }

                Factura facturaFinal = new Factura(platosFinales);
                configurarFacturaFinal(facturaFinal);
                mostrarVentanaEmergente(ventana, facturaFinal);
                platosPedidos.clear();
                platoTemporal = null;
                decoradoresTemporales.clear();
                actualizar();
            }

        });
    }

    private void configurarFacturaFinal(Factura facturaFinal) {

        TableModel modeloOriginal = ventana.getTblFactura().getModel();
        int filas = modeloOriginal.getRowCount();
        int columnas = modeloOriginal.getColumnCount() - 1;

        String[] nombresColumnas = new String[columnas];
        for (int j = 0; j < columnas; j++) {
            nombresColumnas[j] = modeloOriginal.getColumnName(j);
        }

        Object[][] datos = new Object[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datos[i][j] = modeloOriginal.getValueAt(i, j);
            }
        }

// PORQUE ESTA CON COMA Y HAY Q PONER PUNTO
        double subCopia = Double.parseDouble(ventana.getTxtSubtotal().getText().replace(',', '.'));
        double ivaCopia = Double.parseDouble(ventana.getTxtIva().getText().replace(',', '.'));
        double totalCopia = Double.parseDouble(ventana.getTxtTotal().getText().replace(',', '.'));
        DefaultTableModel nuevoModelo = new DefaultTableModel(datos, nombresColumnas);
        facturaFinal.getTblFactura().setModel(nuevoModelo);
        facturaFinal.getTxtSubtotal().setText(String.format("%.2f", subCopia));
        facturaFinal.getTxtIva().setText(String.format("%.2f", ivaCopia));
        facturaFinal.getTxtTotal().setText(String.format("%.2f", totalCopia));
        //String.format("%.2f", total)

    }
    // private Map<Plato, List<Class<? extends DecoradorPlato>>> platosPedidos = new
    // HashMap<>();
    // Plato platoTemporal = null;
    // List<Class<? extends DecoradorPlato>> decoradoresTemporales = null;
    // ACORDARME DE AÑADIR DECORADORES A PLATO
    List<Plato> platosDecoradores = new ArrayList<>();

    private void actualizar() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ventana.getLblOrden().setText(String.valueOf(numeroOrden));
        modelo.addColumn("Producto");
        modelo.addColumn("Adicional");
        modelo.addColumn("Costo");
        modelo.addColumn("Total");
        modelo.addColumn("X");

        ventana.getTblFactura().setModel(modelo);

        ventana.getTblFactura().getColumnModel().getColumn(0).setPreferredWidth(180); // Producto
        ventana.getTblFactura().getColumnModel().getColumn(1).setPreferredWidth(130); // Adicional
        ventana.getTblFactura().getColumnModel().getColumn(2).setPreferredWidth(75); // Costo
        ventana.getTblFactura().getColumnModel().getColumn(3).setPreferredWidth(75); // Total

        ventana.getTblFactura().getColumnModel().getColumn(4).setPreferredWidth(25);

        platosDecoradores.clear();
        // 1. Procesar todos los platos pedidos en el mapa
        for (PlatoPedido platoPedido : platosPedidos) {
            Plato key = platoPedido.getPlato();
            List<Class<? extends DecoradorPlato>> value = platoPedido.getDecoradores();

            // Agregar plato base
            modelo.addRow(new Object[]{
                key.getNombre(),
                "",
                String.format("%.2f", key.getCosto()),
                String.format("%.2f", key.getCosto()),
                "X"
            });

            Plato platoTemporalTbl = key;

            // Agregar decoradores del plato actual
            for (Class<? extends DecoradorPlato> decorador : value) {
                try {
                    platoTemporalTbl = decorador
                            .getDeclaredConstructor(Plato.class)
                            .newInstance(platoTemporalTbl);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                modelo.addRow(new Object[]{
                    "",
                    platoTemporalTbl.nombrePropio(),
                    String.format("%.2f", platoTemporalTbl.costoPropio()),
                    String.format("%.2f", platoTemporalTbl.getCosto()),
                    "X"
                });
            }
            platosDecoradores.add(platoTemporalTbl);
        }
        Plato platoTempDecorado = platoTemporal;
        // 2. Procesar el plato temporal
        if (platoTemporal != null) {
            modelo.addRow(new Object[]{
                platoTemporal.nombrePropio(),
                "",
                String.format("%.2f", platoTemporal.costoPropio()),
                String.format("%.2f", platoTemporal.getCosto()),
                "X"
            });

            platoTempDecorado = platoTemporal;

            if (platoTempDecorado != null) {
                for (Class<? extends DecoradorPlato> decorador : decoradoresTemporales) {
                    try {
                        platoTempDecorado = decorador
                                .getDeclaredConstructor(Plato.class)
                                .newInstance(platoTempDecorado);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    modelo.addRow(new Object[]{
                        "",
                        platoTempDecorado.getNombre(),
                        String.format("%.2f", platoTempDecorado.costoPropio()),
                        String.format("%.2f", platoTempDecorado.getCosto()),
                        "X"
                    });
                }

            }

        }
        double subtotal = 0;
        if (platoTemporal != null) {
            subtotal = platoTempDecorado.getCosto();
           
        }

        double iva = 0;
        double total = 0;

        for (Plato platoD : platosDecoradores) {
            subtotal += platoD.getCosto();
           

        }

        iva = subtotal * 0.10;
        total = subtotal + iva;
        ventana.getTxtSubtotal().setText(
                String.format("%.2f", subtotal));
        ventana.getTxtIva().setText(
                String.format("%.2f", iva));
        ventana.getTxtTotal().setText(
                String.format("%.2f", total));
        ventana.getTxtSubtotal().setPreferredSize(new Dimension(60, 30));
        ventana.getTxtIva().setPreferredSize(new Dimension(60, 30));
        ventana.getTxtTotal().setPreferredSize(new Dimension(60, 30));

    }

    // Plato platoTemporal = null;
    // List<Class<? extends DecoradorPlato>> decoradoresTemporales = null;
    private void configurarTablaEventos() {

        // configurar eliminar
        ventana.getTblFactura().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = ventana.getTblFactura().rowAtPoint(e.getPoint());
                int columna = ventana.getTblFactura().columnAtPoint(e.getPoint());

                if (columna == 4 && fila != -1) {
                    int contador = 0;

                    for (int i = 0; i < platosPedidos.size(); i++) {
                        PlatoPedido platoPedido = platosPedidos.get(i);

                        if (contador == fila && platoPedido.getDecoradores().isEmpty()) {

                            platosPedidos.remove(i);
                            actualizar();
                            return;
                        } else if (contador == fila) {

                            return;
                        }
                        contador++;

                        List<Class<? extends DecoradorPlato>> decoradores = platoPedido.getDecoradores();
                        for (int j = 0; j < decoradores.size(); j++) {
                            if (contador == fila) {

                                decoradores.remove(j);
                                actualizar();
                                return;
                            }

                            contador++;
                        }
                    }

                    if (platoTemporal != null) {
                        if (contador == fila && decoradoresTemporales.isEmpty()) {

                            platoTemporal = null;
                            decoradoresTemporales = new ArrayList<>();
                            actualizar();
                            return;
                        } else if (contador == fila) {

                            return;
                        }
                        contador++;
                        for (int k = 0; k < decoradoresTemporales.size(); k++) {
                            if (contador == fila) {

                                decoradoresTemporales.remove(k);
                                actualizar();
                                return;
                            }
                            contador++;
                        }
                    }
                }

                if (columna == 0 && fila != -1) {
                    int contador = 0;
                    
                    for (int i = 0; i < platosPedidos.size(); i++) {
                        PlatoPedido platoPedido = platosPedidos.get(i);

                        if (contador == fila) {
                            System.out.println("agregar");
                            isModificando = true;
                            indexModificado = i;
                            return;
}
                        contador++;

                        List<Class<? extends DecoradorPlato>> decoradores = platoPedido.getDecoradores();
                        for (int j = 0; j < decoradores.size(); j++) {
                            contador++;
                        }
                    }
                }
            }

        });

    }

    public Plato agregarPlatoConstructor(Plato plato, List<Class<? extends DecoradorPlato>> decoradores) {
        Plato platoTempDecorado = plato;
        if (platoTempDecorado != null && decoradores != null) {
            for (Class<? extends DecoradorPlato> decorador : decoradores) {
                try {
                    platoTempDecorado = decorador
                            .getDeclaredConstructor(Plato.class)
                            .newInstance(platoTempDecorado);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return platoTempDecorado;
    }

    public void mostrarVentanaEmergente(JFrame ventanaPadre, Factura panelAmostrar) {
        JDialog dialogo = new JDialog(ventanaPadre, "Factura Final", false);
        dialogo.getContentPane().add(panelAmostrar);
        dialogo.pack();
        dialogo.setLocationRelativeTo(ventanaPadre);
        dialogo.setVisible(true);
    }
}
