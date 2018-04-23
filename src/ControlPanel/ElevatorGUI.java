package ControlPanel;

import Cabin.CabinButtons;
import Doors.Door;
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
import java.util.LinkedList;
import java.util.List;

class ElevatorGUI {

    private int NUM_FLOORS;

    private int elevatorNumber;
    private double imgHeight, imgWidth;
    private double blockSize;

    private Request currentRequest;
    private boolean flag = false;
    private boolean ableFlag = false;
    private Integer currentFloor = 1; //Begins at bottom floor

    private boolean maintenanceKey = false;
    private boolean isDisabled;
    private boolean isLocked;

    private LinkedList<Request> requestedFloors = new LinkedList<>();

    private VBox elevatorVBox = null;
    private ElevatorSpecs specs = new ElevatorSpecs(false, null, 1, Directions.UP);

    private Pane topPane;
    private GridPane cabinButtons;
    private GridPane floorButtons;

    private List<Floor> lobbyList = new ArrayList<>();
    private List<CabinButtons> cabinList = new ArrayList<>();
    private List<Door> doorList = new ArrayList<>();

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

    void createGUI()
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

        if(specs != null) this.currentRequest = specs.getCurrentRequest();
        if(currentRequest != null) {
            StackPane onFloorLight = changeLight(buttonOn, currentRequest);
            if(onFloorLight != null) gridPane.add(onFloorLight, currentRequest.getDestination()-1, 0);
        }

//        for(Request req : disabledButtons)
//        {
//            StackPane offFloorLight = changeLight(buttonOff, req);
//            if(offFloorLight != null) gridPane.add(offFloorLight, req.getDestination()-1, 0);
//        }

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

    private GridPane floorButtonStatus()
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
                if(specs != null) this.currentRequest = specs.getCurrentRequest();
                if(currentRequest != null)
                {
                    if(currentRequest.getType() == Type.FLOOR)
                    {
                        if(currentRequest.getDestination() == i+1)
                        {
                            if(currentRequest.getDirection() == Directions.UP && rows == 0) imgButton.setImage(buttonOn);
                            else if(currentRequest.getDirection() == Directions.DOWN && rows == 1) imgButton.setImage(buttonOn);
                        }
                    }
                }
                lobbyButton = new StackPane();
                lobbyButton.getChildren().addAll(imgButton, direction);
                gridPane.add(lobbyButton, i, rows);
            }
            rows++;
        }

//        for(Request req : disabledButtons)
//        {
//
//            if(req.getType() == Type.FLOOR)
//            {
//                int row;
//                ImageView direction = new ImageView(downArrow);
//                if(req.getDirection() == Directions.UP) {
//                    row = 0;
//                    direction.setImage(upArrow);
//                }
//                else row = 1;
//
//                int floor = req.getDestination();
//                ImageView imgButton = new ImageView(buttonOff);
//                StackPane lobbyButton = new StackPane(imgButton);
//                gridPane.add(lobbyButton, floor-1 ,row);
//            }
//        }


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
            Request newRequest = new Request(comboBox.getValue(), type);
            currentRequest = newRequest;
            flag = true;
            requestedFloors.add(newRequest);
            System.out.println("New Request to send Elevator to Floor " + comboBox.getValue());
        });
        gridPane.addRow(0, goToFloor, comboBox, typeComboBox, goToFloorButton);

        Label lockLabel = new Label("Lock Elevator: ");
        CheckBox lockBox = new CheckBox();
        if(isDisabled) lockBox.setDisable(true);
        lockBox.setOnAction(e -> {
            if(lockBox.isSelected()) isLocked = true;
        });
        gridPane.addRow(1, lockLabel, lockBox);

        Label maintenanceKeyLabel = new Label("Maintenance Key: ");
        CheckBox maintenanceBool = new CheckBox();
        if(isDisabled) maintenanceBool.setDisable(true);
        maintenanceBool.setOnAction(e -> {
            if(maintenanceBool.isSelected()) maintenanceKey = true;
            else maintenanceKey = false;
        });

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

    void setCabinButtons(ArrayList<CabinButtons> cabinList)
    {
        this.cabinList = cabinList;
    }

    void setLobbyButtons(ArrayList<Floor> lobbyList) { this.lobbyList = lobbyList; }

    void setDoorArray(ArrayList<Door> doorList) { this.doorList = doorList; }

    LinkedList<Request> getRequestedFloors() { return requestedFloors; }

    Request getCurrentRequest()
    {
        return currentRequest;
    }

    synchronized void setCurrentRequest(Request currentRequest)
    {
        this.currentRequest = currentRequest;
    }

    boolean getMaintenanceKey()
    {
        return maintenanceKey;
    }

//    boolean getFlag()
//    {
//        return flag;
//    }
//
//    void setFlag(boolean flag) { this.flag = flag; }
//
//    boolean getAbleFlag() { return ableFlag; }
//
//    void setAbleFlag(boolean ableFlag) { this.ableFlag = ableFlag; }
}
