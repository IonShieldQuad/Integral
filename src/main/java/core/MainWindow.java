package core;

import graphics.GraphDisplay;
import math.*;
import org.mariuszgromada.math.mxparser.Function;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class MainWindow {
    private JPanel rootPanel;
    private JTextArea log;
    private JTextField lowerX;
    private JTextField upperX;
    private JButton calculateButton;
    private GraphDisplay graph;
    private JTextField functionField;
    private JTextField lowerY;
    private JTextField upperY;
    
    private Function function;
    private Integrator integrator = new SimpsonIntegrator();
    private DoubleBinaryOperator integral;
    
    private MainWindow() {
        initComponents();
    }
    
    private void initComponents() {
        calculateButton.addActionListener(e -> calculate());
    }
    
    
    
    private void calculate() {
        try {
            log.setText("");
            function = new Function(functionField.getText());
            integral = integrator.integrate(function::calculate, 1000);
            log.append("\nGenerated");
            log.append("\nResult:" + integral.applyAsDouble(Double.parseDouble(lowerX.getText()), Double.parseDouble(upperX.getText())));
            updateGraph();
        }
        catch (NumberFormatException e) {
            log.append("\nInvalid input format");
        }
    }
    
    private void updateGraph() {
        graph.setFunction(function);
        graph.setIntegral(integral);
        graph.setLowerX(Double.parseDouble(lowerX.getText()));
        graph.setUpperX(Double.parseDouble(upperX.getText()));
        graph.setLowerY(Double.parseDouble(lowerY.getText()));
        graph.setUpperY(Double.parseDouble(upperY.getText()));
        graph.repaint();
    }
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Integral");
        MainWindow gui = new MainWindow();
        frame.setContentPane(gui.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
