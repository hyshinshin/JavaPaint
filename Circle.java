/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
public class Circle extends Element {
    private Ellipse2D.Double circle;
    public Circle(Point center, Point circum, Color color, Color bgcolor, int thickness) {
        super(color, bgcolor);
        double radius = center.distance(circum);
        stroke = new SerializableBasicStroke(thickness, BasicStroke.CAP_ROUND,0);
        position = new Point(center.x -(int)radius, center.y - (int)radius);
        circle = new Ellipse2D.Double(originPoint.x, originPoint.y, 2*radius, 2*radius);
    }

    public void draw(Graphics2D g2D){
        draw(g2D, circle);
    }
    
    public Shape getShape(){
        return circle;
    }
    
    public Rectangle getBounds() {
      return getBounds(circle.getBounds());
    }

    public void modify(Point center, Point circum) {
        double radius = center.distance(circum);
        position.x = center.x -(int)radius;
        position.y = center.y -(int)radius;
        circle.width = 2*radius;
        circle.height = 2*radius;
    }
    
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeDouble(circle.width);
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        double width = in.readDouble();
        circle = new Ellipse2D.Double(0,0,width,width);
    }
}