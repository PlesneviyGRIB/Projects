package com.savchenko.snake.config;

import com.savchenko.snake.gamecore.GameData;
import com.savchenko.snake.enums.Complicity;
import com.savchenko.snake.enums.SceneName;
import com.savchenko.snake.gamecore.AllLets;
import com.savchenko.snake.gamecore.Field;
import com.savchenko.snake.gamecore.PointsOfLets;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.savchenko.snake.models.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.savchenko.snake")
public class SpringConfig  extends Application {

    public static Game currentGame;
    private static Stage stage;
    private static Map<SceneName, Scene> sceneMap = new HashMap<>();
    private static Pane settingsPane;

    static {
        try {
            sceneMap.put(SceneName.FIRST, new Scene(FXMLLoader.load(SpringConfig.class.getClassLoader().getResource("com/savchenko/snake/views/FirstPage.fxml"))));
            settingsPane = FXMLLoader.load(SpringConfig.class.getClassLoader().getResource("com/savchenko/snake/views/SettingsPage.fxml"));
            sceneMap.put(SceneName.SETTINGS, new Scene(settingsPane));
            sceneMap.put(SceneName.PAUSE, new Scene(FXMLLoader.load(SpringConfig.class.getClassLoader().getResource("com/savchenko/snake/views/PausePage.fxml"))));
            sceneMap.put(SceneName.QUESTION, new Scene(FXMLLoader.load(SpringConfig.class.getClassLoader().getResource("com/savchenko/snake/views/QuestionPage.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public GameData gameData() {
        int botsCount = (int) ((Slider) settingsPane.getChildren().get(1)).getValue();
        int cntOfFood = (int) ((Slider) settingsPane.getChildren().get(2)).getValue();
        int velocity = (int) ((Slider) settingsPane.getChildren().get(6)).getValue();
        int size = Integer.parseInt( ((ChoiceBox) settingsPane.getChildren().get(8)).getValue().toString());
        Complicity complicity = Complicity.valueOf(((ChoiceBox) settingsPane.getChildren().get(3)).getValue().toString());

        boolean second = ((CheckBox) settingsPane.getChildren().get(12)).isSelected();
        String firstPlayer = ((TextField) settingsPane.getChildren().get(16)).getText();
        String secondPlayer = ((TextField) settingsPane.getChildren().get(19)).getText();

        Field field = new Field(1000,600,  (600 / (1000 / size)), size, (1000/size));

        return new GameData(field, complicity, botsCount, cntOfFood,velocity,second, firstPlayer, secondPlayer);
    }

    @Bean
    public PointsOfLets pointsOfLets(){
        return new PointsOfLets();
    }

    @Bean
    public Game game() throws Exception{
        Game game = new Game(gameData(), new PointsOfLets(), new AllLets());
        Pane pane = (new FXMLLoader(getClass().getClassLoader().getResource("com/savchenko/snake/views/PlayPage.fxml"))).load();
        ImageView button = (ImageView) pane.getChildren().get(0);
        pane.getChildren().clear();

        game.layers().forEach(canvas -> pane.getChildren().add(canvas));
        pane.getChildren().add(button);
        pane.getChildren().add(game.getScoreBoard());

        currentGame = game;
        sceneMap.put(SceneName.PLAY,new Scene(pane));
        return game;
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        switchScene(SceneName.FIRST);
        primaryStage.show();
        stage.setResizable(false);
    }

    public static Scene GetScene(SceneName sceneName){
        return sceneMap.get(sceneName);
    }

    public static void res(List<Map.Entry<String,Integer>> res)  {
        Pane resultsPane = null;
        try {
            resultsPane = FXMLLoader.load(SpringConfig.class.getClassLoader().getResource("com/savchenko/snake/views/ResultsPage.fxml"));
        } catch (Exception e){ e.printStackTrace();}

        StringBuilder stringBuilder = new StringBuilder();
        Map.Entry<String,Integer> entry;

        for(int i = 0; i < 10 && i<res.size(); i++) {
            entry = res.get(i);
            stringBuilder.append(entry.getKey()).append("   ").append(entry.getValue()).append("\n");
        }
        ((Label)resultsPane.getChildren().get(1)).setText(stringBuilder.toString());
        sceneMap.put(SceneName.RESULT, new Scene(resultsPane));
        switchScene(SceneName.RESULT);
    }

    public static void switchScene(SceneName sceneName) {
        stage.setScene(sceneMap.get(sceneName));
    }

    public static void main(String... args) {
        launch();
    }
}
//--module-path ${javafx} --add-modules javafx.fxml,javafx.controls,javafx.graphics