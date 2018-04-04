package ControlPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import java.io.IOException;

public class ControlPanel extends Thread
{
    private volatile boolean finished = false;
    private ControlGUI controller = null;
    final int FLOORS = 9;
    Integer requestedFloor;

    ControlPanel()
    {
        new JFXPanel();
        Platform.runLater(() -> {
            try
            {
                controller = new ControlGUI(this, FLOORS);
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
                }
            }
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
