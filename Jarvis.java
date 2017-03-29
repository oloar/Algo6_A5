import Dessin.*;
import java.awt.Color;
import java.util.*;

public class Jarvis {
    static final double PRECISION=1E-6;

    static Point choose(List<Point> l, List<Point>h) {
        Random r = new Random();
        Point p;
        do {
            int i = r.nextInt(l.size());
            p = l.get(i);
        } while(h.contains(p));
        return p;
    }

    static boolean isOnRight(Point c, Point a, Point b) {
        int xAB, xAC, yAB, yAC;

        xAB = b.x - a.x;
        yAB = b.y - a.y;
        xAC = c.x - a.x;
        yAC = c.y - a.y;
        return ((xAB*yAC - xAC*yAB) < 0);
    }

    public static void main(Fenetre f, String[] args) {
        Random r = new Random();
        List<Point> l = new LinkedList<Point>();
        int nbPoints, xMin;

        List<Point> hull = new LinkedList<Point>();
        Point   pI = new Point(0,0), // Point initial
                pC, // Point courant
                pE; // Point etudie


        // Get number of points in the graph
        if (args.length > 0) {
            nbPoints = Integer.parseInt(args[0]);
        }
        else {
            nbPoints = 5;
        }
        // Init Graph
        xMin = f.largeur();
        for (int i = 0; i<nbPoints; i++) {
            int x = r.nextInt(f.largeur()-20)+10;
            int y = r.nextInt(f.hauteur()-20)+10;
            if (x < xMin) {
                xMin = x;
                pI = new Point(x, y);
            }
            Point pTmp = new Point(x, y);
            l.add(pTmp);
            f.tracerSansDelai(pTmp);
        }

        // Jarvis
        pC = pI;
        do {
            hull.add(pC);
            pE = choose(l,hull);
            for (int i = 0; i < l.size(); i++) {
                Point p = l.get(i);
                f.tracer(new Segment(pC.x, pC.y, p.x, p.y, Color.red));
                if (isOnRight(p,pC,pE)) {
                    f.effacer(new Segment(pC.x, pC.y, p.x, p.y, Color.red));
                    f.effacer(new Segment(pC.x, pC.y, pE.x, pE.y, Color.black));
                    pE = p;
                    f.tracer(new Segment(pC.x, pC.y, p.x, p.y, Color.black));
                }
                else {
                    f.effacer(new Segment(pC.x, pC.y, p.x, p.y, Color.red));
                }
            }
            f.tracer(new Segment(pC.x, pC.y, pE.x, pE.y, Color.black));
            pC = pE;
        } while (pC.x != pI.x && pC.y != pI.y);
    }
}