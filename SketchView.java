/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.*;

public class SketchView extends JComponent implements Observer, Constants, ActionListener {

    private JPopupMenu elementPopup = new JPopupMenu("Element");
    private JMenuItem moveItem, deleteItem, rotateItem, scaleItem, sendToBackItem;
    private Sketcher theApp;
    private Element highlightElement, selectedElement;
    private int mode = NORMAL;

    public SketchView(Sketcher theApp) {
        this.theApp = theApp;
        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
        
        moveItem = elementPopup.add("Move");
        scaleItem = elementPopup.add("Scale");
        deleteItem = elementPopup.add("Delete");
        rotateItem = elementPopup.add("Rotate");
        sendToBackItem = elementPopup.add("Send back");
        moveItem.addActionListener(this);
        scaleItem.addActionListener(this);
        deleteItem.addActionListener(this);
        rotateItem.addActionListener(this);
        sendToBackItem.addActionListener(this);
    }
    class MouseHandler extends MouseInputAdapter {

        private Graphics2D g2D = null;
        private boolean button1pressed = false;
        private Point start;
        private Point end;
        private Element tempElement;
        
        private Element createElement(Point start, Point end) {
            switch (theApp.getWindow().getElementType()) {
                case LINE:
                    return new Line(start, end, theApp.getWindow().getElementColor(), theApp.getWindow().getbgColor(), theApp.getWindow().getThickness());
                case RECTANGLE:
                    return new Rect(start, end, theApp.getWindow().getElementColor(), theApp.getWindow().getbgColor(), theApp.getWindow().getThickness());
                case CIRCLE:
                    return new Circle(start, end, theApp.getWindow().getElementColor(), theApp.getWindow().getbgColor(), theApp.getWindow().getThickness());
                case ELLIPSE:
                    return new Ellipse(start, end,theApp.getWindow().getElementColor(), theApp.getWindow().getbgColor(), theApp.getWindow().getThickness());
                case CURVE:
                    return new Curve(start, end, theApp.getWindow().getElementColor(), theApp.getWindow().getbgColor(), theApp.getWindow().getThickness());
                case ERASE:
                    return new Eraser(start, end, theApp.getWindow().getElementColor(), theApp.getWindow().getbgColor(), theApp.getWindow().getThickness());
                case TRIANGLE:
                    return new Triangle(start, end, theApp.getWindow().getElementColor(), theApp.getWindow().getbgColor(), theApp.getWindow().getThickness());
                case RHOMBUS:
                    return new Rhombus(start, end, theApp.getWindow().getElementColor(), theApp.getWindow().getbgColor(), theApp.getWindow().getThickness());
            }
            return null;
        }

        public void mouseClicked(MouseEvent e){
            if((e.getButton() == MouseEvent.BUTTON1) && (theApp.getWindow().getElementType() == TEXT)){
                System.out.println("text");
                start = e.getPoint();
                if(theApp.getWindow().getTexts() != null){
                    g2D = (Graphics2D)getGraphics();
                    Text h = new Text(theApp.getWindow().getFont(), theApp.getWindow().getTexts(), start, theApp.getWindow().getElementColor(),theApp.getWindow().getbgColor(), 
                            new java.awt.font.TextLayout(theApp.getWindow().getTexts(), theApp.getWindow().getFont(), g2D.getFontRenderContext()).getBounds().getBounds());
                    if(h != null){
                        tempElement = h;         
                        theApp.getModel().add(tempElement);
                        repaint(); 
                        tempElement = null;
                        h = null;
                    }
                    g2D.dispose();
                    g2D = null; 
                    start = null;
                }
            }

        }
    
        public void mouseMoved(MouseEvent e) {
            Point currentCursor = e.getPoint();
            Iterator elements = theApp.getModel().getIterator();
            Element element = null;

            while (elements.hasNext()) {
                element = (Element) elements.next();
                if (element.getBounds().contains(currentCursor)) {
                    System.out.println(element.getClass().getSimpleName());
                    if (element == highlightElement) {
                        return;
                    }
                    g2D = (Graphics2D) getGraphics();
                    
                    if (highlightElement != null) {
                        highlightElement.sethighlight(false);
                        repaint();
                    }
                    element.sethighlight(true);
                    highlightElement = element;
                    repaint();
                    g2D.dispose();
                    g2D = null;
                    return;
                }
            }
            if (highlightElement != null) {
                g2D = (Graphics2D) getGraphics();
                highlightElement.sethighlight(false);
                highlightElement = null;
                repaint();
                g2D.dispose();
                g2D = null;
            }
        }

        public void mousePressed(MouseEvent e) {
             if ((button1pressed = (e.getButton() == MouseEvent.BUTTON1)) && (theApp.getWindow().getElementType() != TEXT)) {
                start = e.getPoint();
                g2D = (Graphics2D) getGraphics();
                g2D.setXORMode(getBackground());
                g2D.setPaint(theApp.getWindow().getElementColor());
            }else if ((button1pressed = (e.getButton() == MouseEvent.BUTTON1)) && (theApp.getWindow().getElementType() == TEXT)){
                start = e.getPoint();
            }
        }

        public void mouseDragged(MouseEvent e) {
            end = e.getPoint();
            if (button1pressed && (theApp.getWindow().getElementType() != TEXT) && (mode == NORMAL)) {
                if (tempElement == null) {
                    tempElement = createElement(start, end);
                }else {
                    tempElement.modify(start, end);
                    repaint();
                }
                tempElement.draw(g2D, tempElement.getShape());
            } else if(button1pressed && (mode == MOVE) && (selectedElement != null)){
                selectedElement.Move(end.x - selectedElement.position.x, end.y - selectedElement.position.y);
                repaint();
            }else if(button1pressed && (mode == ROTATE) && (selectedElement != null)){
                selectedElement.Rotate(getAngle(selectedElement.getPosition(), start, end));
                repaint();
                start = end;
            }else if(button1pressed && (mode == SCALE) && (selectedElement != null)){
                selectedElement.Scale((double)end.x/(double)selectedElement.position.x, (double)end.y/(double)selectedElement.position.y);
                repaint();
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                start = e.getPoint();
                if(highlightElement == null)
                    theApp.getWindow().getPopup().show((Component) e.getSource(), start.x, start.y);
                else
                    elementPopup.show((Component)e.getSource(), start.x, start.y);
            } else if ((e.getButton() == MouseEvent.BUTTON1) && (theApp.getWindow().getElementType() != TEXT) && (mode == NORMAL)) {
                button1pressed = false;
                if (tempElement != null) {
                    theApp.getModel().add(tempElement);
                }
            }else if((e.getButton() == MouseEvent.BUTTON1) && (mode == MOVE || mode == ROTATE || mode == SCALE)){
                button1pressed = false;
                if(selectedElement != null)
                    repaint();
                mode = NORMAL;
            }
            if(g2D != null){
                g2D.dispose();
                g2D = null;
            }
            start = end = null;
            selectedElement = tempElement = null;
        }
    }
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == moveItem){
            mode = MOVE;
            selectedElement = highlightElement;
            
        }else if(source == deleteItem){
            
            selectedElement = highlightElement;
            if(highlightElement != null){
                theApp.getModel().remove(highlightElement);
                highlightElement = null;
                repaint();
            }else{
                return;
            }
        }else if(source == rotateItem){
            mode = ROTATE;
            selectedElement = highlightElement;
        }else if(source ==scaleItem){
            mode = SCALE;
            selectedElement = highlightElement;
        }else if(source == sendToBackItem){
            if (highlightElement != null){
                theApp.getModel().remove(highlightElement);
                theApp.getModel().add(highlightElement);
                highlightElement.sethighlight(false);
                highlightElement = null;
                repaint();
            }
        }
    }
    
    public void update(Observable o, Object rectangle) {
        if (rectangle == null) {
            repaint();
        } else {
            repaint((Rectangle) rectangle);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Iterator elements = theApp.getModel().getIterator();
        Element element;
        
        while (elements.hasNext()) {
            element = (Element)elements.next();
            if(element.getClass().getCanonicalName().equals("finishedpaint.Text")){
                element.draw(g2D);
            }else{
                element.draw(g2D,(element.getShape()));
            }
        }
    }
    
    public double getAngle(Point position, Point start, Point end){
        double perpendicular = Line2D.ptLineDist(position.x, position.y, end.x, end.y, start.x, start.y);
        double potostart = position.distance(start);
        if (potostart == 0.0){
            potostart = 1.0;
        }
        return -Line2D.relativeCCW(position.x, position.y, start.x, start.y, end.x, end.y) * Math.asin(perpendicular / potostart);
    }
    
}
