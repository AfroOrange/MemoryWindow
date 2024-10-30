package dad;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    // Properties
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty width = new SimpleDoubleProperty(400);  // default width
    private DoubleProperty height = new SimpleDoubleProperty(300); // default height

    private IntegerProperty red = new SimpleIntegerProperty();
    private IntegerProperty green = new SimpleIntegerProperty();
    private IntegerProperty blue = new SimpleIntegerProperty();

    // View elements
    @FXML
    private Slider redSlider;
    @FXML
    private Slider greenSlider;
    @FXML
    private Slider blueSlider;
    @FXML
    private BorderPane root;

    public RootController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootControllerView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Load saved properties (if any)
        loadProperties();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configure sliders
        setupSlider(redSlider, red);
        setupSlider(greenSlider, green);
        setupSlider(blueSlider, blue);

        // Color change listeners
        red.addListener(this::onColorChange);
        green.addListener(this::onColorChange);
        blue.addListener(this::onColorChange);
    }

    private void setupSlider(Slider slider, IntegerProperty colorProperty) {
        slider.setMin(0);
        slider.setMax(255);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(255);
        slider.setMinorTickCount(5);
        slider.valueProperty().bindBidirectional(colorProperty);
    }

    private void onColorChange(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        Color color = Color.rgb(red.get(), green.get(), blue.get());
        root.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void loadProperties() {
        File configFile = new File(System.getProperty("user.home"), ".VentanaConMemoria/config.properties");

        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                Properties props = new Properties();
                props.load(fis);

                // Load dimensions and position if available
                width.set(Double.parseDouble(props.getProperty("size.width", "400")));
                height.set(Double.parseDouble(props.getProperty("size.height", "300")));
                x.set(Double.parseDouble(props.getProperty("location.x", "100")));
                y.set(Double.parseDouble(props.getProperty("location.y", "100")));
            } catch (IOException e) {
                System.err.println("Failed to load configuration: " + e.getMessage());
            }
        }
    }

    // getters and setters for properties
    public Slider getBlueSlider() {
        return blueSlider;
    }

    public Slider getGreenSlider() {
        return greenSlider;
    }

    public Slider getRedSlider() {
        return redSlider;
    }

    public BorderPane getRoot() {
        return root;
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public int getRed() {
        return red.get();
    }

    public IntegerProperty redProperty() {
        return red;
    }

    public int getGreen() {
        return green.get();
    }

    public IntegerProperty greenProperty() {
        return green;
    }

    public int getBlue() {
        return blue.get();
    }

    public IntegerProperty blueProperty() {
        return blue;
    }
}
