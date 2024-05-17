package lol.robert.amstudfinal;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class GameController {


    // left side
    @FXML
    private Label statsLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label dynamicscoreLabel;
    @FXML
    private Label promptLabel;
    @FXML
    private ImageView cialogo;
    @FXML
    private Label correctLabel;
    @FXML
    private Label incorrectLabel;

    // center
    @FXML
    private Button surveillanceButton;
    @FXML
    private Button ignoreButton;
    @FXML
    private ImageView characterImage;
    @FXML
    private Label winloseLabel;

    // panes
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane topmiddlePane;
    @FXML
    private AnchorPane bottommiddlePane;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private SplitPane middlePane;


    // right side

    @FXML
    private Label informationLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label crimesLabel;


    // game attributes

    private int score;
    private int correct;
    private int incorrect;
    private boolean running;
    private HashMap<String, String[]> characters;
    private boolean answer;
    private boolean winner;
    private int turn;
    private String person;


    @FXML
    public void initialize(){

        characters = new HashMap<>();
        characters.put("Kevin_Mitnick", new String[] {"Kevin is an American computer security consultant, owner of Mitnick Security Consulting, and and a resident of Loos Angeles, California.", "Computer Crimes", "true"});
        characters.put("Daniel_Gonzales", new String[] {"Daniel is a new American citizen, recently immigrating from Mexico. Daniel has a family of 3 and works as an Accountant.", "None", "true"});
        characters.put("Noah_Butler", new String[] {"Noah Butler is a hardened criminal addicted to substances throughout his life. His whereabouts are currently unknown.", "Armed Robbery, Murder, Stealing Ideas", "true"});
        characters.put("James_Ross", new String[] {"James is a very successful businessman living in New York. Aside from his career, James lives alone with his 38 cats.", "Being bad at Soccer", "true"});
        characters.put("Joshua_Banchik", new String[] {"Joshua is an Israeli citizen in America living temporarily in Brownsville, Texas. He lives a relatively quiet life with his pet worm.", "Wire Fraud, Building Illegal Tunnels", "false"});
        characters.put("Ford_Tate", new String[] {"Ford is a resident of Florida, with a very successful Business career. Despite this, he lives in and out of houses near West Palm.", "Larsony, Federal Scamming, Mail Fraud", "true"});
        characters.put("Aadi_Patel", new String[] {"Aadi is a go-with-the-flow resident of Honolulu, Hawaii, who runs a successful fishing business off of the coast. ", "Assault, Possession of a deadly weapon", "true"});
        characters.put("Arnav_Marnir", new String[] {"Arnav is an Indian citizen temporarily residing in Boston, New York to run his successful business. On the side, Arnav deals drugs.", "Possession & Distribution", "false"});
        characters.put("Julian_Monz", new String[] {"Julian is a professional breakdancer out of Key West, Florida. According to his tax returns, he earns no money and has no home.", "Tax Fraud, Public Exposure", "true"});
        characters.put("Mir_Quiddus", new String[] {"Mir is an academic weapon residing out of Morganton, North Carolina. Despite never leaving the county, Mir has done well for himself and has a wife and 27 kids.", "Academic Dishonesty", "true"});





        // load & set images
        FileInputStream cia = null;
        FileInputStream background = null;
        FileInputStream center = null;
        FileInputStream lights = null;
        try {

            URL backgroundFileName = GameController.class.getResource("images/background.jpg");
            background = new FileInputStream(backgroundFileName.getPath());

            URL ciaFileName = GameController.class.getResource("images/cia.png");
            cia = new FileInputStream(ciaFileName.getPath());

            URL centerFileName = GameController.class.getResource("images/center.jpg");
            center = new FileInputStream(centerFileName.getPath());

            URL lightsFileName = GameController.class.getResource("images/lights.jpeg");
            lights = new FileInputStream(lightsFileName.getPath());

            Image ciaImage = new Image(cia);
            Image backgroundImage = new Image(background);
            Image centerImage = new Image(center);
            Image topcenterImage = new Image(lights);


            BackgroundImage sideBackground = new BackgroundImage(backgroundImage,
             BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

            BackgroundImage centerBackground = new BackgroundImage(centerImage,
             BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

            BackgroundImage centermiddleBackground = new BackgroundImage(topcenterImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);


            // set images

            cialogo.setImage(ciaImage);
            leftPane.setBackground(new Background(sideBackground));
            rightPane.setBackground(new Background(sideBackground));
            bottommiddlePane.setBackground(new Background(centerBackground));
            topmiddlePane.setBackground(new Background(centermiddleBackground));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



            // style buttons

            surveillanceButton.setBackground(Background.fill(Color.DARKRED));
            ignoreButton.setBackground(Background.fill(Color.DARKRED));
            surveillanceButton.setTextFill(Color.WHITE);
            ignoreButton.setTextFill(Color.WHITE);


            startGame();


    }

    @FXML
    protected void onSurveillance(){

        if(running) {


            if (answer) {
                score += 100;
                correct++;
            } else {
                score -= 200;
                incorrect++;
            }
            turn++;

            try {
                URL personFileName = GameController.class.getResource(STR."images/characters/\{person}.png");
                FileInputStream personstream = new FileInputStream(personFileName.getPath());
                Image image = new Image(personstream);
                characterImage.setImage(image);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            centertoRight(characterImage).setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    newTurn();
                }
            });
        } else {
            running = false;
            newTurn();
        }

    }

    @FXML
    protected void onIgnore(){

        if(running) {


            if (answer) {
                score -= 200;
                incorrect++;
            } else {
                score += 100;
                correct++;
            }
            turn++;

            try {
                URL personFileName = GameController.class.getResource(STR."images/characters/\{person}.png");
                FileInputStream personstream = new FileInputStream(personFileName.getPath());
                Image image = new Image(personstream);
                characterImage.setImage(image);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            centertoRight(characterImage).setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    newTurn();
                }
            });
        } else {
            running = false;
            newTurn();
        }
    }


    private void startGame() {
        running = true;
        score = 500;
        correct = 0;
        incorrect = 0;
        turn = 0;
        newTurn();
    }

    private void newTurn() {

        if(running) {


        Random random = new Random();

        if(turn == 0) {
            // if its the users first turn
            fadeIn(characterImage);
            swapImage();
        } else {
            // if its not the first turn
            swapImage();
            lefttoCenter(characterImage);
        }

        if (score < 250) {
                running = false;
                winner = false;

            }

            if (score > 1000) {
                running = false;
                winner = true;
            }
                // update stats labels
                scoreLabel.setText(STR."Score:  \{score}");
                correctLabel.setText(STR."Correct:  \{correct}");
                incorrectLabel.setText(STR."Incorrect:  \{incorrect}");

                // get the person being identified
                int i = random.nextInt(0, 11);
                person = (String) characters.keySet().toArray()[i];

                // find out what the correct answer should be
                String[] value = characters.get(person);
                if (value[2].equals("true")) {
                    answer = true;
                } else {
                    answer = false;
                }

                // fill their information in on the Labels on the right side of screen
                nameLabel.setText(person);
                descriptionLabel.setText(characters.get(person)[0]);
                crimesLabel.setText(characters.get(person)[1]);
            } else {
            System.out.println("game oover");
                if(winner) {
                    winloseLabel.setText("You Win!\n Click here to continue");
                    winloseLabel.setBackground(Background.fill(Color.LIGHTGREY));
                } else {
                    winloseLabel.setText("You lose.. \n Click here to continue");
                    winloseLabel.setBackground(Background.fill(Color.LIGHTGREY));
                }
            }
        }


        private Transition centertoRight(ImageView image) {
            characterImage.setX(0);

            ImageView rectParallel = characterImage;

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), rectParallel);
            translateTransition.setFromX(0);
            translateTransition.setToX(500);
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(true);

            translateTransition.play();
            return translateTransition;
        }

        @FXML
        private void restart(){
            FXMLLoader game = new FXMLLoader(GameApplication.class.getResource("lobby.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(game.load(), 500, 500);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage currentStage = (Stage) bottommiddlePane.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();

        }


        private Transition lefttoCenter(ImageView image) {

            characterImage.setX(-400);

            ImageView rectParallel = characterImage;

        TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(2000), rectParallel);
        translateTransition.setFromX(0);
        translateTransition.setToX(400);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        translateTransition.play();


        return translateTransition;
        }

        private void fadeIn(ImageView image) {

        swapImage();

        FadeTransition ft = new FadeTransition(Duration.millis(3000), image);
            ft.setFromValue(.1);
            ft.setToValue(1);
            ft.play();

        }

        private void swapImage() {

                    Random random = new Random();
            int index = random.nextInt(1, 10);

                URL silhouetteFileName = GameController.class.getResource(STR."images/silhouettes/\{index}.png");
            FileInputStream silhouetteStream = null;
            try {
                silhouetteStream = new FileInputStream(silhouetteFileName.getPath());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Image image = new Image(silhouetteStream);
            characterImage.setImage(image);


        }


}