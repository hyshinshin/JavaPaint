/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ExtensionFilter extends FileFilter {
  public ExtensionFilter(String ext, String descr) {
    extension = ext.toLowerCase();                 // Store the extension as
                                                   // lower case
    description = descr;                           // Store the description
  }

  public boolean accept(File file) {
      return(file.isDirectory()||file.getName().toLowerCase().endsWith(extension));
  }

  public String getDescription() {
    return description;  
  }

  private String description;                  // Filter description
  private String extension;                    // File extension
}