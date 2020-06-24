package View;

/**
 * @author 姚君彦
 * 2020/6/4,16:40
 * 奇怪的程序增加了
 */

import Data.common;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    //    private TableView table= new TableView;
//    private final ObservableList<record> dataList  = FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        primaryStage.setTitle("littleWatcher by:姚君彦");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.print("监听到窗口关闭");
                common.tryCloseThreads();
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
