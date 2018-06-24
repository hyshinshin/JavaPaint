/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.*;
public class Curve extends Element{
    private GeneralPath curve;
    public Curve(Point start, Point next, Color color, Color bgcolor, int thickness) {
        super(color);
        curve = new GeneralPath();
        position = start;
        stroke = new SerializableBasicStroke(thickness, BasicStroke.CAP_ROUND,0);
        curve.moveTo(originPoint.x, originPoint.y);
        curve.lineTo(next.x - position.x, next.y - position.y);
    }
   
    private void writeObject(ObjectOutputStream out) throws IOException{
        PathIterator iterator = curve.getPathIterator(new AffineTransform());
        Vector coords = new Vector();
        int maxCount = 6;
        float[] temp = new float[maxCount];
        
        int result = iterator.currentSegment(temp);
        assert(result == iterator.SEG_MOVETO);
        iterator.next();
        while(!iterator.isDone()){
            result = iterator.currentSegment(temp);
            assert(result == iterator.SEG_MOVETO);
            coords.add(new Float(temp[0]));
            coords.add(new Float(temp[1]));
            iterator.next();
        }
        out.writeObject(coords);
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        Vector coords = (Vector)in.readObject();
        curve = new GeneralPath();
        curve.moveTo(0,0);
        float x, y;
        for(int i=0;i<coords.size() ; i+=2){
            x = ((Float) coords.get(i)).floatValue();
            y = ((Float) coords.get(i+1)).floatValue();
            curve.lineTo(x, y);
        }
    }
    public void draw(Graphics2D g2D){
        draw(g2D, curve);
    }
    
    public Shape getShape(){
        return curve;
    }
    
    public Rectangle getBounds() {
      return getBounds(curve.getBounds());
    }

    public void modify(Point start, Point next) {
        curve.lineTo(next.x - start.x, next.y - start.y);
    }
}