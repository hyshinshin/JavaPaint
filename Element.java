/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.Vector;

public abstract class Element extends SerializableBasicStroke implements Serializable{
    protected Color color;
    protected Color bgcolor;
    protected boolean highlight = false;
    protected Point position;
    protected double scaleX = 1.0, scaleY = 1.0 ;
    final static Point originPoint = new Point(0, 0);
    protected SerializableBasicStroke stroke;
    protected double angle = 0.0;
    
    public Element(Color color){
        this.color = color;
    }
    
    public Element(Color color, Color bgcolor){
        this.color = color;
        this.bgcolor = bgcolor;
    }
    
    public Point getPosition(){
        return position;
    }
    public Color getColor(){
        return color;
    }
    public boolean gethighlight(){
        return highlight;
    }
    
    public void sethighlight(boolean highlight){
        this.highlight = highlight;
    }
    
    protected void draw(Graphics2D g2D, Shape element){
        if(element.getClass().getSimpleName().equals("GeneralPath")){
            g2D.setPaint(gethighlight() ? new Color(0,255,255) : getColor());
            AffineTransform oldTrans = g2D.getTransform();
            g2D.translate(position.x, position.y);
            g2D.rotate(angle);
            g2D.scale(scaleX, scaleY);
            g2D.setStroke(stroke);
            g2D.draw(element);
            g2D.setTransform(oldTrans);
       }else {
            g2D.setPaint(gethighlight() ? new Color(0,255,255) : getColor());
            AffineTransform oldTrans = g2D.getTransform();
            g2D.translate(position.x, position.y);
            g2D.rotate(angle);
            g2D.scale(scaleX, scaleY);
            g2D.setStroke(stroke);
            g2D.draw(element);
            g2D.setPaint(bgcolor);
            g2D.fill(element);
            g2D.setTransform(oldTrans);
        }
    }
    
    public Rectangle getBounds(Rectangle bounds){
        AffineTransform bound = AffineTransform.getTranslateInstance(position.x, position.y);
        bound.rotate(angle);
        return bound.createTransformedShape(bounds).getBounds();
    };
    
    public void Move(int x, int y){
        position.x += x;
        position.y += y;
    }
    
    public void Rotate(double angle){
        this.angle += angle;
    }
    public void Scale(double x, double y){
        scaleX = x;
        scaleY = y;
    }
    protected void draw(Graphics2D g2D){};
    public abstract Shape getShape();
    public abstract Rectangle getBounds();
    public abstract void modify(Point start, Point end);
}
