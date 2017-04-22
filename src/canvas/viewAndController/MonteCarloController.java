package canvas.viewAndController;

import canvas.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import canvas.model.MonteCarlo;

/**
 * Created by madrimas on 03.04.2017.
 */
public class MonteCarloController {

    @FXML
    private TextField xBegin;

    @FXML
    private TextField xEnd;

    @FXML
    private TextField yBegin;

    @FXML
    private TextField yEnd;

    @FXML
    private TextField noOfPoints;

    @FXML
    private void handleOk(){
        if(isInputValid()) {
            data.setxBegin(Double.parseDouble(xBegin.getText()));
            data.setxEnd(Double.parseDouble(xEnd.getText()));
            data.setyBegin(Double.parseDouble(yBegin.getText()));
            data.setyEnd(Double.parseDouble(yEnd.getText()));
            data.setNoOfPoints(Long.parseLong(noOfPoints.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }


    private boolean isInputValid(){
        String errorMessage = "";
        if(xBegin.getText() == null || xBegin.getText().length() == 0){
            errorMessage += "Błędny x początkowy\n";
        } else {
            try {
                Double.parseDouble(xBegin.getText());
            } catch(NumberFormatException e){
                errorMessage += "Wpisana liczba nie pasuje do typu double (x poczatkowe)\n";
            }
        }


        if(xEnd.getText() == null || xEnd.getText().length() == 0){
            errorMessage += "Błędny x końcowy\n";
        } else {
            try {
                Double.parseDouble(xEnd.getText());
            } catch(NumberFormatException e){
                errorMessage += "Wpisana liczba nie pasuje do typu double (x końcowy)\n";
            }
        }


        if(yBegin.getText() == null || yBegin.getText().length() == 0){
            errorMessage += "Błędny y początkowy\n";
        } else {
            try {
                Double.parseDouble(yBegin.getText());
            } catch(NumberFormatException e){
                errorMessage += "Wpisana liczba nie pasuje do typu double (y poczatkowy)\n";
            }
        }


        if(yEnd.getText() == null || yEnd.getText().length() == 0){
            errorMessage += "Błędny x końcowy\n";
        } else {
            try {
                Double.parseDouble(yEnd.getText());
            } catch(NumberFormatException e){
                errorMessage += "Wpisana liczba nie pasuje do typu double (y końcowy)\n";
            }
        }


        if(noOfPoints.getText() == null || noOfPoints.getText().length() == 0){
            errorMessage += "Błędny x końcowy\n";
        } else {
            try {
                Long.parseLong(noOfPoints.getText());
            } catch(NumberFormatException e){
                errorMessage += "Wpisana liczba nie pasuje do typu long\n";
            }
        }


        if (errorMessage.length() == 0)
            return true;
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Błędne dane!");
            alert.setHeaderText("Proszę skorygować dane");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }

    }


    private Stage dialogStage;
    private MonteCarlo data;
    private Main main;
    private boolean okClicked = false;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(MonteCarlo data) {
        this.data = data;

        xBegin.setText(Double.toString(data.getxBegin()));
        xEnd.setText(Double.toString(data.getxEnd()));
        yBegin.setText(Double.toString(data.getyBegin()));
        yEnd.setText(Double.toString(data.getyEnd()));

        noOfPoints.setText(Long.toString(data.getNoOfPoints()));

    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
