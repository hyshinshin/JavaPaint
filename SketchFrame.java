/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.*;
import java.awt.print.PrinterJob;
import javax.print.PrintService;
import javax.print.event.PrintJobEvent;

public class SketchFrame extends JFrame implements Constants, Observer{
    private Sketcher theApp;
    private JToolBar toolBar = new JToolBar();
    private JMenuBar menuBar = new JMenuBar();     // Window menu bar
    private Status statusBar = new Status();
    private JPanel pane = new JPanel();
    private JPopupMenu popup = new JPopupMenu("general");
    JTextField thicknessControl_tf; // TextFiled
    private String texts;
    private int fontSize;
    private String frameTitle;               
    private String filename = DEFAULT_FILENAME; 
    private File modelFile;
    private Color elementColor = DEFAULT_ELEMENT_COLOR;     // Current element color
    private Color bgColor = DEFAULT_BACKGROUND_COLOR;
    private int elementType = DEFAULT_ELEMENT_TYPE;         // Current element type 
    private Font font = DEFAULT_FONT;
    private boolean sketchChanged = false;
    private JFileChooser files;

    private JLabel thicknessInfo_label, bgcolorInfo_label, shapecolorInfo_label;
    private FileAction newAction, openAction, saveAction, saveAsAction, printAction;
    private TypeAction lineAction, rectangleAction, circleAction, pencilAction, rhombusAction, triangleAction, textAction, ellipseAction, 
            eraseAction, defaultcolorAction, defaultbgcolorAction;
    private ColorAction redAction, yellowAction, greenAction, blueAction;
    private bgColorAction bgredAction, bgyellowAction, bggreenAction, bgblueAction;
    
    public SketchFrame(String title, Sketcher theApp) {
        this.theApp = theApp;
        setJMenuBar(menuBar);                        // Add the menu bar to the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pane.setLayout(new GridLayout(2,1));
        pane.add(toolBar, BorderLayout.NORTH);
        pane.add(statusBar, BorderLayout.SOUTH);
        getContentPane().add(BorderLayout.NORTH, pane);
        frameTitle = title + ": ";
        setTitle(frameTitle + filename);
        getContentPane().setBackground(Color.white);
        files = new JFileChooser(DEFAULT_DIRECTORY);
        if(!DEFAULT_DIRECTORY.exists()){
            if(!DEFAULT_DIRECTORY.mkdirs()){
                JOptionPane.showMessageDialog(this, "Error Directory", "Creating Directory Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        toolBar.setLayout(new FlowLayout());
        
        JMenu fileMenu = new JMenu("File");          // Create File menu
        JMenu elementMenu = new JMenu("Elements");   // Create Elements menu
        toolBar.setFloatable(false);
        fileMenu.setMnemonic('F');
        thicknessInfo_label = new JLabel("굵기"); 
        thicknessInfo_label.setFont(new Font("함초롬돋움", Font.BOLD, 20));
        bgcolorInfo_label = new JLabel("배경색"); 
        bgcolorInfo_label.setFont(new Font("함초롬돋움", Font.BOLD, 20));        
        shapecolorInfo_label = new JLabel("선색"); 
        shapecolorInfo_label.setFont(new Font("함초롬돋움", Font.BOLD, 20));       
        thicknessControl_tf = new JTextField("10", 5);
        thicknessControl_tf.setHorizontalAlignment(JTextField.CENTER); 
        thicknessControl_tf.setFont(new Font("궁서체", Font.PLAIN, 25)); 
        thicknessControl_tf.setPreferredSize(new Dimension(100,20));
        
        newAction = new FileAction("New", KeyStroke.getKeyStroke('N', Event.CTRL_MASK), "Start New Paint");
        openAction = new FileAction("Open", KeyStroke.getKeyStroke('O',Event.CTRL_MASK ), "Open existing Paint");
        saveAction = new FileAction("Save", KeyStroke.getKeyStroke('S',Event.CTRL_MASK ), "Save Paint");
        saveAsAction = new FileAction("Save As...");
        printAction = new FileAction("Print", KeyStroke.getKeyStroke('P',Event.CTRL_MASK ), "Print Paint");
        
        addMenuItem(fileMenu, newAction);
        addMenuItem(fileMenu, openAction);
        fileMenu.addSeparator();
        addMenuItem(fileMenu, saveAction);
        addMenuItem(fileMenu, saveAsAction);
        fileMenu.addSeparator();
        addMenuItem(fileMenu, printAction);
    
        toolBar.addSeparator();
        addToolBarButton(lineAction = new TypeAction("Line", LINE, "Draws a line"));
        addToolBarButton(rectangleAction = new TypeAction("Rectangle", RECTANGLE, "Draws rectangle"));
        addToolBarButton(circleAction = new TypeAction("Circle", CIRCLE, "Draws Circle"));
        addToolBarButton(ellipseAction = new TypeAction("Ellipse", ELLIPSE, "Draws Ellipsee"));
        addToolBarButton(triangleAction = new TypeAction("Triangle", TRIANGLE, "Draws Triangle"));
        addToolBarButton(rhombusAction = new TypeAction("Rhombus", RHOMBUS, "Draws Rhombus"));
        addToolBarButton(textAction = new TypeAction("Text", TEXT, "Writes Text"));
        
        toolBar.addSeparator();
        toolBar.add(shapecolorInfo_label);
        addToolBarButton(defaultcolorAction = new TypeAction("색선택", SELECT, null));
        addToolBarButton(redAction = new ColorAction("red", Color.RED, null));
        addToolBarButton(yellowAction = new ColorAction("yellow", Color.YELLOW, null));
        addToolBarButton(greenAction = new ColorAction("green", Color.GREEN, null));
        addToolBarButton(blueAction = new ColorAction("blue", Color.BLUE, null));
    
        toolBar.addSeparator();        
        toolBar.add(thicknessInfo_label);
        toolBar.add(thicknessControl_tf);
        addToolBarButton(pencilAction = new TypeAction("pen", CURVE, "Draws Curve"));
        addToolBarButton(eraseAction = new TypeAction("eraser", ERASE, "Erase things"));
        
        toolBar.addSeparator();
        toolBar.add(bgcolorInfo_label);
        addToolBarButton(defaultbgcolorAction = new TypeAction("색선택", BGSELECT, null));
        addToolBarButton(bgredAction = new bgColorAction("red", Color.RED, null));
        addToolBarButton(bgyellowAction = new bgColorAction("yellow", Color.YELLOW, null));
        addToolBarButton(bggreenAction = new bgColorAction("green", Color.GREEN, null));
        addToolBarButton(bgblueAction = new bgColorAction("blue", Color.BLUE, null));
        
        toolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.darkGray), BorderFactory.createEmptyBorder(2,2,4,2)));

        
        popup.add(lineAction);
        popup.add(pencilAction);
        popup.add(rectangleAction);
        popup.add(circleAction);
        popup.add(ellipseAction);
        popup.add(triangleAction);
        popup.add(rhombusAction);
        popup.add(textAction);
        popup.addSeparator();
        popup.add(shapecolorInfo_label);
        popup.add(defaultcolorAction);
        popup.add(redAction);
        popup.add(yellowAction);
        popup.add(greenAction);
        popup.add(blueAction);
        
        menuBar.add(fileMenu);                       // Add the file menu
        menuBar.add(elementMenu);                    // Add the element menu    
    }
    
    private JMenuItem addMenuItem(JMenu menu, Action action){
        JMenuItem item = menu.add(action);
      
        KeyStroke keystroke = (KeyStroke)action.getValue(action.ACCELERATOR_KEY);
        if(keystroke != null){
            item.setAccelerator(keystroke);
        }
        item.setIcon(null);
        return item;
    }
    private JButton addToolBarButton (Action action){
        JButton button = toolBar.add(action);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }
    private File showDialog(String dialogTitle, String ButtonText, String Tooltip, char Mnemonic, File file){
        files.setDialogTitle(dialogTitle);
        files.setApproveButtonText(ButtonText);
        files.setApproveButtonToolTipText(Tooltip);
        files.setApproveButtonMnemonic(Mnemonic);
        files.setFileSelectionMode(JFileChooser.FILES_ONLY);
        files.rescanCurrentDirectory();
        files.setSelectedFile(file);
        ExtensionFilter sketchFilter = new ExtensionFilter(".ske", "Sketch files (*.ske)");
        files.addChoosableFileFilter(sketchFilter);
        
        int result = files.showDialog(SketchFrame.this, null); 
        return (result == JFileChooser.APPROVE_OPTION) ? files.getSelectedFile() : null;
    }
    private void saveOperation() {
        if(!sketchChanged)
            return;
        File file = modelFile;
        if(file == null) {
            file = showDialog("Save Sketch", "Save", "Save the sketch", 's', new File(files.getCurrentDirectory(), filename));
            if(file == null ||  (file.exists() && JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(SketchFrame.this, 
                file.getName()+ " exists. Overwrite?", "Confirm Save As", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)))
            return; 
        }
        saveSketch(file);
    }
    private void saveSketch(File outFile){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(outFile)));
            out.writeObject(theApp.getModel());
            out.close();
        }catch(IOException e){
            System.err.println(e);
            JOptionPane.showMessageDialog(SketchFrame.this, "Write Error", "Writing Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(outFile != modelFile){
            modelFile = outFile;
            filename = modelFile.getName();
            setTitle(frameTitle + modelFile.getPath());
        }
        sketchChanged = false;
    }
    
    
    @Override
    public void update(Observable o, Object obj){
        sketchChanged = true;
    }
    
    public void openSketch(File inFile){
        try{
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(inFile)))) {
                theApp.insertModel((SketchModel)in.readObject());
            }
            modelFile = inFile;
            filename = modelFile.getName();
            setTitle(frameTitle+modelFile.getPath());
            sketchChanged = false;
        }catch(IOException | ClassNotFoundException e ){
            System.out.println(e);
        } 
    }

    @Override
    public Font getFont(){
        return font;
    }
    public void checkForSave(){
        if(sketchChanged){
            if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(SketchFrame.this, "Current file has changed. Save current file?", 
                    "Confirm Save Current File", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE))
                saveOperation();
        }
    }



    class FileAction extends AbstractAction{
        FileAction(String name){
              super(name);
         }
        FileAction(String name, KeyStroke keystroke, String tooltip){
            this(name);
            if(keystroke != null){
                putValue(ACCELERATOR_KEY, keystroke);
            }
            if(tooltip != null){
                putValue(SHORT_DESCRIPTION, tooltip);
            }
        }
        @Override
        public void actionPerformed(ActionEvent e){
            String name = (String)getValue(NAME);
            if(name.equals(saveAction.getValue(NAME))){
                saveOperation();
            }else if(name.equals(saveAsAction.getValue(NAME))){
                File file = showDialog("Save Sketch as", "Save", "Save the Sketch", 's', modelFile == null ? new File(files.getCurrentDirectory(), filename):modelFile);
                if(file != null){
                    if(file.exists() && !file.equals(modelFile))
                        if(JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(SketchFrame.this, file.getName()+" exists. Overwrite?", 
                                "Confirm Save As",  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE))
                            return;
                    saveSketch(file);
                }
            }else if(name.equals(openAction.getValue(NAME))){
                checkForSave();
                File file = showDialog("Open sketch file", "Open", "Read a sketch from file", 'o', null);
                if(file != null)
                    openSketch(file);
            }else if(name.equals(newAction.getValue(NAME))){
                checkForSave();
                theApp.insertModel(new SketchModel());
                modelFile = null;
                setTitle(frameTitle + files.getCurrentDirectory() + "+" + filename);
                sketchChanged = false;
            }else if(name.equals(printAction.getValue(NAME))){
               
                PrinterJob print = PrinterJob.getPrinterJob();
                PrintService printer = print.getPrintService();
                if(printer == null){
                    JOptionPane.showMessageDialog(SketchFrame.this, "No printer", "Print error!!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
//                print.setPrintable(theApp.getView());
                if(print.printDialog()){
                    try{
                        print.print();
                    }catch(PrinterException pe){
                        System.err.println(pe);
                        JOptionPane.showMessageDialog(SketchFrame.this, "Printing Error", "Cannot Print!!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
  
    class TypeAction extends AbstractAction{
        String iconFileName;
        private int typeID;
        TypeAction(String name){
            super(name);
        }
        TypeAction(String name, int TypeID, String tooltip){
            this(name);
            this.typeID = TypeID;
            iconFileName = "Images/" + name + ".png";
            if(new File(iconFileName).exists())
                putValue(SMALL_ICON, new ImageIcon(iconFileName));
            if (tooltip != null){
                putValue(SHORT_DESCRIPTION, tooltip);
            }
        }
        @Override
        public void actionPerformed(ActionEvent e){
            if(typeID == SELECT){
                JColorChooser chooser = new JColorChooser();
                elementColor = chooser.showDialog(null, "Color", Color.ORANGE);
                statusBar.setColor(elementColor);
            }else if(typeID == BGSELECT){
                JColorChooser chooser = new JColorChooser();
                bgColor = chooser.showDialog(null, "Color", Color.ORANGE);
                statusBar.setbgColor(bgColor);
            }else if(typeID == TEXT){
                elementType = typeID;
                texts = JOptionPane.showInputDialog(null, "Enter Text:", "Dialog for Text Element", JOptionPane.PLAIN_MESSAGE);
            }else if(typeID == CURVE){
                elementType = typeID;
                statusBar.setType(typeID);
            }else if(typeID == ERASE){
                elementType = typeID;
                statusBar.setType(typeID);
            }else{
                elementType = typeID;
                statusBar.setType(typeID);               
            }
        }
    }
  
  
    class ColorAction extends AbstractAction{
        private Color color;
        String iconFileName;
        ColorAction(String name, Color color, String tooltip){
            super(name);
            this.color = color;
            iconFileName = "./Images/" + name + ".png";
            if(new File(iconFileName).exists())
                putValue(SMALL_ICON, new ImageIcon(iconFileName));
            if(tooltip != null){
                putValue(SHORT_DESCRIPTION, tooltip);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e){
            elementColor = color;
            statusBar.setColor(color);
        
        }
    }
    
    class bgColorAction extends AbstractAction{
        private Color color;
        String iconFileName;
        bgColorAction(String name, Color color, String tooltip){
            super(name);
            this.color = color;
            iconFileName = "./Images/" + name + ".png";
            if(new File(iconFileName).exists())
                putValue(SMALL_ICON, new ImageIcon(iconFileName));
            if(tooltip != null){
                putValue(SHORT_DESCRIPTION, tooltip);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e){
            bgColor = color;
            statusBar.setbgColor(color);
        
        }
    }
    
    public int getThickness(){
        return Integer.parseInt(thicknessControl_tf.getText());
    }
    
    public String getTexts(){
        return texts;
    }
    public int getFontSize(){
        return fontSize;
    }
    
    public Color getbgColor(){
        return bgColor;
    }
   
    public Color getElementColor(){
        return elementColor;
    }
    
    public int getElementType(){
        return elementType;
    }
    public JPopupMenu getPopup(){
        return popup;
    }

}