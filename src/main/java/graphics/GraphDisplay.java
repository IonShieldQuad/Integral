package graphics;

import math.PointDouble;
import org.mariuszgromada.math.mxparser.Function;

import javax.swing.*;
import java.awt.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class GraphDisplay extends JPanel {
    private static final int MARGIN_X = 50;
    private static final int MARGIN_Y = 50;
    private static final double EXTRA_AMOUNT = 0.2;
    private static final Color GRID_COLOR = Color.GRAY;
    private static final Color GRAPH_COLOR = new Color(0x5bcefa);
    private static final Color POINT_COLOR = Color.YELLOW;
    private static final int POINT_SIZE = 5;
    
    private Function function;
    private DoubleBinaryOperator integral;
    
    private double lowerX ;
    private double upperX;
    private double lowerY;
    private double upperY;
    
    public GraphDisplay() {
        super();
    }
    
    public void setFunction(Function function) {
        this.function = function;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        drawGrid(g);
        if (function != null) {
            drawGraph(g, function::calculate);
        }
        if (integral != null) {
            drawGraph(g, x -> integral.applyAsDouble(lowerX(), x));
        }
    }
    
    
    private void drawGraph(Graphics g, DoubleUnaryOperator op) {
        g.setColor(new Color(Color.HSBtoRGB((float) Math.random(), 1.0f, 1.0f)));
        int prev = 0;
        for (int i = 0; i < graphWidth(); i++) {
            PointDouble val = graphToValue(new PointDouble(i + MARGIN_X, 0));
            val = new PointDouble(val.getX(), op.applyAsDouble(val.getX()));
            val = valueToGraph(val);
            if (i != 0) {
                g.drawLine(MARGIN_X + i - 1, prev, (int)Math.round(val.getX()), (int)Math.round(val.getY()));
            }
            prev = (int)Math.round(val.getY());
        }
    }
    
    private void drawGrid(Graphics g) {
        g.setColor(GRID_COLOR);
        g.drawLine(MARGIN_X, getHeight() - MARGIN_Y, getWidth() - MARGIN_X, getHeight() - MARGIN_Y);
        g.drawLine(MARGIN_X, MARGIN_Y + (int)(graphHeight() * (1 - EXTRA_AMOUNT)), getWidth() - MARGIN_X, MARGIN_Y + (int)(graphHeight() * (1 - EXTRA_AMOUNT)));
        g.drawLine(MARGIN_X, MARGIN_Y + (int)(graphHeight() * EXTRA_AMOUNT), getWidth() - MARGIN_X, MARGIN_Y + (int)(graphHeight() * EXTRA_AMOUNT));
        
        g.drawLine(MARGIN_X, getHeight() - MARGIN_Y, MARGIN_X, MARGIN_Y);
        g.drawLine(MARGIN_X + (int)(graphWidth() * EXTRA_AMOUNT), getHeight() - MARGIN_Y, MARGIN_X + (int)(graphWidth() * EXTRA_AMOUNT), MARGIN_Y);
        g.drawLine(MARGIN_X + (int)(graphWidth() * (1 - EXTRA_AMOUNT)), getHeight() - MARGIN_Y, MARGIN_X + (int)(graphWidth() * (1 - EXTRA_AMOUNT)), MARGIN_Y);
        
        g.drawString(Double.toString(lowerX()), MARGIN_X + (int)(graphWidth() * EXTRA_AMOUNT), getHeight() - MARGIN_Y / 2);
        g.drawString(Double.toString(upperX()), MARGIN_X + (int)(graphWidth() * (1 - EXTRA_AMOUNT)), getHeight() - MARGIN_Y / 2);
        g.drawString(Double.toString(lowerY()), MARGIN_X / 4, MARGIN_Y + (int)(graphHeight() * (1 - EXTRA_AMOUNT)));
        g.drawString(Double.toString(upperY()), MARGIN_X / 4, MARGIN_Y + (int)(graphHeight() * EXTRA_AMOUNT));
    }
    
    private int graphWidth() {
        return getWidth() - 2 * MARGIN_X;
    }
    
    private int graphHeight() {
        return getHeight() - 2 * MARGIN_Y;
    }
    
    private double lowerX() {
        return lowerX;
    }
    
    private double upperX() {
        return upperX;
    }
    
    private double lowerY() {
        return lowerY;
    }
    
    private double upperY() {
        return upperY;
    }
    
    public void setLowerX(double lowerX) {
        this.lowerX = lowerX;
    }
    
    public void setUpperX(double upperX) {
        this.upperX = upperX;
    }
    
    public void setLowerY(double lowerY) {
        this.lowerY = lowerY;
    }
    
    public void setUpperY(double upperY) {
        this.upperY = upperY;
    }
    
    private PointDouble valueToGraph(PointDouble point) {
        double valX = (point.getX() - lowerX()) / (upperX() - lowerX());
        double valY = (point.getY() - lowerY()) / (upperY() - lowerY());
        return new PointDouble(MARGIN_X + (int)((graphWidth() * EXTRA_AMOUNT) * (1 - valX) + (graphWidth() * (1 - EXTRA_AMOUNT)) * valX), getHeight() - MARGIN_Y - (int)((graphHeight() * EXTRA_AMOUNT) * (1 - valY) + (graphHeight() * (1 - EXTRA_AMOUNT)) * valY));
    }
    
    private PointDouble graphToValue(PointDouble point) {
        double valX = (point.getX() - (MARGIN_X + (graphWidth() * EXTRA_AMOUNT))) / ((MARGIN_X + (graphWidth() * (1 - EXTRA_AMOUNT))) - (MARGIN_X + (graphWidth() * EXTRA_AMOUNT)));
        double valY = (point.getY() - (MARGIN_Y + (graphHeight() * (1 - EXTRA_AMOUNT)))) / ((MARGIN_Y + (graphHeight() * EXTRA_AMOUNT)) - (MARGIN_Y + (graphHeight() * (1 - EXTRA_AMOUNT))));
        return new PointDouble(lowerX() * (1 - valX) + upperX() * valX, lowerY() * (1 - valY) + upperY() * valY);
    }
    
    public DoubleBinaryOperator getIntegral() {
        return integral;
    }
    
    public void setIntegral(DoubleBinaryOperator integral) {
        this.integral = integral;
    }
}
