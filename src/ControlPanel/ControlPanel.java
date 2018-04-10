package ControlPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import Request.*;
import java.util.ArrayList;

public class ControlPanel extends Thread
{
    private volatile boolean finished = false;
    private ControlGUI controller = null;
    private final int FLOORS = 10;
    private final int ELEVATORS = 4;
    private Integer requestedFloor;
    private Integer currentFloor = 1;
    private ArrayList<Request> disabledButtons;
    private Request currentRequest;

    public ControlPanel()
    {
        new JFXPanel();
        Platform.runLater(() -> {
            try
            {
                controller = new ControlGUI(this, FLOORS, ELEVATORS);
                controller.updateCurrentFloor(currentFloor);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void run()
    {
        while(!finished)
        {
            if(controller != null) {
                Integer floor = controller.getRequestedFloor();
                if (floor != null) {
                    this.requestedFloor = floor;
                    this.requestedFloor = null;
                }
                disabledButtons = controller.getDisabledButtons();
                if(currentRequest != null)
                {
                    controller.setRequest(currentRequest);
                }
            }
        }
    }

    public void setCurrentFloor(Integer floor)
    {
        if(controller != null) controller.updateCurrentFloor(floor);
    }

    //This is the method to be used by building control to check if a button is available and can be requested
    public ArrayList<Request> getDisabledButtons()
    {
        return disabledButtons;
    }

    //This is the method used by buildingControl to send the control panel where the cabin will travel
    public void setCurrentRequest(Request request)
    {
        this.currentRequest = request;
    }

    //This is returned from the GUI to be added to the queue.  Building control needs to check for this return
    //and add the request.
    public Request getRequestedFloor()
    {
        Request guiRequest = new Request(requestedFloor, Type.CABIN); //Still needs to be implemented for Floor requests
        return guiRequest;
    }

    void shutdown()
    {
        finished = true;
    }
}
