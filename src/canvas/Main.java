package canvas;

import canvas.viewAndController.DrawingController;
import canvas.viewAndController.MonteCarloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import canvas.model.MonteCarlo;

import java.io.IOException;
import java.net.URL;

/**
 * Created by madrimas on 02.04.2017.
 */
public class Main extends Application{
    private Stage primaryStage;
    private BorderPane rootLayout;
    private MonteCarlo data;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Monte Carlo Canvas");
        this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));

        URL resource = Main.class.getResource("viewAndController/RootLayout.fxml");
        System.out.println(resource.toString());

        initRootLayout();
        showDrawingOverview();

    }

    public void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("viewAndController/RootLayout.fxml"));

            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showDrawingOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("viewAndController/Drawing.fxml"));
            SplitPane splitPane = loader.load();

            rootLayout.setCenter(splitPane);

            DrawingController controller = loader.getController();
            controller.setMain(this);
            controller.setMonteCarlo(data);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean showMonteCarloDialog(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("viewAndController/MonteCarlo.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Podaj dane do Monte Carlo");
            dialogStage.getIcons().add(new Image("file:resources/images/icon.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            MonteCarloController controller = loader.getController();
            controller.setData(data);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public Main(){
        this.data = new MonteCarlo();
    }

    public static void main(String[] args){
        launch(args);
    }
}
