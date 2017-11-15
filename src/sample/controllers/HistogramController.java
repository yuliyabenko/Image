package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.classes.CsvWriter;
import sample.classes.ImageHistogram;
import java.io.File;
import java.io.IOException;

public class HistogramController {
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private Label lb;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnSaveCsv;
    @FXML
    private Button btnCalculate;
    @FXML
    private TextField txtLimit;

    private CalculateController calculateController;
    private Stage calculateStage;
    private Parent fxmlW;
    private Stage twoStage;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    ImageHistogram currentHist;
    long points[]  = new long[255];

    public void Histogram(Image img){
        lineChart.getData().clear();
        ImageHistogram imageHistogram = new ImageHistogram(img);
        currentHist = imageHistogram;
        if(imageHistogram.isSuccess()){
            lineChart.getData().addAll(
                    imageHistogram.getSeriesRed(),
                    imageHistogram.getSeriesGreen(),
                    imageHistogram.getSeriesBlue());
        }
    }

    public void BrightnessHistogram(Image img){
        lineChart.getData().clear();
        ImageHistogram imageHistogram = new ImageHistogram(img);
        currentHist = imageHistogram;
        if(imageHistogram.isSuccess()){
            lineChart.getData().addAll(imageHistogram.getSeriesBrightness());
        }
    }

    public void actionButtonPressed (ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)){
            return;
        }
        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/calculate.fxml"));
            fxmlW = fxmlLoader.load();
            calculateController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button clickedButton = (Button) source;
        long limit = Long.parseLong(txtLimit.getText());
        switch (clickedButton.getId()){
            case "btnOk":
                findExtremes(limit);
                break;
            case "btnSaveCsv":
                save();
                break;
            case "btnCalculate":
                calculateController.getArray(points);
                showDialog();
                break;
        }
    }

    private void save(){
        Stage secondStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(secondStage);
        if (file != null){
            CsvWriter writer = new CsvWriter();
            writer.writeCsv(file.getAbsolutePath(), points);
        }
    }

    public void findExtremes(long limit) {
        int y = 0;
        long brightness[] = currentHist.getBrightness();
        for (int i = 0; i < brightness.length; i++) {
            if (brightness[i] >= limit) {
                points[y] = brightness[i];
                y++;
            }
        }
    }

    private void showDialog() {
        if (calculateStage ==null){
            calculateStage = new Stage();
            calculateStage.setTitle("Calculation");
            calculateStage.setMinHeight(247);
            calculateStage.setMinWidth(374);
            calculateStage.setScene(new Scene(fxmlW));
            calculateStage.initOwner(twoStage);
        }
        calculateStage.showAndWait();
    }

    public void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
