package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculateController {
    @FXML
    private Label lb1;
    @FXML
    private Label lb2;
    @FXML
    private Label lb3;
    @FXML
    private TextField tf1;
    @FXML
    private TextField tf2;
    @FXML
    private TextField tf3;
    @FXML
    private TextField tf4;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;

    long array[] = new long[255];
    public void getArray(long[] points){
        array = points;
    }

    public void actionButtonPressed (ActionEvent actionEvent){
        int n = array.length;
        double averageSize = 0;
        double externalSurface = 0;
        double averageFreeGap = 0;

        Object source = actionEvent.getSource();
        if (!(source instanceof Button)){
            return;
        }
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()){
            case "btn1":
                Double m = Double.parseDouble(tf1.getText());
                long sum = 0;
                for (int i=0; i < array.length; i++){
                    sum+=array[i];
                }
                averageSize = sum / (n * m);
                lb1.setText("= " + averageSize);
                break;
            case "btn2":
                Double f = Double.parseDouble(tf2.getText());
                Double p = Double.parseDouble(tf3.getText());
                externalSurface = 5.7;
                lb2.setText("= " + externalSurface);
                break;
            case "btn3":
                Double v = Double.parseDouble(tf4.getText());
                averageFreeGap = (Math.sqrt((2 * Math.pow(averageSize, 2)) / v)) * (1 - v);
                lb3.setText("= " + averageFreeGap);
                break;
        }
    }
}
