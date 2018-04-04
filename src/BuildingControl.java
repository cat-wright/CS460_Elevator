import ControlPanel.ControlPanel;

/**
 * Created by Dominic on 4/4/2018.
 */
public class BuildingControl
{
  final static ControlPanel cP = new ControlPanel();
  private Integer requestedFloor;
  private BuildingControl()
  {
    
  }
  
  private void sendToFloor()
  {
    requestedFloor = cP.getRequestedFloor();
    if(requestedFloor != null)
    {
      
    }
  }
  
  
  
  
  
  
  
  
  public static void main(final String[] args)
  {
    final BuildingControl bP = new BuildingControl();
    cP.start();
    while(true)
    {
      bP.sendToFloor();
    }
  }
}
