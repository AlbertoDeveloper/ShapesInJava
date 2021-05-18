/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gesco_tarea.pkg1_unidad4_traslaescalarota_ruiz_alberto;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Ruiz
 */
public class Shapes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("TraslaEscalaRota_Ruiz_Alberto");
        PolygonPanel objPolygonPanel =new PolygonPanel();
        
        mainFrame.add(objPolygonPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
}
