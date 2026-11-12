
import Modelo.Interseccion;
import Modelo.Calle;
import Estructuras.Grafo;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelMapa extends JPanel {

    private Grafo grafo;
    private Map<Interseccion, Point> coordenadas = new HashMap<>();
    public PanelMapa() {
        setPreferredSize(new Dimension(600, 350));
        setBackground(Color.WHITE); 
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
        inicializarCoordenadasAutomaticas();
        repaint();
    }

    private void inicializarCoordenadasAutomaticas() {
        coordenadas.clear();
        if (grafo == null) return;

        int x = 80, y = 80;
        int maxX = 500; 

        for (Interseccion interseccion : grafo.listaAdyacencia().keySet()) {
            coordenadas.put(interseccion, new Point(x, y));
            x += 150; 
            if (x > maxX) {
                x = 80;
                y += 120; 
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (grafo == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Map<Interseccion, List<Calle>> adyacencia = grafo.listaAdyacencia();

      
        g2d.setStroke(new BasicStroke(2));
        for (Map.Entry<Interseccion, List<Calle>> entry : adyacencia.entrySet()) {
            Interseccion origen = entry.getKey();
            Point pOrigen = coordenadas.get(origen);
            
            if (pOrigen == null) continue;

            for (Calle calle : entry.getValue()) {
                Interseccion destino = calle.getdestino();
                Point pDestino = coordenadas.get(destino);

                if (pDestino != null) {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.drawLine(pOrigen.x, pOrigen.y, pDestino.x, pDestino.y);

                    g2d.setColor(Color.DARK_GRAY);
                    int midX = (pOrigen.x + pDestino.x) / 2;
                    int midY = (pOrigen.y + pDestino.y) / 2;
                    g2d.drawString(String.valueOf(calle.getpesoRuta()), midX, midY);
                }
            }
        }

     
        int radio = 35;
        for (Map.Entry<Interseccion, Point> entry : coordenadas.entrySet()) {
            Interseccion inter = entry.getKey();
            Point p = entry.getValue();

            if (inter.gethaySede()) {
                if ("Hospital".equalsIgnoreCase(inter.gettipoSede())) {
                    g2d.setColor(Color.RED);
                } else if ("Bomberos".equalsIgnoreCase(inter.gettipoSede())) {
                    g2d.setColor(Color.ORANGE);
                } else {
                    g2d.setColor(Color.BLUE);
                }
            } else {
                g2d.setColor(Color.DARK_GRAY);
            }

            g2d.fillOval(p.x - radio / 2, p.y - radio / 2, radio, radio);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(p.x - radio / 2, p.y - radio / 2, radio, radio);

            g2d.setColor(Color.WHITE);
            g2d.drawString(inter.getid(), p.x - 10, p.y + 5);
        }
    }
}