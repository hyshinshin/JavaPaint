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

public class Triangle extends Element {
    private Polygon triangle;
    private Point start;
    private int x[] = new int[3];
    private int y[] = new int[3];
    
    public Triangle(Point start, Point end, Color color, Color bgcolor, int thickness) {
        super(color, bgcolor);
        stroke = new SerializableBasicStroke(thickness, BasicStroke.CAP_ROUND,0);
        this.position = new Point(Math.min(start.x,end.x), Math.min(start.y,end.y));
        this.start = start;
        x[0] = (int)((originPoint.x + end.x)/2);
        y[0] = originPoint.y;
        
        x[1] = originPoint.x;
        y[1] = end.y;
        
        x[2] = end.x;
        y[2] = end.y;
        triangle = new Polygon(x, y, 3);
    }
   
    protected void draw(Graphics2D g2D){
        draw(g2D, triangle);
    }
    public Shape getShape(){
        return triangle;
    }
    public Rectangle getBounds() {
      return getBounds(triangle.getBounds());
    }

    public void modify(Point start, Point last) {
        x[0] = (int)((start.x + last.x)/ 2);
        y[0] = start.y;
        
        x[1] = start.x;
        y[1] = last.y;
        
        x[2] = last.x;
        y[2] = last.y;
        triangle.xpoints = x;
        triangle.ypoints = y;
    }
}
