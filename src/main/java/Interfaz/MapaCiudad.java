package Interfaz;

import Modelo.Interseccion;
import Modelo.Calle;
import Estructuras.Grafo;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapaCiudad extends JPanel {

    private Grafo grafo;
    private Map<Interseccion, Point> coordenadas = new HashMap<>();

    public MapaCiudad() {
        setPreferredSize(new Dimension(600, 350));
        setBackground(Color.WHITE); 
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
        inicializarCoordenadasAutomaticas();

        // Escucha cuando cambia de tamaño el panel para recalcular y redibujar fluido
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                inicializarCoordenadasAutomaticas();
                repaint();
            }
        });

        repaint();
    }

    private void inicializarCoordenadasAutomaticas() {
        coordenadas.clear();
        if (grafo == null) {
            return;
        }

        int margenX = 60;
        int margenY = 50;

        int ancho = getWidth() - 2 * margenX;
        int alto = getHeight() - 2 * margenY;
        
        // Evitar divisiones por cero si el panel aún no se ha pintado
        if (ancho <= 0) ancho = 500;
        if (alto <= 0) alto = 300;

        for (Interseccion interseccion : grafo.getListaAdyacencia().keySet()) {
            // Mapea las coordenadas x, y de la intersección proporcionalmente al tamaño del panel
            int x = margenX + interseccion.getX() * ancho / 3;
            int y = margenY + interseccion.getY() * alto / 3;

            coordenadas.put(interseccion, new Point(x, y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo verde oscuro para la simulación de la ciudad
        g.setColor(new Color(20, 45, 35));
        g.fillRect(0, 0, getWidth(), getHeight());

        if (grafo == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        Map<Interseccion, List<Calle>> adyacencia = grafo.getListaAdyacencia();

        // 1. Dibujar las calles
        g2d.setStroke(new BasicStroke(4));
        
        for (Map.Entry<Interseccion, List<Calle>> entry : adyacencia.entrySet()) {
            Interseccion origen = entry.getKey();
            Point pOrigen = coordenadas.get(origen);

            if (pOrigen == null) continue;

            for (Calle calle : entry.getValue()) {
                Interseccion destino = calle.getDestino();
                Point pDestino = coordenadas.get(destino);

                if (pDestino != null) {
                    g2d.setColor(new Color(170, 180, 190));
                    g2d.drawLine(
                        pOrigen.x, pOrigen.y,
                        pDestino.x, pDestino.y
                    );

                    // Peso de la calle
                    g2d.setColor(new Color(220, 230, 220));
                    int midX = (pOrigen.x + pDestino.x) / 2;
                    int midY = (pOrigen.y + pDestino.y) / 2;

                    g2d.drawString(
                        String.valueOf(calle.getPesoRuta()),
                        midX + 4, midY - 4
                    );
                }
            }
        }

        // 2. Dibujar intersecciones, sedes y emergencias
        for (Map.Entry<Interseccion, Point> entry : coordenadas.entrySet()) {
            Interseccion inter = entry.getKey();
            Point p = entry.getValue();

            boolean tieneSede = inter.isHaySede();
            boolean tieneEmergencia = inter.isTieneEmergencia();

            int tamaño; // Usando la variable con la "ñ" que prefieres
            if (tieneEmergencia) {
                tamaño = 25;
            } else if (tieneSede) {
                tamaño = 26;
            } else {
                tamaño = 14;
            }

            // 3. Elegir el color según el estado
            if (tieneEmergencia) {
                g2d.setColor(new Color(235, 45, 55)); // Rojo alerta
            } else if (tieneSede) {
                if ("Hospital".equalsIgnoreCase(inter.getTipoSede())) {
                    g2d.setColor(new Color(220, 65, 65));
                } else if ("Bomberos".equalsIgnoreCase(inter.getTipoSede())) {
                    g2d.setColor(new Color(240, 145, 45));
                } else {
                    g2d.setColor(new Color(55, 125, 210));
                }
            } else {
                g2d.setColor(new Color(95, 115, 105)); // Intersección normal
            }

            // 4. Dibujar la forma geométrica correspondiente
            if (tieneEmergencia) {
                // Triángulo para emergencias
                int[] xPoints = { p.x, p.x - tamaño / 2, p.x + tamaño / 2 };
                int[] yPoints = { p.y - tamaño / 2, p.y + tamaño / 2, p.y + tamaño / 2 };

                g2d.fillPolygon(xPoints, yPoints, 3);
                g2d.setColor(Color.WHITE);
                g2d.drawPolygon(xPoints, yPoints, 3);

                g2d.drawString(inter.getId(), p.x - 10, p.y + 23);

            } else if (tieneSede) {
                // Rectángulo redondeado para sedes
                g2d.fillRoundRect(p.x - tamaño / 2, p.y - tamaño / 2, tamaño, tamaño, 7, 7);
                g2d.setColor(Color.WHITE);
                g2d.drawRoundRect(p.x - tamaño / 2, p.y - tamaño / 2, tamaño, tamaño, 7, 7);

                g2d.drawString(inter.getId(), p.x - 10, p.y + 23);
                g2d.setColor(Color.WHITE);
                g2d.drawString(inter.getTipoSede(), p.x + 17, p.y);

            } else {
                // Círculo normal
                g2d.fillOval(p.x - tamaño / 2, p.y - tamaño / 2, tamaño, tamaño);
                g2d.setColor(Color.WHITE);
                g2d.drawOval(p.x - tamaño / 2, p.y - tamaño / 2, tamaño, tamaño);

                g2d.drawString(inter.getId(), p.x - 8, p.y + 23);
            }
        }
    }
}