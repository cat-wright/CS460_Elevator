package ControlPanel;

import Doors.Door;
import Doors.DoorState;
import Request.*;
import Floors.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

class ElevatorGUI {

    private int NUM_FLOORS;

    private int elevatorNumber;
    private double imgHeight, imgWidth;
    private double blockSize;

    private Request currentRequest;
    private Integer currentFloor = 1; //Begins at bottom floor

    private boolean maintenanceKey = false;
    private boolean isDisabled;
    private boolean isLocked;

    private VBox elevatorVBox = null;
    private ElevatorSpecs specs = new ElevatorSpecs( false, 1, Directions.UP);

    private Pane topPane;
    private GridPane cabinButtons;
    private GridPane floorButtons;

    private List<Floor> lobbyList = new ArrayList<>();
    private List<Boolean> cabinList = new ArrayList<>();
    private List<Door> doorList = new ArrayList<>();

    private ImageView button = new ImageView(new Image(getClass().getResource("/Button_Grey.png").toString(), blockSize, blockSize, false, false));
    private ImageView buttonOn = new ImageView(new Image(getClass().getResource("/Button_Yellow.png").toString(), blockSize, blockSize, false, false));
    private ImageView buttonOff = new ImageView(new Image(getClass().getResource("/Button_Black.png").toString(), blockSize, blockSize, false, false));

    ElevatorGUI(int floors, int elevatorNumber, boolean disabled, double imgWidth)
    {
        this.imgWidth = imgWidth;
        this.isDisabled = disabled;
        this.NUM_FLOORS = floors;
        this.elevatorNumber = elevatorNumber;
        if(disabled) currentFloor = 0;
    }

    VBox getElevatorVBox()
    {
        createGUI();
        return elevatorVBox;
    }

    void repaint()
    {
        topPane = topPane();
        cabinButtons = cabinButtonStatus();
        floorButtons = floorButtonStatus();
        elevatorVBox.getChildren().set(1, topPane);
        elevatorVBox.getChildren().set(2, cabinButtons);
        elevatorVBox.getChildren().set(3, floorButtons);
    }

    private void createGUI()
    {
        Label elevLabel = new Label("ELEVATOR " + elevatorNumber);
        if(isDisabled) elevLabel.setText("NO ELEVATOR");
        elevLabel.getStyleClass().add("elevator_label");

        topPane = topPane();
        blockSize = imgWidth / NUM_FLOORS;

        cabinButtons = cabinButtonStatus();
        floorButtons = floorButtonStatus();

        Label doorStatusLabel = new Label("Door Status: ");
        doorStatusLabel.getStyleClass().add("title_label");

        VBox doorStatus = getDoorStatus();

        Label controlPanel = new Label("Control Panel: ");
        controlPanel.getStyleClass().add("title_label");

        GridPane controls = makeControlBlock();
        elevatorVBox = new VBox(elevLabel, topPane, cabinButtons, floorButtons, doorStatusLabel, doorStatus, controlPanel, controls);
        elevatorVBox.setAlignment(Pos.CENTER);
        elevatorVBox.setSpacing(5);
    }

    void setSpecs(ElevatorSpecs specs) { this.specs = specs; }

    private GridPane cabinButtonStatus()
    {
        GridPane gridPane = new GridPane();
        StackPane floorButton;

        for(int i = 0; i < NUM_FLOORS; i++)
        {
            int floor = i+1;
            Label floorLabel = new Label(String.valueOf(floor));
            floorButton = new StackPane();
            ImageView imgButton;
            if(cabinList.get(i)) imgButton = buttonOn;
            else imgButton = button;
            floorButton.getChildren().addAll(imgButton, floorLabel);
            gridPane.add(floorButton, i, 0);
        }
//        this.currentRequest = specs.getCurrentRequest();
//        if(currentRequest != null) {
//            StackPane onFloorLight = changeLight(buttonOn, currentRequest);
//            if(onFloorLight != null) gridPane.add(onFloorLight, currentRequest.getDestination()-1, 0);
//        }

//        for(Request req : disabledButtons)
//        {
//            StackPane offFloorLight = changeLight(buttonOff, req);
//            if(offFloorLight != null) gridPane.add(offFloorLight, req.getDestination()-1, 0);
//        }
        return gridPane;
    }

//    StackPane changeLight(Image buttonImage, Request request)
//    {
//        if(request.getType() == Type.CABIN)
//        {
//            Label floorLabel = new Label(String.valueOf(request.getDestination()));
//            ImageView imgButton = new ImageView(buttonImage);
//            return new StackPane(imgButton, floorLabel);
//        }
//        else return null;
//    }

    private GridPane floorButtonStatus()
    {
        GridPane gridPane = new GridPane();
        ImageView upArrow = new ImageView(new Image(getClass().getResource("/up_arrow.png").toString(), blockSize, blockSize, false, false));
        ImageView downArrow = new ImageView(new Image(getClass().getResource("/down_arrow.png").toString(), blockSize, blockSize, false, false));

        int rows = 0;
        while(rows < 2)
        {
            StackPane lobbyButton;
            ImageView direction;

            for(int i = 0; i < NUM_FLOORS; i++)
            {
                if(rows == 0) direction = upArrow;
                else
                {
                    direction = downArrow;
                }
                ImageView imgButton = button;
                if(isLocked) imgButton = buttonOff;
                else
                {
                    if(rows == 0) if(lobbyList.get(i).isUpButton()) imgButton = buttonOn;
                    else if(lobbyList.get(i).isDownButton()) imgButton = buttonOn;
                    else imgButton = button;
                }
                lobbyButton = new StackPane();
                lobbyButton.getChildren().addAll(imgButton, direction);
                gridPane.add(lobbyButton, i, rows);
            }
            rows++;
        }
        return gridPane;
    }

    private VBox getDoorStatus()
    {
        VBox doors;
        GridPane legend = new GridPane();
        ImageView openDoor = new ImageView(new Image(getClass().getResource("/Door_Open.png").toString(), blockSize, blockSize, false, false));
        ImageView movingDoor = new ImageView(new Image(getClass().getResource("/Door_Moving.png").toString(), blockSize, blockSize, false, false));
        ImageView closedDoor = new ImageView(new Image(getClass().getResource("/Door_Closed.png").toString(), blockSize, blockSize, false, false));

        Label openLabel = new Label(":OPEN");
        openLabel.getStyleClass().add("door_label");
        Label movingLabel = new Label(":MOVING");
        movingLabel.getStyleClass().add("door_label");
        Label closedLabel = new Label(":CLOSED");
        closedLabel.getStyleClass().add("door_label");

        legend.addRow(0, openDoor, openLabel, movingDoor, movingLabel, closedDoor, closedLabel);
        legend.setMaxWidth(imgWidth);
        legend.setHgap(10);

        GridPane floorGrid = new GridPane();
        for(int i = 0; i < NUM_FLOORS; i++)
        {
            Label floorLabel = new Label(String.valueOf(i+1));
            floorGrid.setHalignment(floorLabel, HPos.CENTER);
            floorGrid.add(floorLabel, i, 0);
            if(doorList.get(i).getDoorState() == DoorState.CLOSED) floorGrid.add(closedDoor, i, 1);
            else if(doorList.get(i).getDoorState() == DoorState.OPENED) floorGrid.add(openDoor, i, 1);
            else floorGrid.add(movingDoor, i, 1);
        }
        floorGrid.setMaxWidth(imgWidth);

        Label cabinLabel = new Label("CABIN DOOR: ");
        cabinLabel.setTranslateX(imgWidth/4);
        cabinLabel.setContentDisplay(ContentDisplay.RIGHT);
        cabinLabel.setGraphic(closedDoor);


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

        Label goToFloor = new Label("Send Request: ");
        ComboBox<Integer> comboBox = makeFloorComboBox();
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll(
                "Cabin",
                "Up",
                "Down"
        );
        Button goToFloorButton = new Button("Go!");
        if(isDisabled) goToFloorButton.setDisable(true);
        goToFloorButton.setOnAction(e -> {
            Type type;
            if(typeComboBox.getValue().equals("Cabin")) type = Type.CABIN;
            else type = Type.FLOOR;
            currentRequest = new Request(comboBox.getValue(), type);
            System.out.println("New Request to send Elevator to Floor " + comboBox.getValue());
        });
        gridPane.addRow(0, goToFloor, comboBox, typeComboBox, goToFloorButton);

        Label lockLabel = new Label("Lock Elevator: ");
        CheckBox lockBox = new CheckBox();
        if(isDisabled) lockBox.setDisable(true);
        lockBox.setOnAction(e -> isLocked = lockBox.isSelected());
        gridPane.addRow(1, lockLabel, lockBox);

        Label maintenanceKeyLabel = new Label("Maintenance Key: ");
        CheckBox maintenanceBool = new CheckBox();
        if(isDisabled) maintenanceBool.setDisable(true);
        maintenanceBool.setOnAction(e -> maintenanceKey = maintenanceBool.isSelected());

        gridPane.addRow(2, maintenanceKeyLabel, maintenanceBool);

        return gridPane;
    }

    private ComboBox<Integer> makeFloorComboBox()
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

        if(isDisabled) currentFloor = 0;
        else currentFloor = specs.getCurrentFloor();
        Label currentFloor = new Label(this.currentFloor.toString());
        currentFloor.getStyleClass().add("floor_label");
        currentFloor.setTranslateX(imgWidth*.5);
        currentFloor.setTranslateY(imageView.getFitHeight()*.3);
        currentFloor.toFront();
        Pane pane;
        if(specs.isMoving())
        {
            if (specs.getDirection() == Directions.UP) moving.setText("Moving Up");
            else moving.setText("Moving Down");
            pane = new Pane(imageView, currentFloor, moving);
        }
        else pane = new Pane(imageView, currentFloor);
        return pane;
    }

    boolean getLock() { return isLocked; }

    void setLock(boolean lock)
    {
        isLocked = lock;
    }

    void setCabinButtons(ArrayList<Boolean> cabinList)
    {
        this.cabinList = cabinList;
    }

    void setLobbyButtons(ArrayList<Floor> lobbyList) { this.lobbyList = lobbyList; }

    void setDoorArray(ArrayList<Door> doorList) { this.doorList = doorList; }

    Request getCurrentRequest()
    {
        return currentRequest;
    }

    boolean getMaintenanceKey()
    {
        return maintenanceKey;
    }
}
