package ControlPanel;

import Cabin.CabinButtons;
import Doors.Door;
import Floors.Floor;
import Request.*;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

class ControlGUI extends Application {

    private final int maxElevators = 4;
    private boolean fireAlarm = false;
    private double imgWidth;

    private ElevatorGUI e1, e2, e3, e4;
    private ElevatorGUI[] elevatorGUIS = new ElevatorGUI[maxElevators];

    ControlGUI(final ControlPanel controlPanel, int floors, int elevators) {
        imgWidth = Toolkit.getDefaultToolkit().getScreenSize().width / 5;
        e1 = (elevators > 0) ? new ElevatorGUI(floors, 1, false, imgWidth) : new ElevatorGUI(floors, 1, true, imgWidth);
        e2 = (elevators > 1) ? new ElevatorGUI(floors, 2, false, imgWidth) : new ElevatorGUI(floors, 2, true, imgWidth);
        e3 = (elevators > 2) ? new ElevatorGUI(floors, 3, false, imgWidth) : new ElevatorGUI(floors, 3, true, imgWidth);
        e4 = (elevators > 3) ? new ElevatorGUI(floors, 4, false, imgWidth) : new ElevatorGUI(floors, 4, true, imgWidth);
        elevatorGUIS[0] = e1;
        elevatorGUIS[1] = e2;
        elevatorGUIS[2] = e3;
        elevatorGUIS[3] = e4;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(e -> primaryStage.close());
        VBox containingScreen = new VBox();
        containingScreen.setSpacing(10);

        Button emergencyButton = new Button("FIRE ALARM");
        emergencyButton.setMaxWidth(imgWidth * maxElevators);
        emergencyButton.getStyleClass().add("fire_alarm");
        emergencyButton.setOnAction(e ->
        {
            fireAlarm = true;
            setAllLocks(true);
        });
        HBox elevatorPanels = new HBox(e1.getElevatorVBox(), e2.getElevatorVBox(), e3.getElevatorVBox(), e4.getElevatorVBox());

        startAnimation();

        elevatorPanels.setSpacing(20);
        containingScreen.getChildren().addAll(elevatorPanels, emergencyButton);
        containingScreen.setAlignment(Pos.CENTER);
        containingScreen.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(containingScreen);
        scene.getStylesheets().add("gui_stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void startAnimation()
    {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for(ElevatorGUI eGUI : elevatorGUIS)
                {
                    eGUI.repaint();
                }
            }
        };

        timer.start();
    }

    ElevatorGUI getElevator(int elevatorNumber)
    {
        return elevatorGUIS[elevatorNumber-1];
    }

    Request getRequest(int elevatorNumber)
    {
        if(elevatorGUIS[elevatorNumber-1].getRequestFlag())
        {
            return elevatorGUIS[elevatorNumber-1].getCurrentRequest();
        }
        else return null;
    }

    void setCabin(Cabin.Cabin cabin, int elevatorNumber)
    {
        elevatorGUIS[elevatorNumber-1].setCabin(cabin);
    }

    void setCabinList(ArrayList<Boolean> cabinList, int elevatorNumber)
    {
        elevatorGUIS[elevatorNumber-1].setCabinButtons(cabinList);
    }

    void setLobbyList(ArrayList<Floor> lobbyList)
    {
        for(ElevatorGUI eGUI : elevatorGUIS) eGUI.setLobbyButtons(lobbyList);
    }

    void setCabinDoorList(ArrayList<Door> cabinDoorList)
    {
        for(int i = 0; i < maxElevators; i++)
        {
            elevatorGUIS[i].setCabinDoorList(cabinDoorList);
        }
    }

    void setDoorList(ArrayList<Door> doorList, int elevatorNumber)
    {
        elevatorGUIS[elevatorNumber-1].setDoorArray(doorList);
    }

    boolean isLocked(int elevatorNumber)
    {
        return elevatorGUIS[elevatorNumber-1].getLock();
    }

    Boolean[] updateLockedElevators()
    {
        Boolean[] locked = new Boolean[4];
        for(int i = 0; i < maxElevators; i++)
        {
            locked[i] = elevatorGUIS[i].getLock();
        }
        return locked;
    }

    Boolean[] updateMaintenanceKeys()
    {
        Boolean[] mKeys = new Boolean[4];
        for(int i = 0; i < maxElevators; i++)
        {
            mKeys[i] = elevatorGUIS[i].getMaintenanceKey();
        }
        return mKeys;
    }

    void lockElevator(int elevatorNumber)
    {
        elevatorGUIS[elevatorNumber-1].setLock(true);
    }

    void setAllLocks(boolean lockStatus)
    {
        for(ElevatorGUI eGUI : elevatorGUIS)
        {
            eGUI.setLock(lockStatus);
        }
    }

    boolean getMaintenanceKey(int elevatorNumber)
    {
        return elevatorGUIS[elevatorNumber-1].getMaintenanceKey();
    }

    boolean getFireAlarm() { return fireAlarm; }
}