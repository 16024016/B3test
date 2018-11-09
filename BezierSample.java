/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bernsteinsample;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author takahashi
 */
public class BezierSample extends Application {

  private final Canvas canvas = new Canvas(640, 480);
  private final List<Point2D> controlPoints = new ArrayList<>();
  
  
  public BezierSample() {
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    canvas.setOnMouseClicked(this::drawPoint);

    primaryStage.setTitle("b3semi");
    primaryStage.setScene(new Scene(new Pane(canvas)));
    primaryStage.show();
  }

  public void drawPoint(MouseEvent e) {
    
    double r = 5.0;
    canvas.getGraphicsContext2D()
            .strokeOval(e.getX() - r / 2, e.getY() - r / 2, r, r);

    controlPoints.add(new Point2D(e.getX(), e.getY()));
    
    if(controlPoints.size() >= 3){
        
        /*for(double t = 0.01; t <= 1;t += 0.01){
            
            canvas.getGraphicsContext2D().setStroke(Color.BLUE);
            canvas.getGraphicsContext2D().setLineWidth(10);
            canvas.getGraphicsContext2D()
                .strokeOval(Bezier(t).getX() - r / 2, Bezier(t).getY() - r / 2, r, r);
        
        } */  
        
        
        for(double t = 0.1; t <= 1;t += 0.1){
            canvas.getGraphicsContext2D().setStroke(Color.BLUE);
            canvas.getGraphicsContext2D().setLineWidth(1);
            canvas.getGraphicsContext2D()
                .strokeOval(Bernstein(t).getX() - r / 2,Bernstein(t).getY() - r / 2,r, r);

        
        }   
       for(double t = 0.1; t <= 1;t += 0.1){
            canvas.getGraphicsContext2D().setStroke(Color.RED);
            canvas.getGraphicsContext2D().setLineWidth(1);
            canvas.getGraphicsContext2D()
               .strokeLine((Bernstein(t).getX()),Bernstein(t).getY(),
                       Bernstein(t).getX()+Bibun(t).getX(), Bernstein(t).getY()+Bibun(t).getY());
       }
        
       
    }
    
    
    
  }
  
  public Point2D Bezier(double t){
      List<Point2D> r = new ArrayList<>(controlPoints);
      double x,y;
      for(int j = 1;j <= controlPoints.size();j++){
        for(int i = 0;i < controlPoints.size()- j;i++){
                x = (1 - t)*r.get(i).getX() + t * r.get(i+1).getX();
                y = (1 - t)*r.get(i).getY() + t * r.get(i+1).getY();
            
                r.set(i,new Point2D(x,y));
        }
      }
      return r.get(0);
  }
  
    public Point2D Bernstein(double t){
      Point2D r = Point2D.ZERO;
      double x = 0,y = 0,ni = 0,B = 1 ;
      
        int j = controlPoints.size() - 1; 
        for(int i = 0;i <= j;i++){
            
                ni = (double)factorial(j)/((double)factorial(i)*(double)factorial(j-i)) ; 
                B = ni * (double)Math.pow(t,i) * (double)Math.pow((1 - t),(j-i));
                x = controlPoints.get(i).getX()*B;
                y = controlPoints.get(i).getY()*B;
                r = r.add(new Point2D(x,y));
                
        }
        
        return r;
      
      
 
    }
    
    public Point2D Bibun(double t){
      Point2D r = Point2D.ZERO;
      double x = 0,y = 0,ni = 0,B = 1 ;
      
        double j = controlPoints.size() - 1; 
        for(int i = 0;i <= j-1;i++){
                ni = (double)factorial(j-1)/((double)factorial(i)*(double)factorial((j-1)-i)) ; 
                B = ni * (double)Math.pow(t,i) * (double)Math.pow((1 - t),((j-1)-i));
                x = ((controlPoints.get(i+1).getX() - controlPoints.get(i).getX())*B);
                y = ((controlPoints.get(i+1).getY() -controlPoints.get(i).getY())*B);

                r = r.add(new Point2D(x,y));
                
        }
        
        return r.multiply(j);
      
      
 
    }
    
  public double factorial(double n){
        double fact = 1;
        if (n == 0)
            return  fact;
        else { // in case of n > 0
            for (double i = n; i > 0; i--)
                fact *= i;
            return fact;
        }
   }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
