package dad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.Properties;

public class VentanaApp extends Application {

    private final RootController rootController = new RootController();


    @Override
    public void start(Stage stage) throws Exception {
        Scene memoryWindow = new Scene(rootController.getRoot());

        Stage memoryStage = new Stage();

        memoryStage.setTitle("Memory Window");
        memoryStage.setScene(memoryWindow);
        memoryStage.show();
    }

    @Override
    public void stop() throws Exception {
        File profileFolder = new File(System.getProperty("user.home"));
        File configFolder = new File(profileFolder, ".VentanaConMemoria");
        File configFile = new File(configFolder, "config.properties");

        if (!configFolder.exists()) {
            configFolder.mkdir();
        }

        System.out.println("Saving config: " + configFile);

        FileOutputStream fos = new FileOutputStream(configFile);

        Properties props = new Properties();
        props.setProperty("size.width", "" + rootController.getWidth());
        props.setProperty("size.height", "" + rootController.getHeight());
        props.setProperty("location.x", "" + rootController.getX());
        props.setProperty("location.y", "" + rootController.getY());
        props.store(fos, "Estado de la ventana");
    }
}
