package canvas.model;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by mzdrimas on 03.04.2017.
 */
public class MonteCarloDrawerTask extends Task {
    volatile double sum;

    @Override
    protected Object call() throws Exception{
        final int width = (int)gc.getCanvas().getWidth();
        final int height = (int)gc.getCanvas().getHeight();

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        double dx = data.getxEnd() - data.getxBegin();
        double dy = data.getyEnd() - data.getyBegin();

        double xB = data.getxBegin();
        double yB = data.getyBegin();

        double x;
        double y;

        long hits = 0;

        Random random = new Random();

        long noOfPoints = data.getNoOfPoints();

        long noHits = 0;

        for(long i = 1; i <= noOfPoints; i++){
            x = xB + (-2*xB) * random.nextDouble();
            y = yB + (-2*yB) * random.nextDouble();
            if(Equation.calc(x,y)){
                x=(width*(x-xB)/(-2*xB)+(-width/2));
                y=(height*(y-yB)/(-2*yB)+(-height/2));
                bi.setRGB((int)(x + width/2),(int)(-y + height/2), Color.YELLOW.getRGB());
                hits++;
            }
            else{
                x=(width*(x-xB)/(-2*xB)+(-width/2));
                y=(height*(y-yB)/(-2*yB)+(-height/2));
                bi.setRGB((int)(x + width/2),(int)(-y + height/2), Color.BLUE.getRGB());
                noHits++;
            }
            if(i % 10000 == 0) gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0,0 );
            updateProgress(i, data.getNoOfPoints());
            if(isCancelled()) break;
        }
        sum = (hits/(double)noOfPoints)*dx*dy;
        data.setSum(sum);


        return null;
    }

    public MonteCarloDrawerTask(GraphicsContext gc, MonteCarlo data){
        this.gc = gc;
        this.data = data;
    }

    private GraphicsContext gc;
    private MonteCarlo data;
}
