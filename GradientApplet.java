/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import javax.swing.JComponent;
import javax.swing.JApplet;
import java.awt.*;
import java.awt.geom.*;

public class GradientApplet extends JApplet {
  // Initialize the applet
  public void init() {
    getContentPane().add(pane);         // BorderLayout.CENTER is default position
  }

  // Class defining a pane on which to draw
  class GradientPane extends JComponent {
    public void paint(Graphics g) {
      Graphics2D g2D = (Graphics2D)g;

      Point2D.Float p1 = new Point2D.Float(150.f, 75.f);    // Gradient line start
      Point2D.Float p2 = new Point2D.Float(250.f, 75.f);    // Gradient line end
      float width = 300;
      float height = 50;
      GradientPaint g1 = new GradientPaint(p1, Color.white,
                                           p2, Color.DARK_GRAY
                                           );           // Cyclic gradient
      Rectangle2D.Float rect1 = new Rectangle2D.Float(
                                   p1.x-100, p1.y-25, width,height);
      g2D.setPaint(g1);                                     // Gradient color fill
      g2D.fill(rect1);                                      // Fill the rectangle
      g2D.setPaint(Color.black);                            // Outline in black
      g2D.draw(rect1);                                      // Fill the rectangle
      g2D.draw(new Line2D.Float(p1, p2));
      g2D.drawString("Cyclic Gradient Paint", p1.x-100, p1.y-50);
      g2D.drawString("p1", p1.x-20, p1.y);
      g2D.drawString("p2", p2.x+10, p2.y);
    }
  }

  GradientPane pane = new GradientPane();  // Pane containing filled rectangles
}