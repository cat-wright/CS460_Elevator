package ControlPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import java.io.IOException;

public class ControlPanel extends Thread
{
    private volatile boolean finished = false;
    private ControlGUI controller;
    Integer requestedFloor = null;

    ControlPanel()
    {
        new JFXPanel();
        Platform.runLater(() -> {
            try
            {
                controller = new ControlGUI(this);
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
            /*if(controller.getRequestedFloor() == null)
            {
                requestedFloor = controller.getRequestedFloor();
            }*/
        }
    }

    void getCurrentFloor(Integer floor)
    {
        controller.setCurrentFloor(floor);
    }

    Integer getRequestedFloor()
    {
        return requestedFloor;
    }

    public static void main(final String[] args)
    {
        final ControlPanel cP = new ControlPanel();
        cP.start();
    }

    void shutdown()
    {
        finished = true;
    }
}
