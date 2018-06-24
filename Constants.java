/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;
import java.awt.Color;
import java.awt.Font;
import java.io.*;

public interface Constants { 
  // Element type definitions
  int LINE      = 101;
  int CURVE     = 102;
  int RECTANGLE = 103;
  int CIRCLE    = 104;
  int ELLIPSE   = 105;
  int TRIANGLE  = 106;
  int RHOMBUS   = 107;
  int TEXT      = 108;
  int ERASE     = 109;
  int SELECT    = 110;
  int BGSELECT  = 111;
  
  int NORMAL = 1;
  int MOVE = 2;
  int ROTATE = 3;
  int SCALE = 4;
  File DEFAULT_DIRECTORY = new File("C:/Sketches");
  String DEFAULT_FILENAME = "Sketch.ske";
  
  // Initial conditions
  int DEFAULT_ELEMENT_TYPE = LINE;
  Color DEFAULT_ELEMENT_COLOR = Color.BLACK;
  Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
  Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 30);
}