package projek.projekpbo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//Class untuk start program
public class StartProgram extends Application {

    //Ketika program pertama kali dijalankan akan menuju ke scene main menu
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Room Booking Portal Universitas Hasanuddin");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}