package Game;

import Pokemon.Pokemon;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    protected static final ArrayList<Pokemon> pokemonArrayList = new ArrayList<>();
    @FXML
    private Text textRed;
    @FXML
    private Text textYellow;
    @FXML
    private Text textGreen;
    @FXML
    private Text textBlue;
    @FXML
    private Pane paneRed;
    @FXML
    private Pane paneYellow;
    @FXML
    private Pane paneGreen;
    @FXML
    private Pane paneBlue;
    @FXML
    private Label countDownLabel;
    @FXML
    private Pane gamePanel;
    @FXML
    private Pane gameOverPanel;
    @FXML
    private Button nextButton;
    @FXML
    private Text scoreText;
    @FXML
    private Text bestScoreText;
    @FXML
    private ImageView pokemonMysteryEditionImage;
    private final ArrayList<Text> texts = new ArrayList<>();
    private final FadeTransition fadeTransition = new FadeTransition();
    private int score = 0;
    private int counter = 15;
    private Text correctPokemonText;
    private PauseTransition pauseTransition;
    private DropShadow dropShadow;
    private ColorAdjust colorAdjust;
    private final URL url = this.getClass().getResource("Files/GameData.DAT");
    private final String gameDataPATH = Objects.requireNonNull(url).toString().substring(url.toString().indexOf("/") + 1);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        texts.add(textRed);
        texts.add(textYellow);
        texts.add(textGreen);
        texts.add(textBlue);

        //añade texto
        scoreText.setText(scoreTextText + score);

        //añade texto y le asigna la mayor puntuacion obtenida hasta el momento
        bestScoreText.setText("Bᴇꜱᴛ ꜱᴄᴏʀᴇ: " + getBestScore());

        //escoge un pokemon aleatoriamente
        setNewPokemon();

        //inicia la cuenta atras
        pauseTransition = new PauseTransition(Duration.seconds(1));
        countDown();

        //Inicializa las propiedades de una transicion que hará que un componente pase de ser no transparente a transparente en x segundos
        fadeTransition.setNode(pokemonMysteryEditionImage);
        fadeTransition.setDuration(new Duration(1500)); //1.5s
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        dropShadow = (DropShadow) textRed.getEffect();

        colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.6);
    }

    private int getBestScore() {
        int bestScore = 0;
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(gameDataPATH));
            bestScore = dataInputStream.readInt();
            dataInputStream.close();
        } catch (IOException ignored) {
        }
        //En caso de no leer nada le asigna el valor 0
        return bestScore;
    }


    //= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    private int correctPokemon;

    /**
     * Método que genera un número aleatorio entero del 0 al numero pasado por parametro
     *
     * @return numero aleatorio generado
     */
    private int getRandomNumberIntType(int number) {
        return (int) (Math.random() * (number));
    }

    private void setNewPokemon() {
        for (Text text : texts) text.setText("");
        //Genera un pokemon aleatorio (el que será correcto) y muestra su imagen y inserta la palabra a uno de los botones
        correctPokemon = getRandomNumberIntType(pokemonArrayList.size());
        //Pone la imagen del pokemon que se encuentra en la ubicacion del numero aleatorio generado
        pokemonMysteryEditionImage.setImage(new Image(pokemonArrayList.get(correctPokemon).getImageMysteryPokemonPATH()));
        //Pilla uno de los campos de textos donde se encuentra las opciones a elegir y le pone el nombre del pokemon el cual es el correcto
        correctPokemonText = texts.get(getRandomNumberIntType(texts.size()));
        correctPokemonText.setText(pokemonArrayList.get(correctPokemon).getName());

        //Rellena el resto de los botones (los que se encuentran vacios) sin repetirse
        //Primero almacena el correcto en un arrayList y luego recorre el resto de los campos de texto y le asigna un nombre sin repetirse
        fillTexts();

    }

    private void fillTexts() {
        //ArrayList donde almacena los nombres para luego chekear si se repiten
        ArrayList<String> names = new ArrayList<>();
        //añade el nombre del pokemon correcto al arraylist
        names.add(correctPokemonText.getText());
        //recorre los campos de textos
        for (Text text : texts) {
            //Si se encuentra vacio le asignara un nombre de un pokemon aleatoriamente
            if (text.getText().equals("")) {
                do {
                    text.setText(pokemonArrayList.get(getRandomNumberIntType(pokemonArrayList.size())).getName());
                    //comprueba si el nombre se repite en caso afirmativo, vuelve a aisgnar otro nombre
                } while (names.contains(text.getText()));
                //añade el nombre nuevo al arrayList
                names.add(text.getText());
            }
        }
        updateTextLayout(texts);
    }

    private void updateTextLayout(ArrayList<Text> texts) {
        for (Text text : texts) {
            int x = (int) (200 - text.getLayoutBounds().getWidth()) / 2;
            text.setLayoutX(x);
        }
    }

    //= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //Añade un efecto a los campos de texto
    @FXML
    public void onMouseEnteredRectangle(MouseEvent event) {
        int fontValor = 30;
        Object source = event.getSource();
        if (paneRed.equals(source)) updateText(textRed, fontValor, colorAdjust);
        else if (paneYellow.equals(source)) updateText(textYellow, fontValor, colorAdjust);
        else if (paneGreen.equals(source)) updateText(textGreen, fontValor, colorAdjust);
        else if (paneBlue.equals(source)) updateText(textBlue, fontValor, colorAdjust);
    }

    @FXML
    public void onMouseExitedRectangle(MouseEvent event) {
        int fontValor = 24;
        Object source = event.getSource();
        if (paneRed.equals(source)) updateText(textRed, fontValor, dropShadow);
        else if (paneYellow.equals(source)) updateText(textYellow, fontValor, dropShadow);
        else if (paneGreen.equals(source)) updateText(textGreen, fontValor, dropShadow);
        else if (paneBlue.equals(source)) updateText(textBlue, fontValor, dropShadow);
    }

    private void updateText(Text text, int fontValor, Effect effect) {
        text.setFont(Font.font(fontValor));
        int x = (int) (200 - text.getLayoutBounds().getWidth()) / 2;
        text.setLayoutX(x);
        text.setEffect(effect);
    }

    //= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //Cuenta atras

    /**
     * Método que lleva la cuenta atrás.
     * Inicializa un pauseTransition que se ejecutará cada segundo siempre que los segundos restantes sean mayor que 0
     * En caso de que se cumpla se irá decrementando 1 en 1 cada segundo y en caso de que llegue a 0 ejecutará el método GameOver
     */
    @FXML
    private void countDown() {
        pauseTransition.setOnFinished(event -> {
            if (counter > 0) {
                if (counter <= 3) countDownLabel.setTextFill(Color.valueOf("#ff8383"));
                else if (counter <= 5) countDownLabel.setTextFill(Color.valueOf("#fa7407"));
                else if (counter < 10) countDownLabel.setTextFill(Color.valueOf("#fae107"));
                else countDownLabel.setTextFill(Color.valueOf("#07fc38"));
                pauseTransition.play();
                countDownLabel.setText(String.valueOf(counter--));
            } else {
                countDownLabel.setText("0");
                gameOver();
            }
        });
        pauseTransition.play();
    }
//    @FXML
//    private void countDown(){ //Otra forma, pero lo malo es que no se puede saber cuando llega a 0
//        Timeline countDownTimeLine = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) ->
//                countDownLabel.setText(String.valueOf(counter--))
//        ));
//        countDownTimeLine.setCycleCount(counter+1);
//        countDownTimeLine.play();
//    }

    //= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    @FXML
    private void onMouseClickedCheckResult(MouseEvent event) {
        checkResult(event.getSource());
    }

    private final String scoreTextText = "Sᴄᴏʀᴇ: ";

    private void checkResult(Object source) {
        if (source.equals(correctPokemonText)) {
            //pausa la cuenta atras
            pauseTransition.pause();
            //transicion a la imagen del pokemon con color
            fadeTransition.play();
            pokemonMysteryEditionImage.setImage(new Image(pokemonArrayList.get(correctPokemon).getImagePokemonPATH()));
            //suma un punto
            score++;
            //actualiza el campo de texto de la puntuacion
            scoreText.setText(scoreTextText + score);
            //desactiva los campos de texto de los nombres de los pokemons
            for (Text text : texts) text.setDisable(true);
            //activa el boton para pasar al siguiente pokemon
            nextButton.setDisable(false);
            nextButton.setVisible(true);
        } else {
            gameOver();
        }
    }

    @FXML
    private void nextButton() {
        //fadeTransition.play();
        setNewPokemon();
        for (Text text : texts) text.setDisable(false);
        nextButton.setDisable(true);
        nextButton.setVisible(false);
        counter = 15;
        pauseTransition.play();
    }

    //= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //Game over
    private void gameOver() {
        try {
            if (score > getBestScore()) {
                DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(gameDataPATH));
                dataOutputStream.writeInt(score);
                dataOutputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gamePanel.setDisable(true);
        gamePanel.setVisible(false);
        gameOverPanel.setDisable(false);
        gameOverPanel.setVisible(true);
    }

    @FXML
    private void onMouseClickedPlayAgain() {
        gameOverPanel.setDisable(true);
        gameOverPanel.setVisible(false);
        gamePanel.setDisable(false);
        counter = 15;
        score = 0;
        scoreText.setText(scoreTextText + score);
        setNewPokemon();
        bestScoreText.setText("Bᴇꜱᴛ ꜱᴄᴏʀᴇ: " + getBestScore());
        countDown();
        gamePanel.setVisible(true);
    }
}