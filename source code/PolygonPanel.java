/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gesco_tarea.pkg1_unidad4_traslaescalarota_ruiz_alberto;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ruiz
 */
public class PolygonPanel extends JPanel implements ActionListener{

    private JPanel panelButton, panelSlider, panelMain, panelMovement, panelSizePosition, panelRotate, panelBelow;
    private Color selectBackgroundColor, selectGridColor, selectLineColor, selectFillColor;
    private JButton buttonBackground, buttonGrid, buttonFill, buttonLine, buttonClean;
    private JButton buttonUp, buttonDown, buttonLeft, buttonRight;
    private JButton buttonOffsetSize, buttonRotate90, buttonRotate180, buttonRotate270, buttonRotate360;
    private JTextField fieldPolySize, fieldOffsetX, fieldOffsetY;
    private final JCheckBox checkFill;
    private JLabel labelPoints, labelThickness, labelStyle, labelPolySize, labelOffsetX, labelOffsetY, labelPolyPosition;
    private JComboBox boxPoints, boxStyle, boxThickness;
    private boolean flagFill = true, flagDrawPoly = false, flagFirstDraw = true, flagClean = false, flagMirror, flagMove = true, flagRotate = false;
    private String selectPoints, selectStyles, selectThickness;
    private final String[] listPoints = {"3","4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};
    private final String[] listStyle = {"Continuo", "Discontinuo", "Punteado"};
    private final String[] listThickness = {"Bajo", "Medio", "Alto"};        
    private int R = 200;
    private float transparency = 0.35f;
    private int moveIncrementX = 0, moveIncrementY = 0, moveCounter = 0;
    public int numberPoints = 3, thicknessValue = 2, mirrorSpace;
    private int offsetX = 480, offsetY = 325, valueSize = 200, valueRotate;
    public int[] polyX, mirrorPolyX, updatePolyX;
    public int[] polyY, mirrorPolyY, updatePolyY;
    private Stroke defaultStroke;
    private Composite defaultComp;
    private AffineTransform defaultTransform;
    
    public PolygonPanel() {        
        //Colors for the polygon, grid, background
        selectBackgroundColor = new Color(40, 44, 52);
        selectGridColor = new Color(161, 82, 219);
        selectLineColor = new Color(225, 185, 110);
        selectFillColor = new Color(83, 152, 212);
        
        setPreferredSize(new Dimension(950, 700));
        setLayout(new BorderLayout());
        
        //Elementos de UI        
        panelMain = new JPanel();
        panelButton = new JPanel();
        panelSlider = new JPanel();
        panelMovement = new JPanel();
        panelSizePosition = new JPanel();
        panelRotate = new JPanel();
        panelBelow = new JPanel();
        
        panelMain.setLayout(new BorderLayout());
        panelButton.setLayout(new FlowLayout());
        panelSlider.setLayout(new FlowLayout()); 
        panelMovement.setLayout(new FlowLayout());
        panelSizePosition.setLayout(new FlowLayout());
        panelRotate.setLayout(new FlowLayout());
        panelBelow.setLayout(new BorderLayout());
        
        buttonBackground = new JButton("Color de fondo");
        buttonGrid = new JButton("Color de cuadrícula");
        buttonFill = new JButton("Color de relleno");
        buttonLine = new JButton("Color de contorno");
        buttonClean = new JButton("Limpiar lienzo");
        buttonOffsetSize = new JButton("Aplicar coordenadas y tamaño");        
        buttonRotate90 = new JButton("Rotar 90º");
        buttonRotate180 = new JButton("Rotar 180º");
        buttonRotate270 = new JButton("Rotar 270º");
        buttonRotate360 = new JButton("Rotar 360º");
        
        
        /*Buttons for movement*/
        buttonUp = new JButton("Arriba");
        buttonDown = new JButton("Abajo");
        buttonLeft = new JButton("Izquierda");
        buttonRight = new JButton("Derecha");
        
        labelPoints = new JLabel("Cantidad de vértices: ");
        labelStyle = new JLabel("Estilo de contorno: ");
        labelThickness = new JLabel("Grosor del contorno: ");
        labelPolySize = new JLabel("Tamaño en px");
        labelPolyPosition = new JLabel("Posición del polígono");
        labelOffsetX = new JLabel("                 X: ");
        labelOffsetY = new JLabel("Y: ");     
        labelPolySize = new JLabel("Tamaño en px: ");
        
        boxPoints =new JComboBox(listPoints);
        boxStyle =new JComboBox(listStyle);
        boxThickness =new JComboBox(listThickness);
        
        checkFill = new JCheckBox("Con Relleno");
        checkFill.setSelected(true);
        
        //Initialize offset fields
        fieldOffsetX = new JTextField("435");
        fieldOffsetY = new JTextField("325");
        fieldPolySize = new JTextField("200");        
        fieldOffsetX.setPreferredSize(new Dimension(40, 20));
        fieldOffsetY.setPreferredSize(new Dimension(40, 20));
        fieldPolySize.setPreferredSize(new Dimension(40, 20));        

        buttonBackground.addActionListener(this);
        buttonGrid.addActionListener(this);
        buttonLine.addActionListener(this);
        buttonFill.addActionListener(this);
        buttonClean.addActionListener(this);
        checkFill.addActionListener(this); 
        boxPoints.addActionListener(this);
        boxStyle.addActionListener(this);
        boxThickness.addActionListener(this);
        
        /*Action Listener for movement buttons*/
        buttonUp.addActionListener(this);
        buttonDown.addActionListener(this);
        buttonLeft.addActionListener(this);
        buttonRight.addActionListener(this);
        
        //ActionListener for setting up new corrdinates and size
        buttonOffsetSize.addActionListener(this);
        
        //buttons for rotation        
        buttonRotate90.addActionListener(this);
        buttonRotate180.addActionListener(this);
        buttonRotate270.addActionListener(this);
        buttonRotate360.addActionListener(this);
        
        //Panel for the buttons
        panelButton.add(buttonBackground);
        panelButton.add(buttonGrid);
        panelButton.add(buttonLine);
        panelButton.add(buttonFill);
        panelButton.add(checkFill);
        
        //panel for the combo boxes and labels
        panelSlider.add(labelPoints);
        panelSlider.add(boxPoints);
        panelSlider.add(labelStyle);
        panelSlider.add(boxStyle);
        panelSlider.add(labelThickness);
        panelSlider.add(boxThickness);
        
        /*Adding the movement buttons to the panel*/
        panelMovement.add(buttonUp);
        panelMovement.add(buttonDown);
        panelMovement.add(buttonLeft);
        panelMovement.add(buttonRight);
        
        //Adding offset control components to the offset and size panel
        panelSizePosition.add(labelOffsetX);
        panelSizePosition.add(fieldOffsetX);
        panelSizePosition.add(labelOffsetY);
        panelSizePosition.add(fieldOffsetY);
        panelSizePosition.add(labelPolySize);
        panelSizePosition.add(fieldPolySize);
        panelSizePosition.add(buttonOffsetSize);   
        
        //Adding rotate buttons to rotate panel
        panelRotate.add(buttonRotate90);
        panelRotate.add(buttonRotate180);
        panelRotate.add(buttonRotate270);
        panelRotate.add(buttonRotate360);               
        
        //add offset and size components to the movement panel
        panelMovement.add(panelSizePosition, BorderLayout.NORTH);
        
        
        panelBelow.add(panelMovement, BorderLayout.NORTH);
        panelBelow.add(panelRotate, BorderLayout.SOUTH);
        
        //Add secondary panels to main panel
        panelMain.add(panelButton, BorderLayout.NORTH);
        panelMain.add(panelSlider, BorderLayout.SOUTH);
        
        //Main panel for the components
        add(panelMain, BorderLayout.NORTH);
        add(panelBelow, BorderLayout.SOUTH);
    }
    
    public void paintComponent(Graphics g) {                
        super.paintComponent(g);               
        Graphics2D g2 = (Graphics2D) g;
        
        defaultStroke = g2.getStroke();
        defaultComp = g2.getComposite();
        defaultTransform = g2.getTransform();
        
        
        //FONDO
        g.setColor(selectBackgroundColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());           
        
        //CUADRÍCULA
        g.setColor(selectGridColor);
        drawGrid(g);        
        
        //clean the canvas if clean is pressed
        if (flagClean) {
            //FONDO
            g.setColor(selectBackgroundColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            //CUADRÍCULA
            g.setColor(selectGridColor);
            drawGrid(g);  
            
            flagClean = false;
        }
        
        //first time the polygon is drawn
        //so the canvas won`t show up empty
        if (flagFirstDraw) {
            //relleno, después contorno
            int placeHolderX[] = {480, 653, 307};
            int placeHolderY[] = {125, 425, 424};
           
            g2.setColor(selectFillColor);
            g2.fillPolygon(placeHolderX, placeHolderY, 3);

            g.setColor(selectLineColor);
            g.drawPolygon(placeHolderX, placeHolderY, 3);
            
            g.setFont(new Font("DialogInput", Font.BOLD + Font.ITALIC, 15));
            g.setColor(Color.white);
            g.drawString("TRIÁNGULO", offsetX + moveIncrementX - 35, offsetY + moveIncrementY - 25);
            
            flagFirstDraw = false;
        }
        
        //draw the polygon that changes everytime                   
        if (flagDrawPoly) {           
            //establish the style and thickness of the outline
            setLineParameters(g2);
            
            if(!flagFill) {                        
                g.setColor(selectLineColor);
                g.drawPolygon(polyX, polyY, numberPoints);    
                                
                if (flagMirror) {
                    //This is the code for transparency
                    AlphaComposite alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, transparency);
                    g2.setComposite(alcom);
                    //End of transparency code  

                    g.setColor(selectLineColor);
                    g.drawPolygon(mirrorPolyX, mirrorPolyY, numberPoints);
                    g2.setComposite(defaultComp);
                }

            }else {      
                
                if(flagMirror) {
                    //This is the code for transparency
                    AlphaComposite alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, transparency);
                    g2.setComposite(alcom);
                    //End of transparency code     
                    
                    g.setColor(selectFillColor);
                    g.fillPolygon(mirrorPolyX, mirrorPolyY, numberPoints);
                    
                    g.setColor(selectLineColor);
                    g.drawPolygon(mirrorPolyX, mirrorPolyY, numberPoints);     
                    
                    g2.setComposite(defaultComp);
                }
                //rotate shape
                if (flagRotate) {
                    rotate(g2);
                    
                    flagRotate = false;
                }
                
                g.setColor(selectFillColor);
                g.fillPolygon(polyX, polyY, numberPoints);

                g.setColor(selectLineColor);
                g2.drawPolygon(polyX, polyY, numberPoints);                     
            }
            
            drawName(g);
        }

        g2.setStroke(defaultStroke);
        g2.setComposite(defaultComp);    
        g2.setTransform(defaultTransform);                
    }
    
    public void setLineParameters(Graphics2D g2) {
        switch (selectThickness) {
            case "Bajo":
                thicknessValue = 2;
                break;

            case "Medio":
                thicknessValue = 4;
                break;

            case "Alto":
                thicknessValue = 6;
                break;
        }

        //get the style of the line before you draw it
        switch (selectStyles) {
            case "Continuo":
                g2.setStroke(new BasicStroke(thicknessValue));
                break;

            case "Discontinuo":
                Stroke dashed = new BasicStroke(thicknessValue, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                g2.setStroke(dashed);
                break;

            case "Punteado":
                Stroke dashed2 = new BasicStroke(thicknessValue, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{19}, 0);
                g2.setStroke(dashed2);
                break;
        }
    }
    
    //get the coordinates and draw the polygon
    public void getPolygonCoord() {    
        polyX = new int [numberPoints];
        polyY = new int [numberPoints]; 
        updatePolyX = new int [numberPoints];
        
        for (int i = 0; i < numberPoints; i++) {            
            polyX[i] =   (int) (R * Math.sin( 2 * Math.PI * (numberPoints - i) / numberPoints)) * -1 + offsetX + moveIncrementX;
            polyY[i] =   (int) (R * Math.cos( 2 * Math.PI * (numberPoints - i) / numberPoints)) * -1 + offsetY + moveIncrementY;                           
        }         
        
        repaint();
        flagDrawPoly = true;                       
    }
    
    public void getMirrorPolygonCoord() {
        mirrorPolyX = new int [numberPoints];
        mirrorPolyY = new int [numberPoints];
        for (int i = 0; i < numberPoints; i++) {            
            mirrorPolyX[i] =   (int) (R * Math.sin( 2 * Math.PI * (numberPoints - i) / numberPoints)) * -1 + this.getWidth() / 2;
            mirrorPolyY[i] =   (int) (R * Math.cos( 2 * Math.PI * (numberPoints - i) / numberPoints)) + this.getHeight() / 2 + mirrorSpace;                  
        }
        
        repaint();
    }
    
    public boolean hasFillColor() {
        
        if (checkFill.isSelected()) {
            flagFill = true;
            buttonFill.setEnabled(true);
            repaint();
        }else {
            flagFill = false;
            buttonFill.setEnabled(false);
            repaint();
        }
        
        return flagFill;
    }
    
    private void drawGrid(Graphics g) {                
        for (int i = 0; i < this.getWidth(); i+= 15) {
            g.drawLine(i, 0, i, this.getHeight());
            g.drawLine(0, i, this.getWidth(), i);
        }
    }
    
    public void chooseBackgroundColor() {
        selectBackgroundColor = JColorChooser.showDialog(null, "Color de fondo", selectBackgroundColor);
        
        if (selectBackgroundColor == null) {
            selectBackgroundColor = new Color(40, 44, 52);
        }
        
        repaint();
    }
    
    public void chooseGridColor() {
        selectGridColor = JColorChooser.showDialog(null, "Color de cuadrícula", selectGridColor);
        
        if (selectGridColor == null) {
            selectGridColor = new Color(161, 82, 219);
        }
        
        repaint();
    }
    
    public void chooseLineColor() {
        selectLineColor = JColorChooser.showDialog(null, "Color de contorno", selectLineColor);
        
        if (selectLineColor == null) {
            selectLineColor = new Color(225, 185, 110);
        }
        
        repaint();
    }
    
    public void chooseFillColor() {
        selectFillColor = JColorChooser.showDialog(null, "Color de relleno", selectFillColor);
        
        if (selectFillColor == null) {
            selectFillColor = new Color(83, 152, 212);
        }
        
        repaint();
    }
    
    private void getBoxValue() {
        selectPoints = (String) boxPoints.getSelectedItem();        
        selectStyles = (String) boxStyle.getSelectedItem();
        selectThickness = (String) boxThickness.getSelectedItem();             
        
        switch(selectPoints) {        
            case "3":
                numberPoints = 3;
                mirrorSpace = 25;
            break;
            
            case "4":
                numberPoints = 4;
                mirrorSpace = 175;
            break;
            
            case "5":
                numberPoints = 5;
                mirrorSpace = 115;
            break;
        
            case "6":
                numberPoints = 6;
                mirrorSpace = 175;
            break;
            
            case "7":
                numberPoints = 7;
                mirrorSpace = 145;
            break;
            
            case "8":
                numberPoints = 8;
                mirrorSpace = 175;
            break;
            
            case "9":
                numberPoints = 9;
                mirrorSpace = 155;
            break;
            
            case "10":
                numberPoints = 10;
                mirrorSpace = 175;
            break;
            
            case "11":
                numberPoints = 11;
                mirrorSpace = 160;
            break;
            
            case "12":
                numberPoints = 12;
                mirrorSpace = 175;
            break;
            
            case "13":
                numberPoints = 13;
                mirrorSpace = 165;
            break;
            
            case "14":
                numberPoints = 14;
                mirrorSpace = 175;
            break;
            
            case "15":
                numberPoints = 15;
                mirrorSpace = 165;
            break;
            
            case "16":
                numberPoints = 16;
                mirrorSpace = 175;
            break;
        }
               
        repaint();
    }
    
    private void drawName(Graphics g) {
        int fontSize = (valueSize / valueSize) * 10;
        g.setFont(new Font("DialogInput", Font.BOLD + Font.ITALIC, fontSize));
        
        g.setColor(Color.white);
        switch(selectPoints) {
        
            case "3":                
                g.drawString("TRIÁNGULO", offsetX - 50 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "4":
                g.drawString("CUADRILÁTERO", offsetX - 60 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "5":
                g.drawString("PENTÁGONO", offsetX - 50 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
        
            case "6":
                g.drawString("HEXÁGONO", offsetX - 50 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "7":
                g.drawString("HEPTÁGONO", offsetX - 50 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "8":
                g.drawString("OCTÁGONO", offsetX - 50 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "9":
                g.drawString("ENÁGONO", offsetX - 50 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
                        
            case "10":
                g.drawString("DECÁGONO", offsetX - 50 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "11":
                g.drawString("ENDECÁGONO", offsetX - 50 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "12":
                g.drawString("DODECÁGONO", offsetX - 50 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "13":
                g.drawString("TRIDECÁGONO", offsetX - 60 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "14":
                g.drawString("TETRADECÁGONO", offsetX - 60 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "15":
                g.drawString("PENTADECÁGONO", offsetX - 70 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
            
            case "16":
                g.drawString("HEXADECÁGONO", offsetX - 60 + moveIncrementX, offsetY - 60 + moveIncrementY);
            break;
        }
    } 
    
    //Method for the offset value parsing
    private boolean validateFields() {
        
        boolean areValid = false;
        
        try{
            offsetX = Integer.parseInt(fieldOffsetX.getText());
            offsetY = Integer.parseInt(fieldOffsetY.getText());
            valueSize = Integer.parseInt(fieldPolySize.getText());
            //update the new radius
            R = valueSize;
            
            areValid = checkValue(offsetX) && checkValue(offsetY) && checkSizeValue(valueSize);
            
        }catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los valores deben de ser ENTEROS POSITIVOS y el tamaño debe de ser mayor igual a 1");
        }
        
        return areValid;
    }
    
    private boolean checkValue(int value) {
        return value >= 0;
    }
    
    private boolean checkSizeValue(int value) {
        return value >= 1;
    }
    
    private void rotate (Graphics2D g2) {     
        g2.rotate(Math.toRadians(valueRotate), polyX[0], polyY[0]);//pass the off set to the X and Y paramaters       
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        hasFillColor();
        getBoxValue();
        getPolygonCoord();
        getMirrorPolygonCoord();        
        repaint();

        if (e.getSource() == buttonBackground) {            
            chooseBackgroundColor();
        }
        
        if (e.getSource() == buttonLine) {
            chooseLineColor();
        }
        
        if (e.getSource() == buttonFill) {
            chooseFillColor();
        }
        
        if (e.getSource() == buttonGrid) {
            chooseGridColor();
        }     
        
        if (e.getSource() == buttonClean) {
            flagClean = true;
            flagDrawPoly = false;
            repaint();
        }

        if (e.getSource() == buttonOffsetSize) {
            if(validateFields()) {                
                //Initialize movement variables so that the offset won´t be affected                
                moveIncrementX = 0;
                moveIncrementY = 0;
                
                getPolygonCoord();
                repaint();
            }else {
                JOptionPane.showMessageDialog(null, "Los valores deben de ser ENTEROS POSITIVOS");
            }         
        }
        
        //Rotate buttons below
        if (e.getSource() == buttonRotate90) {
            flagRotate = true;
            valueRotate = 90;
            repaint();
        }
        
        if (e.getSource() == buttonRotate180) {
            flagRotate = true;
            valueRotate = 180;
            repaint();
        }
        
        if (e.getSource() == buttonRotate270) {
            flagRotate = true;
            valueRotate = 270;
            repaint();
        }
        
        if (e.getSource() == buttonRotate360) {
            flagRotate = true;
            valueRotate = 360;
            repaint();
        }
        
        //Movement buttons below
        if (e.getSource() == buttonUp) {                     
            moveIncrementY -= 50;            
            getPolygonCoord();
            repaint();
        }
        
        if (e.getSource() == buttonDown) {                     
            moveIncrementY += 50;
            getPolygonCoord();
            repaint();
        }
        
        if (e.getSource() == buttonRight) {    
            moveIncrementX += 50;
            getPolygonCoord();
            repaint();            
        }
        
        if (e.getSource() == buttonLeft) {                 
            moveIncrementX -= 50;            
            getPolygonCoord();
            repaint();
        }
        
    }
}