package ControlPanel;

import Request.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.util.LinkedList;

class ControlGUI extends Stage {

    private int NUM_FLOORS, NUM_ELEVS;
    private final int maxElevators = 4;
    //private Integer requestedFloor;

    private boolean fireAlarm = false;

    private double imgWidth;

    private Integer currentFloor; //Begins at bottom floor

    private LinkedList<Request> disabledButtons = new LinkedList<>();
    private LinkedList<Request> currentRequests = new LinkedList<>();

    private ElevatorGUI e1, e2, e3, e4;
    private ElevatorGUI[] elevatorGUIS = new ElevatorGUI[maxElevators];
    ;

    ControlGUI(final ControlPanel controlPanel, int floors, int elevators) {
        setOnCloseRequest(e -> {
            controlPanel.shutdown();
            close();
        });

        NUM_FLOORS = floors;
        NUM_ELEVS = elevators;
        imgWidth = Toolkit.getDefaultToolkit().getScreenSize().width / 5;
        e1 = (elevators > 0) ? new ElevatorGUI(floors, 1, false, imgWidth) : new ElevatorGUI(floors, 1, true, imgWidth);
        e2 = (elevators > 1) ? new ElevatorGUI(floors, 2, false, imgWidth) : new ElevatorGUI(floors, 2, true, imgWidth);
        e3 = (elevators > 2) ? new ElevatorGUI(floors, 3, false, imgWidth) : new ElevatorGUI(floors, 3, true, imgWidth);
        e4 = (elevators > 3) ? new ElevatorGUI(floors, 4, false, imgWidth) : new ElevatorGUI(floors, 4, true, imgWidth);
        elevatorGUIS[0] = e1;
        elevatorGUIS[1] = e2;
        elevatorGUIS[2] = e3;
        elevatorGUIS[3] = e4;

        repaint();
    }

//    private synchronized void updateElevators() {
//        for (int i = 0; i < NUM_ELEVS; i++) {
//            if(elevatorGUIS[i] != null)
//            {
//                if(elevatorGUIS[i].getRequestedFloors().size() > 0)
//                {
//                    if (elevatorGUIS[i].getRequestedFloors() != null) currentRequests.addAll(elevatorGUIS[i].getRequestedFloors());
//                }
//                if(elevatorGUIS[i].getDisabledButtons().size() > 0)
//                {
//                    if (elevatorGUIS[i].getDisabledButtons() != null) disabledButtons.addAll(elevatorGUIS[i].getDisabledButtons());
//                }
//            }
//        }
//    }

//    void checkForRepaint()
//    {
//        //updateElevators();
//        for(int i = 0; i < NUM_ELEVS; i++)
//        {
//            if(elevatorGUIS[i] != null)
//            {
//                if(elevatorGUIS[i].getFlag()) {
//                    Platform.runLater(() -> repaint());
//                    elevatorGUIS[i].setFlag(false);
//                }
//                if(elevatorGUIS[i].getAbleFlag()) {
//                    Platform.runLater(() -> repaint());
//                    elevatorGUIS[i].setAbleFlag(false);
//                }
//            }
//        }
//    }

    void repaint() {
        VBox containingScreen = new VBox();
        containingScreen.setSpacing(10);

        Button emergencyButton = new Button("FIRE ALARM");
        emergencyButton.setMaxWidth(imgWidth * maxElevators);
        emergencyButton.getStyleClass().add("fire_alarm");
        emergencyButton.setOnAction(e -> fireAlarm = true);

        HBox elevatorPanels = new HBox(e1.getElevatorVBox(), e2.getElevatorVBox(), e3.getElevatorVBox(), e4.getElevatorVBox());
        elevatorPanels.setSpacing(20);
        containingScreen.getChildren().addAll(elevatorPanels, emergencyButton);
        containingScreen.setAlignment(Pos.CENTER);
        containingScreen.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(containingScreen);
        scene.getStylesheets().add("gui_stylesheet.css");
        setScene(scene);
        show();
    }

    void updateCurrentFloor(Integer floor) {
        this.currentFloor = floor;
        //Platform.runLater(() -> repaint());
        System.out.println(currentFloor);
    }

//    Integer getRequestedFloor() {
//        return requestedFloor;
//    }

    Request getRequest() {
        if(e1.getFlag()) {
            e1.setFlag(false);
            return e1.getCurrentRequest();
        }
//        if(currentRequests.size() > 0)
//        {
//            Request nextRequest = currentRequests.getFirst();
//            currentRequests.removeFirst();
//            System.out.println("Size of requests: " + currentRequests.size());
//            return nextRequest;
//        }
        else return null;
    }

    LinkedList<Request> getDisabledButtons() {
        if(e1.getAbleFlag())
        {
            Platform.runLater(() -> repaint());
            e1.setAbleFlag(false);
        }
        return disabledButtons;
    }

//    void setRequest(Request request) {
//        this.request = request;
//        e1.setCurrentRequest(request);
//        Platform.runLater(() -> repaint());
//    }

    boolean getMaintenanceKey() {
        return e1.getMaintenanceKey();
    }

    synchronized void getSpecs(ElevatorSpecs specs)
    {
        e1.setSpecs(specs);
        Platform.runLater(() -> repaint());
    }

    boolean getFireAlarm() { return fireAlarm; }

    void setFireAlarm(boolean fireAlarm) { this.fireAlarm = fireAlarm; }
}