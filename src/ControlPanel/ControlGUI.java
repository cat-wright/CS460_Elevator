package ControlPanel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

public class ControlGUI extends Stage {

    private Integer currentFloor = 8;
    private Integer requestedFloor = null;

    public ControlGUI(final ControlPanel controlPanel)
    {
        setOnCloseRequest(e -> {
            controlPanel.shutdown();
            close();
        });
        //Pane pane = FXMLLoader.load(getClass().getResource("fxmlDoc.fxml"));

        Image img = new Image(getClass().getResource("/Elevator_Top.png").toString());
        int ratio = 3;
        //image height to be used
        double imgHeight = img.getHeight()/ratio;
        double imgWidth = img.getWidth()/ratio;
        Pane top = topPain(imgHeight, imgWidth, img);



        setScene(new Scene(top, imgWidth, imgHeight));
        show();
    }

    Pane topPain(double imageHeight, double imageWidth, Image img)
    {
        double labelHeight = imageHeight*.25;
        double labelWidth = imageWidth*.55;

        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(imageWidth);
        imageView.setFitHeight(imageHeight);
        imageView.toBack();

        Label cF = new Label(currentFloor.toString());
        cF.setTranslateX(labelWidth);
        cF.setTranslateY(labelHeight);
        cF.toFront();
        cF.setTextFill(Color.RED);
        cF.setFont(Font.font("Courier", 90));

        return new Pane(imageView, cF);
    }

    void setCurrentFloor(Integer floor) { this.currentFloor = floor; }

    Integer getRequestedFloor() { return requestedFloor; }
}