/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import java.util.Observer;
import java.util.Observable;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

public class SketchModel extends Observable implements Serializable{
    protected LinkedList elementList = new LinkedList();
    public boolean remove(Element shape){
        boolean removed = elementList.remove(shape);
        System.out.println(removed);
        if(removed){
            setChanged();
            notifyObservers(shape.getBounds());
        }
        return removed;
    }
    
    public void add(Element shape){
        elementList.add(shape);
        setChanged();
        notifyObservers(shape.getBounds());
    }
    public Iterator getIterator(){
        return elementList.listIterator();
    }
}



