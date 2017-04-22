package canvas.viewAndController;

import canvas.Main;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import canvas.model.MonteCarlo;
import canvas.model.MonteCarloDrawerTask;

/**
 * Created by madrimas on 02.04.2017.
 */
public class DrawingController {

    @FXML
    private Canvas canvas;

    @FXML
    private Label xBeginLabel;
    @FXML
    private Label yBeginLabel;
    @FXML
    private Label xEndLabel;
    @FXML
    private Label yEndLabel;
    @FXML
    private Label noOfPointsLabel;
    @FXML
    private Label sumLabel;


    private MonteCarloDrawerTask mcDrawerTask;

    @FXML
    private void initialize(){
        showMonteCarloDetails();
    }

    @FXML
    private void handleCalculateBtnAction() throws InterruptedException {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        mcDrawerTask = new MonteCarloDrawerTask(gc, monteCarlo);
        mcDrawerTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                showMonteCarloDetails();
            }
        });
        progressBar.progressProperty().bind(mcDrawerTask.progressProperty());

        showMonteCarloDetails();

        Thread thread = new Thread(mcDrawerTask);
        thread.start();
    }

    @FXML
    private void handleStopBtnAction(){
        if(mcDrawerTask != null)
            mcDrawerTask.cancel();
    }

    @FXML
    private ProgressBar progressBar;

    @FXML
    private void handleEditDataAction(){
        boolean okClicked = main.showMonteCarloDialog();
        if(okClicked){
            showMonteCarloDetails();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Błąd dancyh");
            alert.setHeaderText("Nie podano danych");

            alert.showAndWait();
        }
    }


    private void showMonteCarloDetails(){
        if(monteCarlo != null) {
            xBeginLabel.setText(Double.toString(monteCarlo.getxBegin()));
            yBeginLabel.setText(Double.toString(monteCarlo.getxEnd()));
            xEndLabel.setText(Double.toString(monteCarlo.getyBegin()));
            yEndLabel.setText(Double.toString(monteCarlo.getyEnd()));
            noOfPointsLabel.setText(Long.toString(monteCarlo.getNoOfPoints()));
            sumLabel.setText(Double.toString(monteCarlo.getSum()));
        }
        else {
            xBeginLabel.setText("null");
            yBeginLabel.setText("null");
            xEndLabel.setText("null");
            yEndLabel.setText("null");
            noOfPointsLabel.setText("null");
            sumLabel.setText("null");
        }
    }

    private Main main;
    private MonteCarlo monteCarlo;

    public void setMonteCarlo(MonteCarlo monteCarlo){
        this.monteCarlo = monteCarlo;
    }

    public void setMain(Main main){
        this.main = main;
    }
}
