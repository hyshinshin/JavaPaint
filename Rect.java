/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.*;
import java.io.*;

public class Rect extends Element {
    private Rectangle2D.Double rect;
    
    public Rect(Point start, Point end, Color color, Color bgcolor,int thickness) {
        super(color, bgcolor);
        stroke = new SerializableBasicStroke(thickness, BasicStroke.CAP_ROUND,0);
        position = new Point(Math.min(start.x,end.x), Math.min(start.y,end.y));
        rect = new Rectangle2D.Double(originPoint.x, originPoint.y, Math.abs(start.x-end.x), Math.abs(start.y-end.y));
    }
    public void draw(Graphics2D g2D){
        draw(g2D, rect);
        
    }
    public Shape getShape(){
        return rect;
    }
    public Rectangle getBounds() {
      return getBounds(rect.getBounds());
    }

    public void modify(Point start, Point last) {
        position.x =  Math.min(start.x,last.x);
        position.y = Math.min(start.y,last.y);
        rect.width = Math.abs(start.x-last.x);
        rect.height = Math.abs(start.y-last.y);
    }
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeDouble(rect.width);
        out.writeDouble(rect.height);
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        double width = in.readDouble();
        double height = in.readDouble();
        rect = new Rectangle2D.Double(0,0,width,height);
    }
}