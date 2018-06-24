/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import java.awt.*;
import java.util.*;

public class Sketcher{
    private SketchModel sketch;        
    private SketchView view;     
    private Dimension winndowSize;
    static SketchFrame window;           
    static Sketcher theApp;       
    
    public static void main(String[] args){
        theApp = new Sketcher();
        theApp.init();
    }
    public void init(){
        window = new SketchFrame("Sketcher", this);
        Toolkit toolkit = window.getToolkit( );
        winndowSize = toolkit.getScreenSize();  
        window.setBounds(winndowSize.width/5, winndowSize.height/5, 2*winndowSize.width/4, 2*winndowSize.height/4);
        sketch = new SketchModel();
        view = new SketchView(this);
        sketch.addObserver((Observer)view);
        sketch.addObserver(window);
        window.getContentPane().add(view, BorderLayout.CENTER);
        window.setVisible(true);
    }
    
    public void insertModel(SketchModel newSketch){
        sketch = newSketch;
        sketch.addObserver((Observer)view);
        sketch.addObserver((Observer)window);
        view.repaint();
    }
 
    public Dimension getDimension(){
        return winndowSize;
    }
    
    public SketchFrame getWindow(){
        return window;
    }
    public SketchModel getModel(){
        return sketch;
    }
    public SketchView getView(){
        return view;
    }

}
