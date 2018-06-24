/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;
import static finishedpaint.Element.originPoint;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.*;
import java.io.*;

public class Rhombus extends Element {
    private Polygon rhombus;
    private int x[] = new int[4];
    private int y[] = new int[4];
    
    public Rhombus(Point start, Point end, Color color, Color bgcolor, int thickness) {
        super(color, bgcolor);
        stroke = new SerializableBasicStroke(thickness, BasicStroke.CAP_ROUND,0);
        position = new Point(Math.min(start.x,end.x), Math.min(start.y,end.y));
        
        x[0] = start.x;
        y[0] = (int)((start.y + end.y)/ 2);
        x[1] = (int)((start.x + end.x)/ 2);
        y[1] = end.y;
        x[2] = end.x;
        y[2] = (int)((start.y + end.y)/ 2);
        x[3] = (int)((start.x + end.x)/ 2);
        y[3] = start.y;
        rhombus = new Polygon(x, y, 4);
    }
   
    public void draw(Graphics2D g2D){
        draw(g2D, rhombus);
        
    }
    public Shape getShape(){
        return rhombus;
    }
    public Rectangle getBounds() {
      return getBounds(rhombus.getBounds());
    }

    public void modify(Point start, Point last) {
        x[0] = start.x;
        y[0] = (int)((start.y + last.y)/ 2);
        x[1] = (int)((start.x + last.x)/ 2);
        y[1] = last.y;
        x[2] = last.x;
        y[2] = (int)((start.y + last.y)/ 2);
        x[3] = (int)((start.x + last.x)/ 2);
        y[3] = start.y;
        rhombus.xpoints = x;
        rhombus.ypoints = y;
    }
}
