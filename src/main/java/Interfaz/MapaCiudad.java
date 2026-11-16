package Interfaz;

import Modelo.Interseccion;
import Modelo.Calle;
import Estructuras.Grafo;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MapaCiudad extends JPanel {

    private Grafo grafo;
    private Map<Interseccion, Point> coordenadas = new HashMap<>();
    private Map<Interseccion, Double> posRelativaX = new HashMap<>();
    private Map<Interseccion, Double> posRelativaY = new HashMap<>();
    
    
    private Map<String, Point> unidadesMoviles = new ConcurrentHashMap<>();
    private Map<String, Color> coloresUnidades = new ConcurrentHashMap<>();
    private Map<String, List<Interseccion>> rutasActivas = new ConcurrentHashMap<>();

    public MapaCiudad() {
        setPreferredSize(new Dimension(600, 440));
        setBackground(new Color(244, 247, 245)); 
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
        generarPosicionesAleatorias();
        recalcularCoordenadas();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                recalcularCoordenadas();
                repaint();
            }
        });

        repaint();
    }

    
    private void generarPosicionesAleatorias() {
        posRelativaX.clear();
        posRelativaY.clear();
        if (grafo == null) return;

        Random rand = new Random();
        List<Point.Double> puntosGenerados = new ArrayList<>();

        for (Interseccion inter : grafo.getListaAdyacencia().keySet()) {
            double rx = 0.15 + rand.nextDouble() * 0.70;
            double ry = 0.15 + rand.nextDouble() * 0.70;

            
            for (int intento = 0; intento < 30; intento++) {
                boolean muyCerca = false;
                for (Point.Double p : puntosGenerados) {
                    if (Math.hypot(rx - p.x, ry - p.y) < 0.22) {
                        muyCerca = true;
                        break;
                    }
                }
                if (!muyCerca) break;
                rx = 0.15 + rand.nextDouble() * 0.70;
                ry = 0.15 + rand.nextDouble() * 0.70;
            }

            puntosGenerados.add(new Point.Double(rx, ry));
            posRelativaX.put(inter, rx);
            posRelativaY.put(inter, ry);
        }
    }

    private void recalcularCoordenadas() {
        coordenadas.clear();
        if (grafo == null) return;

        int ancho = getWidth() > 0 ? getWidth() : 580;
        int alto = getHeight() > 0 ? getHeight() : 440;

        for (Interseccion inter : grafo.getListaAdyacencia().keySet()) {
            double rx = posRelativaX.getOrDefault(inter, 0.5);
            double ry = posRelativaY.getOrDefault(inter, 0.5);
            coordenadas.put(inter, new Point((int) (rx * ancho), (int) (ry * alto)));
        }
    }

    
    public void actualizarPosicionUnidad(String idEmergencia, Interseccion desde, Interseccion hasta, double progreso, Color color, List<Interseccion> ruta) {
        Point p1 = coordenadas.get(desde);
        Point p2 = coordenadas.get(hasta);
        if (p1 != null && p2 != null) {
            int x = (int) (p1.x + (p2.x - p1.x) * progreso);
            int y = (int) (p1.y + (p2.y - p1.y) * progreso);
            unidadesMoviles.put(idEmergencia, new Point(x, y));
            coloresUnidades.put(idEmergencia, color);
            if (ruta != null) {
                rutasActivas.put(idEmergencia, ruta);
            }
            repaint();
        }
    }

    public void removerUnidad(String idEmergencia) {
        unidadesMoviles.remove(idEmergencia);
        coloresUnidades.remove(idEmergencia);
        rutasActivas.remove(idEmergencia);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        g2d.setColor(new Color(244, 247, 245));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(232, 238, 234));
        g2d.setStroke(new BasicStroke(1));
        for (int x = 0; x < getWidth(); x += 40) g2d.drawLine(x, 0, x, getHeight());
        for (int y = 0; y < getHeight(); y += 40) g2d.drawLine(0, y, getWidth(), y);

        if (grafo == null) return;

        Map<Interseccion, List<Calle>> adyacencia = grafo.getListaAdyacencia();

     
        for (Map.Entry<Interseccion, List<Calle>> entry : adyacencia.entrySet()) {
            Interseccion origen = entry.getKey();
            Point pOrigen = coordenadas.get(origen);
            if (pOrigen == null) continue;

            for (Calle calle : entry.getValue()) {
                Interseccion destino = calle.getDestino();
                Point pDestino = coordenadas.get(destino);
                if (pDestino == null) continue;

                
                g2d.setColor(new Color(203, 213, 225));
                g2d.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(pOrigen.x, pOrigen.y, pDestino.x, pDestino.y);

                g2d.setColor(new Color(255, 255, 255));
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(pOrigen.x, pOrigen.y, pDestino.x, pDestino.y);

               
                int midX = (pOrigen.x + pDestino.x) / 2;
                int midY = (pOrigen.y + pDestino.y) / 2;
                g2d.setColor(new Color(244, 247, 245));
                g2d.fillRoundRect(midX - 12, midY - 10, 26, 16, 6, 6);
                g2d.setColor(new Color(71, 85, 105));
                g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
                g2d.drawString(String.valueOf(calle.getPesoRuta()), midX - 9, midY + 2);
            }
        }

        
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (Map.Entry<String, List<Interseccion>> rutaEntry : rutasActivas.entrySet()) {
            List<Interseccion> ruta = rutaEntry.getValue();
            Color colorRuta = coloresUnidades.getOrDefault(rutaEntry.getKey(), new Color(16, 185, 129));
            g2d.setColor(new Color(colorRuta.getRed(), colorRuta.getGreen(), colorRuta.getBlue(), 130));

            for (int i = 0; i < ruta.size() - 1; i++) {
                Point p1 = coordenadas.get(ruta.get(i));
                Point p2 = coordenadas.get(ruta.get(i + 1));
                if (p1 != null && p2 != null) {
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

     
        for (Map.Entry<Interseccion, Point> entry : coordenadas.entrySet()) {
            Interseccion inter = entry.getKey();
            Point p = entry.getValue();

            boolean tieneSede = inter.isHaySede();
            boolean tieneEmergencia = inter.isTieneEmergencia();

            if (tieneEmergencia) {
                int tam = 28;
                int[] xPoints = { p.x, p.x - tam / 2, p.x + tam / 2 };
                int[] yPoints = { p.y - tam / 2, p.y + tam / 2, p.y + tam / 2 };

                g2d.setColor(new Color(239, 68, 68)); 
                g2d.fillPolygon(xPoints, yPoints, 3);
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawPolygon(xPoints, yPoints, 3);

                g2d.setColor(new Color(185, 28, 28));
                g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
                g2d.drawString(inter.getId() + " (!)", p.x - 14, p.y + 26);

            } else if (tieneSede) {
                int tam = 28;
                if ("Hospital".equalsIgnoreCase(inter.getTipoSede())) {
                    g2d.setColor(new Color(244, 63, 94)); 
                } else if ("Bomberos".equalsIgnoreCase(inter.getTipoSede())) {
                    g2d.setColor(new Color(249, 115, 22)); 
                } else {
                    g2d.setColor(new Color(59, 130, 246)); 
                }

                g2d.fillRoundRect(p.x - tam / 2, p.y - tam / 2, tam, tam, 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(p.x - tam / 2, p.y - tam / 2, tam, tam, 10, 10);

                g2d.setColor(new Color(30, 41, 59));
                g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
                g2d.drawString(inter.getId(), p.x - 9, p.y + 26);
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 11));
                g2d.drawString(inter.getTipoSede(), p.x + 18, p.y + 4);

            } else {
                int tam = 18;
                g2d.setColor(new Color(100, 116, 139)); // Gris pizarra suave
                g2d.fillOval(p.x - tam / 2, p.y - tam / 2, tam, tam);
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(p.x - tam / 2, p.y - tam / 2, tam, tam);

                g2d.setColor(new Color(51, 65, 85));
                g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
                g2d.drawString(inter.getId(), p.x - 8, p.y + 22);
            }
        }

       
        for (Map.Entry<String, Point> unidad : unidadesMoviles.entrySet()) {
            Point p = unidad.getValue();
            Color color = coloresUnidades.getOrDefault(unidad.getKey(), Color.BLACK);

            // Halo exterior
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 70));
            g2d.fillOval(p.x - 13, p.y - 13, 26, 26);

            // Vehículo
            g2d.setColor(color);
            g2d.fillOval(p.x - 8, p.y - 8, 16, 16);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(p.x - 8, p.y - 8, 16, 16);
        }
    }
}