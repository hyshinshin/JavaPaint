/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

public class Line extends Element {
    private Line2D.Double line;
    public Line(Point start, Point end, Color color, Color bgcolor, int thickness) {
        super(color, bgcolor);
        stroke = new SerializableBasicStroke(thickness, BasicStroke.CAP_ROUND,0);
        position = start;
        line = new Line2D.Double(originPoint, new Point(end.x - position.x, end.y-position.y));
    }
    public Shape getShape(){
        return line;
    }
    
    public void draw(Graphics2D g2D){
        draw(g2D, line);
    }
    
    public Rectangle getBounds() {
      return getBounds(line.getBounds());
    }

    public void modify(Point start, Point last) {
      line.x2 = last.x - position.x;
      line.y2 = last.y - position.y;
    }
   private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeDouble(line.x2);
        out.writeDouble(line.y2);
    }
     private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        double x2 = in.readDouble();
        double y2 = in.readDouble();
        line = new Line2D.Double(0,0,x2,y2);
    }
}
