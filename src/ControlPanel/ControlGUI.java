package ControlPanel;

import Request.*;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;

class ControlGUI extends Stage {

    private int NUM_FLOORS;
    private int NUM_ELEVS;
    private Integer currentFloor = 1; //Begins at bottom floor
    private Integer requestedFloor;

    private double imgHeight;
    private double imgWidth;
    private double blockSize;

    private boolean isMoving;
    private Enum currentDirection;
    private Request request;

    private boolean maintenanceKey;

    private ArrayList<Request> disabledButtons = new ArrayList<>();

    ControlGUI(final ControlPanel controlPanel, int floors, int elevators)
    {
        setOnCloseRequest(e -> {
            controlPanel.shutdown();
            close();
        });

        NUM_FLOORS = floors;
        NUM_ELEVS = elevators;
        imgWidth = Toolkit.getDefaultToolkit().getScreenSize().width/5;

        repaint();
    }

    private void repaint()
    {
        VBox holdingBox = new VBox();
        holdingBox.setPadding(new Insets(10, 10, 10, 10));

        HBox panels = new HBox();
        panels.setSpacing(10);
        Button emergencyButton = new Button("FIRE ALARM");
        emergencyButton.setMaxWidth(imgWidth*NUM_ELEVS);
        emergencyButton.getStyleClass().add("fire_alarm");

        for(int i = 0; i < NUM_ELEVS; i++)
        {
            panels.getChildren().add(new VBox(redrawElevatorPane(i+1)));
        }
        holdingBox.getChildren().addAll(panels, emergencyButton);


        Scene scene = new Scene(holdingBox);
        scene.getStylesheets().add("gui_stylesheet.css");
        setScene(scene);
        show();
    }

    VBox redrawElevatorPane(int elevatorNumber)
    {
        Label elevLabel = new Label("ELEVATOR " + elevatorNumber);
        elevLabel.getStyleClass().add("elevator_label");

        Pane top = topPane();
        blockSize = imgWidth / NUM_FLOORS;

        GridPane floorButtons = floorButtonStatus();
        GridPane lobbyButtons = lobbyButtonStatus();

        Label doorStatusLabel = new Label("Door Status: ");
        doorStatusLabel.getStyleClass().add("title_label");

        VBox doorStatus = getDoorStatus();

        Label controlPanel = new Label("Control Panel: ");
        controlPanel.getStyleClass().add("title_label");

        GridPane controls = makeControlBlock();

        VBox elevator = new VBox(elevLabel, top, floorButtons, lobbyButtons, doorStatusLabel, doorStatus, controlPanel, controls);
        elevator.setAlignment(Pos.CENTER);
        elevator.setSpacing(5);

        return elevator;
    }

    private GridPane floorButtonStatus()
    {
        GridPane gridPane = new GridPane();
        Image button = new Image(getClass().getResource("/Button_Grey.png").toString(), blockSize, blockSize, false, false);
        Image buttonOff = new Image(getClass().getResource("/Button_Black.png").toString(), blockSize, blockSize, false, false);
        Image buttonOn = new Image(getClass().getResource("/Button_Yellow.png").toString(), blockSize, blockSize, false, false);

        StackPane floorButton;

        for(int i = 0; i < NUM_FLOORS; i++)
        {
            int floor = i+1;
            Label floorLabel = new Label(String.valueOf(floor));
            ImageView imgButton = new ImageView(button);
            floorButton = new StackPane();
            floorButton.getChildren().addAll(imgButton, floorLabel);
            gridPane.add(floorButton, i, 0);
        }

        if(request != null) {
            StackPane onFloorLight = changeLight(buttonOn, request);
            if(onFloorLight != null) gridPane.add(onFloorLight, request.getDestination()-1, 0);
        }

        for(Request req : disabledButtons)
        {
            StackPane offFloorLight = changeLight(buttonOff, req);
            if(offFloorLight != null) gridPane.add(offFloorLight, req.getDestination()-1, 0);
        }

        return gridPane;
    }

    StackPane changeLight(Image buttonImage, Request request)
    {
        if(request.getType() == Type.CABIN)
        {
            Label floorLabel = new Label(String.valueOf(request.getDestination()));
            ImageView imgButton = new ImageView(buttonImage);
            return new StackPane(imgButton, floorLabel);
        }
        else return null;
    }

    private GridPane lobbyButtonStatus()
    {
        GridPane gridPane = new GridPane();
        Image button = new Image(getClass().getResource("/Button_Grey.png").toString(), blockSize, blockSize, false, false);
        Image buttonOff = new Image(getClass().getResource("/Button_Black.png").toString(),blockSize, blockSize, false, false);
        Image buttonOn = new Image(getClass().getResource("/Button_Yellow.png").toString(),blockSize, blockSize, false, false);

        Image upArrow = new Image(getClass().getResource("/up_arrow.png").toString(), blockSize, blockSize, false, false);
        Image downArrow = new Image(getClass().getResource("/down_arrow.png").toString(), blockSize, blockSize, false, false);

        int rows = 0;
        while(rows < 2)
        {
            StackPane lobbyButton;
            ImageView direction;

            for(int i = 0; i < NUM_FLOORS; i++)
            {
                if(rows == 0) direction = new ImageView(upArrow);
                else
                {
                    direction = new ImageView(downArrow);
                }
                ImageView imgButton = new ImageView(button);
                if(request != null)
                {
                    if(request.getType() == Type.FLOOR)
                    {
                        if(request.getDestination() == i+1)
                        {
                            if(request.getDirection() == Directions.UP && rows == 0) imgButton.setImage(buttonOn);
                            else if(request.getDirection() == Directions.DOWN && rows == 1) imgButton.setImage(buttonOn);
                        }
                    }
                }
                lobbyButton = new StackPane();
                lobbyButton.getChildren().addAll(imgButton, direction);
                gridPane.add(lobbyButton, i, rows);
            }
            rows++;
        }

        for(Request req : disabledButtons)
        {

            if(req.getType() == Type.FLOOR)
            {
                int row;
                ImageView direction = new ImageView(downArrow);
                if(req.getDirection() == Directions.UP) {
                    row = 0;
                    direction.setImage(upArrow);
                }
                else row = 1;

                int floor = req.getDestination();
                ImageView imgButton = new ImageView(buttonOff);
                StackPane lobbyButton = new StackPane(imgButton);
                gridPane.add(lobbyButton, floor-1 ,row);
            }
        }


        return gridPane;
    }

    private VBox getDoorStatus()
    {
        VBox doors;
        GridPane legend = new GridPane();
        Image openDoor = new Image(getClass().getResource("/Door_Open.png").toString(), blockSize, blockSize, false, false);
        Image movingDoor = new Image(getClass().getResource("/Door_Moving.png").toString(), blockSize, blockSize, false, false);
        Image closedDoor = new Image(getClass().getResource("/Door_Closed.png").toString(), blockSize, blockSize, false, false);

        Label openLabel = new Label(":OPEN");
        openLabel.getStyleClass().add("door_label");
        Label movingLabel = new Label(":MOVING");
        movingLabel.getStyleClass().add("door_label");
        Label closedLabel = new Label(":CLOSED");
        closedLabel.getStyleClass().add("door_label");

        legend.addRow(0, new ImageView(openDoor), openLabel, new ImageView(movingDoor), movingLabel, new ImageView(closedDoor), closedLabel);
        legend.setMaxWidth(imgWidth);
        legend.setHgap(10);

        GridPane floorGrid = new GridPane();
        for(int i = 0; i < NUM_FLOORS; i++)
        {
            Label floorLabel = new Label(String.valueOf(i+1));
            floorGrid.setHalignment(floorLabel, HPos.CENTER);
            floorGrid.add(floorLabel, i, 0);
            floorGrid.add(new ImageView(closedDoor), i, 1);
        }

        floorGrid.setMaxWidth(imgWidth);

        Label cabinLabel = new Label("CABIN DOOR: ");
        cabinLabel.setTranslateX(imgWidth/4);
        cabinLabel.setContentDisplay(ContentDisplay.RIGHT);
        cabinLabel.setGraphic(new ImageView(closedDoor));


        doors = new VBox(legend, floorGrid, cabinLabel);
        return doors;
    }

    private GridPane makeControlBlock()
    {
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(imgWidth);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //gridPane.setMaxWidth(imgWidth);
        Label goToFloor = new Label("Go To Floor: ");
        ComboBox<Integer> comboBox = makeFloorComboBox();
        Button goToFloorButton = new Button("Launch");
        goToFloorButton.setOnAction(e -> requestedFloor = (Integer) comboBox.getValue());
        gridPane.addRow(0, goToFloor, comboBox, goToFloorButton);

        Label AbleDisableButtons = new Label("Able/Disable: ");
        ComboBox<Integer> floorNumberBox = makeFloorComboBox();
        ComboBox<String> buttonComboBox = new ComboBox<>();
        buttonComboBox.getItems().addAll(
                "Cabin",
                "Up",
                "Down"
        );
        Button ableDisableButton = new Button("Launch");
        ableDisableButton.setOnAction(e -> {
            Type type;
            Integer floor = floorNumberBox.getValue();
            if((buttonComboBox.getValue()).equals("Cabin")) type = Type.CABIN;
            else type = Type.FLOOR;
            Request requestToAlter = new Request(floor, type);

            if(buttonComboBox.getValue().equals("Up")) requestToAlter.setDirection(Directions.UP);
            else if(buttonComboBox.getValue().equals("Down")) requestToAlter.setDirection(Directions.DOWN);
            if(disabledButtons.contains(requestToAlter)) disabledButtons.remove(requestToAlter);
            else disabledButtons.add(requestToAlter);
            Platform.runLater(() -> repaint());
        });
        gridPane.addRow(1, AbleDisableButtons, floorNumberBox, buttonComboBox, ableDisableButton);

        Label maintenanceKeyLabel = new Label("Maintenance Key: ");
        CheckBox maintenanceBool = new CheckBox();
        maintenanceBool.setOnAction(e -> {
            if(maintenanceBool.isSelected()) maintenanceKey = true;
            else maintenanceKey = false;
        });

        gridPane.addRow(2, maintenanceKeyLabel, maintenanceBool);

        return gridPane;
    }

    ComboBox<Integer> makeFloorComboBox()
    {
        ComboBox<Integer> comboBox = new ComboBox<>();
        Integer integer = 1;
        for(int i = 0; i < NUM_FLOORS; i++)
        {
            comboBox.getItems().add(integer);
            integer++;
        }
        return comboBox;
    }

    private Pane topPane()
    {
        Image img = new Image(getClass().getResource("/Elevator_Top.png").toString());

        imgHeight = (imgWidth/img.getWidth())*img.getHeight();
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(imgWidth);
        imageView.setFitHeight(imgHeight);
        imageView.toBack();

        Label moving = new Label();
        moving.setTranslateX(imgWidth/3);
        moving.setTranslateY(imgHeight/2);
        moving.getStyleClass().add("moving_label");

        Label currentFloor = new Label(this.currentFloor.toString());
        currentFloor.getStyleClass().add("floor_label");
        currentFloor.setTranslateX(imgWidth*.5);
        currentFloor.setTranslateY(imageView.getFitHeight()*.3);
        currentFloor.toFront();
        Pane pane;
        if(isMoving) {
            if (currentDirection == Directions.UP) moving.setText("Moving Up");
            else moving.setText("Moving Down");
            pane = new Pane(imageView, currentFloor, moving);
        }
        else pane = new Pane(imageView, currentFloor);

        return pane;
    }

    void updateCurrentFloor(Integer floor)
    {
        this.currentFloor = floor;
        Platform.runLater(() -> repaint());
        System.out.println(currentFloor);
    }

    Integer getRequestedFloor() { return requestedFloor; }

    synchronized void setIsMoving(boolean isMoving, Directions direction)
    {
        this.isMoving = isMoving;
        this.currentDirection = direction;
        Platform.runLater(() -> repaint());
    }

    ArrayList<Request> getDisabledButtons()
    {
        return disabledButtons;
    }

    synchronized void setRequest(Request request)
    {
        this.request = request;
        Platform.runLater(() -> repaint());
    }

    boolean getMaintenanceKey()
    {
        return maintenanceKey;
    }
}