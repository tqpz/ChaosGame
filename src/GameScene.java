import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by Mateusz on 07.05.2017.
 */
public class GameScene extends Pane {
    private ArrayList<Point> points = new ArrayList<>(0); //this array is representing all alive cells on board+
    private int POINT_SIZE = 1;
    private Canvas canvas = new Canvas(800, 600);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Point startingPoint;
    private Point lastPoint;
    private boolean triangle, pentagon;
    private int lastRoll = 0;

    GameScene() {
        getChildren().add(canvas);
        startingPoint = new Point(45, 19);
        points.add(startingPoint);
        lastPoint = startingPoint;

        triangle = false;
        pentagon = true;


        int numOfExecutors = 1000;

        for(int i = 0; i < numOfExecutors; i++){
            new AnimationTimer() {
                @Override
                public void handle(long timestamp) {
                    int diceroll;

                    if (triangle) {
                        diceroll = (int) (Math.random() * 6) + 1;
                        drawPredefinedTriangle();
                        if (diceroll <= 2)
                            lastPoint = new Point(middlePoint(lastPoint, points.get(1)));
                        else if (diceroll > 2 && diceroll <= 4)
                            lastPoint = new Point(middlePoint(lastPoint, points.get(2)));
                        else
                            lastPoint = new Point(middlePoint(lastPoint, points.get(3)));
                        points.add(lastPoint);
                        requestLayout();


                    } else if (pentagon) {
                        diceroll = (int) (Math.random() * 5) + 1;

                        drawPredefinedPentagon();
                        if (diceroll <= 1 && diceroll != lastRoll)
                            lastPoint = new Point(middlePoint(lastPoint, points.get(1)));
                        else if ((diceroll > 1 && diceroll <= 2) && diceroll != lastRoll)
                            lastPoint = new Point(middlePoint(lastPoint, points.get(2)));
                        else if ((diceroll > 2 && diceroll <= 3) && diceroll != lastRoll)
                            lastPoint = new Point(middlePoint(lastPoint, points.get(3)));
                        else if ((diceroll > 3 && diceroll <= 4) && diceroll != lastRoll)
                            lastPoint = new Point(middlePoint(lastPoint, points.get(4)));
                        else if((diceroll > 4 && diceroll <= 5) && diceroll != lastRoll)
                            lastPoint = new Point(middlePoint(lastPoint, points.get(5)));

                        points.add(lastPoint);
                        lastRoll = diceroll;
                        requestLayout();
                    }
                }
            }.start();
        }

    }

    private void drawPoints() {
        try {
            for (Point newPoint : points) {
                gc.setFill(Color.BLUE);
                gc.fillOval(POINT_SIZE + (POINT_SIZE * newPoint.x),
                        POINT_SIZE + (POINT_SIZE * newPoint.y),
                        POINT_SIZE,
                        POINT_SIZE);
            }
        } catch (ConcurrentModificationException | NullPointerException e) {
        }
    }


    protected void layoutChildren() {
        super.layoutChildren();
        canvas.setWidth(getWidth());
        canvas.setHeight(getHeight());

        gc.clearRect(0, 0, getWidth(), getHeight()); //clear canvas after each generation
        gc.setLineWidth(0.2);

        drawPoints();
    }

    private void drawPredefinedTriangle() {
        points.add(new Point(400, 20));
        points.add(new Point(20, 700));
        points.add(new Point(780, 700));
        requestLayout();
    }

    private void drawPredefinedPentagon() {
        POINT_SIZE = 1;
        points.add(new Point(300, 20));
        points.add(new Point(20, 300));
        points.add(new Point(150, 600));
        points.add(new Point(450, 600));
        points.add(new Point(600, 300));
        requestLayout();
    }

    private Point middlePoint(Point start, Point end) {
        Point result;
        int resultX, resultY;

        resultX = (start.x + end.x) / 2;
        resultY = (start.y + end.y) / 2;

        result = new Point(resultX, resultY);
        return result;
    }
}
