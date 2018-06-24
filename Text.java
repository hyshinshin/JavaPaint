/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Text extends Element{
    private Font font;
    private String text;
    java.awt.Rectangle bounds;
    
    public Text(Font font, String text, Point position, Color color, Color bgcolor, Rectangle bounds){
        
        super(color, bgcolor);
        this.font = font;
        this.text = text;
        this.position = position;
        this.position.y -= (int)bounds.getHeight();
        this.bounds = new Rectangle(originPoint.x, originPoint.y, bounds.width, bounds.height);
    }
    @Override
    public Shape getShape(){  
        return null;
    }
    
    @Override
    public Rectangle getBounds(){
        return getBounds(bounds);
    }
    
    @Override
    protected void draw(Graphics2D g2D){
        g2D.setPaint(gethighlight() ? new Color(0,255,255) : color);
        Font oldFont = g2D.getFont();
        g2D.setFont(font);
        AffineTransform oldTrans = g2D.getTransform();
        g2D.translate(position.x, position.y);
        g2D.rotate(angle);
        g2D.drawString(text, originPoint.x, originPoint.y + (int)bounds.getHeight());
        g2D.setTransform(oldTrans);
        g2D.setFont(oldFont);
    }
    
    @Override
    public void modify(Point start, Point last) {
        // not needed
    }
}
