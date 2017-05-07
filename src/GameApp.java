import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Mateusz on 07.05.2017.
 */
public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,800,600);
        GameScene gameScene = new GameScene();
        primaryStage.setTitle("Chaos Game");
        root.setCenter(gameScene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
