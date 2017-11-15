package sample.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.start.Main;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    private Stage mainStage;

    @FXML
    private ImageView imgView;
    @FXML
    private Button btnOpen;
    @FXML
    private Button btnHistogram;
    @FXML
    private Button btnBrightness;
    @FXML
    private Slider opacityLevel;
    @FXML
    private Slider sepiaTone;
    @FXML
    private Slider scaling;
    @FXML
    private Slider contrast;
    @FXML
    private Slider hue;
    @FXML
    private Slider saturation;
    @FXML
    private Label opacityValue;
    @FXML
    private Label sepiaValue;
    @FXML
    private Label scalingValue;
    @FXML
    private Label hueValue;
    @FXML
    private Label saturationValue;
    @FXML
    private Label contrastValue;

    private Parent fxmlW;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private HistogramController histogramController;
    private Stage histogramStage;
    final static SepiaTone sepiaEffect = new SepiaTone();
    ColorAdjust colorAdjust = new ColorAdjust();

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void actionButtonPressed (ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)){
            return;
        }
        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/histogram.fxml"));
            fxmlW = fxmlLoader.load();
            histogramController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()){
            case "btnOpen" :
                open();
                break;
            case "btnHistogram" :
                Image im = imgView.snapshot(new SnapshotParameters(), null);
                histogramController.Histogram(im);
                showDialog();
                break;
            case "btnBrightness" :
                im = imgView.snapshot(new SnapshotParameters(), null);
                histogramController.BrightnessHistogram(im);
                showDialog();
        }

        opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                imgView.setOpacity(new_val.doubleValue());
                opacityValue.setText(String.format("%.2f", new_val));
            }
        });

        sepiaTone.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                imgView.setEffect(sepiaEffect);
                sepiaEffect.setLevel(new_val.doubleValue());
                sepiaValue.setText(String.format("%.2f", new_val));
            }
        });

        scaling.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                imgView.setScaleX(new_val.doubleValue());
                imgView.setScaleY(new_val.doubleValue());
                scalingValue.setText(String.format("%.2f", new_val));
            }
        });

        contrast.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                colorAdjust.setContrast(new_val.doubleValue());
                imgView.setEffect(colorAdjust);
                contrastValue.setText(String.format("%.2f", new_val));
            }
        });

        hue.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                colorAdjust.setHue(new_val.doubleValue());
                imgView.setEffect(colorAdjust);
                hueValue.setText(String.format("%.2f", new_val));
            }
        });

        saturation.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                colorAdjust.setSaturation(new_val.doubleValue());
                imgView.setEffect(colorAdjust);
                saturationValue.setText(String.format("%.2f", new_val));
            }
        });
    }

    private void showDialog() {
        if (histogramStage ==null){
            histogramStage = new Stage();
            histogramStage.setTitle("Histogram");
            histogramStage.setMinHeight(247);
            histogramStage.setMinWidth(374);
            histogramStage.setScene(new Scene(fxmlW));
            histogramStage.initOwner(mainStage);
        }
        histogramStage.showAndWait();
    }

    private void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter( "JPG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image img = new Image(file.toURI().toString());
            imgView.setImage(img);
        }
    }

    public void save(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter( "jpg files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(mainStage);
        WritableImage writableImage = imgView.snapshot(new SnapshotParameters(), null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);


        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void close() {
        mainStage.close();
    }
}
