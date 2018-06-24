/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.border.BevelBorder;

public class Status extends JPanel implements Constants{
    private StatusLabel statusColor = new StatusLabel("BLUE");
    private StatusLabel statusbgColor = new StatusLabel("WHITE");
    private StatusLabel statusType = new StatusLabel("LINE");
    private JLabel colorInfo_label, bgcolorInfo_label, typeInfo_label;
    
    public Status(){
        setLayout(new FlowLayout(FlowLayout.LEFT, 10,3));
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setColor(DEFAULT_ELEMENT_COLOR);
        setType(DEFAULT_ELEMENT_TYPE);
        colorInfo_label = new JLabel("선색"); 
        colorInfo_label.setFont(new Font("함초롬돋움", Font.BOLD, 20));
        bgcolorInfo_label = new JLabel("배경색"); 
        bgcolorInfo_label.setFont(new Font("함초롬돋움", Font.BOLD, 20));
        typeInfo_label = new JLabel("타입"); 
        typeInfo_label.setFont(new Font("함초롬돋움", Font.BOLD, 20));
        add(colorInfo_label);
        add(statusColor);
        add(bgcolorInfo_label);
        add(statusbgColor);
        add(typeInfo_label);
        add(statusType);
    }
    
    public void setColor(Color color) {
        String text = null;
        if((Color.yellow).equals(color)){ 
            text = "Yellow";
        }else if((Color.red).equals(color)){
            text = "Red";
        }else if((Color.green).equals(color)){
            text = "Green";
        }else if((Color.blue).equals(color)){
            text = "Blue";
        }else{
            text = "Default";
        }
        statusColor.setForeground(color);
        statusColor.setText(text);
        statusColor.setBackground(color);
    }
    public void setbgColor(Color color) {
        String text = null;  
        if((Color.yellow).equals(color)){ 
            text = "Yellow";
        }else if((Color.red).equals(color)){
            text = "Red";
        }else if((Color.green).equals(color)){
            text = "Green";
        }else if((Color.blue).equals(color)){
            text = "Blue";
        }else{
            text = "Default";
        }
        statusbgColor.setForeground(color);
        statusbgColor.setText(text);
        statusbgColor.setBackground(color);
    }
    
    public void setType(int elementType){
        String iconFileName = null;
        switch(elementType){
            case LINE:
                iconFileName = "LINE";
                break;
            case RECTANGLE:
                iconFileName = "RECTANGLE";
                break;
            case CIRCLE:
                iconFileName = "CIRCLE";
                break;
            case CURVE:
                iconFileName = "pen";
                break;
            case TEXT:
                iconFileName = "TEXT";
                break;
            case TRIANGLE:
                iconFileName = "TRIANGLE";
                break;
            case RHOMBUS:
                iconFileName = "RHOMBUS";
                break;
            case ELLIPSE:
                iconFileName = "ELLIPSE";
                break;
            case ERASE:
                iconFileName = "eraser";
        }
        statusType.setText(iconFileName);
        iconFileName = "Images/" + iconFileName + ".png";
        if(new File(iconFileName).exists()){
            statusType.setIcon(new ImageIcon(iconFileName));
        }
    }
    
    class StatusLabel extends JLabel{
        private Font labelFont = new Font("Arial", Font.PLAIN, 12);
        public StatusLabel(String text){
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.BLACK);
            setFont(labelFont);
            setHorizontalAlignment(CENTER);
            setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            setPreferredSize(new Dimension(100,20));
            setText(text);
        }
    }
}
