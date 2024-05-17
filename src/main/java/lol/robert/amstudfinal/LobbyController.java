package lol.robert.amstudfinal;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class LobbyController {
    @FXML
    private ImageView cialogo;
    @FXML
    private Button playButton;
    @FXML
    private AnchorPane topPane;
    @FXML
    private VBox wholeVbox;
    @FXML
    private AnchorPane bottomPane;
    @FXML
    private Label infoLabel;

    @FXML
    public void initialize(){

         URL ciaFileName = GameController.class.getResource("images/cia.png");
        try {
            FileInputStream cia = new FileInputStream(ciaFileName.getPath());
            Image ciaImage = new Image(cia);
            cialogo.setImage(ciaImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        wholeVbox.setBackground(Background.fill(Color.DARKGREY));
        topPane.setBackground(Background.fill(Color.LIGHTGREY));
        bottomPane.setBackground(Background.fill(Color.LIGHTGREY));

        infoLabel.setText("Welcome to Surveillance Please, an informative game about surveillance and the Patriot Act in the United States!\n" + "\n"+
                "In this game, you will act as the CIA under the Patriot Act, and choose to either surveil or ignore various different individuals\n" + "\n"+
                "Your goal is to mimic what the CIA would have done during this time period - reach 1000 points to win!\n"+"\n" +
                "Ready to play?");


    }


    @FXML
    protected void playButtonClick() {

        try {
            FXMLLoader game = new FXMLLoader(LobbyController.class.getResource("game.fxml"));
            Scene scene = new Scene(game.load(), 900, 600);
            Stage currentStage = (Stage) playButton.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}