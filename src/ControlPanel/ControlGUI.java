package ControlPanel;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import java.awt.image.ImageObserver;

public class ControlGUI extends Stage {

    private int NUM_FLOORS;
    private Integer currentFloor = 0; //Begins at bottom floor
    private Integer requestedFloor;

    private double imgHeight;
    private double imgWidth;
    private final int RATIO = 5;
    private final int cols = 5;
    private double blockSize;

    public void setNUM_FLOORS(int floors) { this.NUM_FLOORS = floors; }

    ControlGUI(final ControlPanel controlPanel, int floors)
    {
        setOnCloseRequest(e -> {
            controlPanel.shutdown();
            close();
        });

        NUM_FLOORS = floors;
        repaint();
    }

    void repaint()
    {
        Image img = new Image(getClass().getResource("/Elevator_Top.png").toString());
        //image height to be used
        imgHeight = img.getHeight()/RATIO;
        imgWidth = img.getWidth()/RATIO;
        Pane top = topPain(imgHeight, imgWidth, img);
        blockSize = imgWidth / cols;

        Canvas canvas = new Canvas(imgWidth, imgWidth);
        GraphicsContext gct = canvas.getGraphicsContext2D();
        setGCT(gct);
        canvas.setOnMouseClicked(e -> doAction(e, gct));

        VBox vBox = new VBox(top, canvas);
        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("gui_stylesheet.css");
        setScene(scene);
        show();
    }

    private void doAction(MouseEvent e, GraphicsContext gct)
    {
        int yval = (int) Math.floor(e.getX() / blockSize);
        int xval = (int) Math.floor(e.getY() / blockSize);
        Integer floor = xval * cols + yval + 1;
        if(floor <= NUM_FLOORS) this.requestedFloor = floor;
        setGCT(gct);
    }

    private void setGCT(GraphicsContext gct)
    {
        Image button = new Image(getClass().getResource("/Button_Grey.png").toString());
        Image buttonOff = new Image(getClass().getResource("/Button_Black.png").toString());
        Image buttonOn = new Image(getClass().getResource("/Button_Yellow.png").toString());

        int rows = 2;
        int floorNumber = 1;
        double offset = blockSize/3;
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                gct.drawImage(button, blockSize * j, blockSize * i, blockSize, blockSize);
                gct.setFont(Font.font("Courier", 30));
                if(requestedFloor != null)
                {
                    if(requestedFloor == floorNumber) gct.drawImage(buttonOn, blockSize * j, blockSize * i, blockSize, blockSize);
                }
                if(floorNumber <= NUM_FLOORS) gct.fillText(String.valueOf(floorNumber),blockSize * j + offset, blockSize * i + 2 *offset);
                else{
                    gct.drawImage(buttonOff,blockSize * j, blockSize * i, blockSize, blockSize);
                }
                floorNumber++;
            }
        }
    }

    private Pane topPain(double imageHeight, double imageWidth, Image img)
    {
        double labelHeight = imageHeight*.25;
        double labelWidth = imageWidth*.55;

        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(imageWidth);
        imageView.setFitHeight(imageHeight);
        imageView.toBack();

        Label cF = new Label(currentFloor.toString());
        cF.getStyleClass().add("floor_label");
        cF.setTranslateX(labelWidth);
        cF.setTranslateY(labelHeight);
        cF.toFront();
        return new Pane(imageView, cF);
    }

    void updateCurrentFloor(Integer floor)
    {
        this.currentFloor = floor;
        Platform.runLater(() -> repaint());
        System.out.println(currentFloor);
    }

    Integer getRequestedFloor() { return requestedFloor; }
}