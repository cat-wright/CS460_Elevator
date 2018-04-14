package ControlPanel;

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
import java.util.LinkedList;

class ControlGUI extends Application {

    private final int maxElevators = 4;
    private boolean fireAlarm = false;
    private double imgWidth;
    private Integer currentFloor; //Begins at bottom floor

    private LinkedList<Request> disabledButtons = new LinkedList<>();
    private LinkedList<Request> currentRequests = new LinkedList<>();

    private ElevatorGUI e1, e2, e3, e4;
    private ElevatorGUI[] elevatorGUIS = new ElevatorGUI[maxElevators];
    private HBox elevatorPanels;

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
        emergencyButton.setOnAction(e -> fireAlarm = true);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                elevatorPanels = new HBox(e1.getElevatorVBox(), e2.getElevatorVBox(), e3.getElevatorVBox(), e4.getElevatorVBox());
                primaryStage.show();
            }
        };

        timer.start();

        elevatorPanels.setSpacing(20);
        containingScreen.getChildren().addAll(elevatorPanels, emergencyButton);
        containingScreen.setAlignment(Pos.CENTER);
        containingScreen.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(containingScreen);
        scene.getStylesheets().add("gui_stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    synchronized void setSpecs(ElevatorSpecs specs)
    {
        e1.setSpecs(specs);
    }


    //
    void updateCurrentFloor(Integer floor) {
        this.currentFloor = floor;
        //Platform.runLater(() -> repaint());
        System.out.println(currentFloor);
    }

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
            e1.setAbleFlag(false);
        }
        return disabledButtons;
    }

    boolean getMaintenanceKey() {
        return e1.getMaintenanceKey();
    }

    boolean getFireAlarm() { return fireAlarm; }

    void setFireAlarm(boolean fireAlarm) { this.fireAlarm = fireAlarm; }
}