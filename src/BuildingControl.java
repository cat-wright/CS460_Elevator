import ControlPanel.ControlPanel;
import Cabin.Cabin;

/**
 * Created by Dominic on 4/4/2018.
 */
public class BuildingControl
{
  final static ControlPanel cP = new ControlPanel();
  Cabin cabin = new Cabin(1);
  private Integer requestedFloor;
  private BuildingControl()
  {
    
  }
  
  private boolean isCabinMoving()
  {
    if(cabin.isCabinMoving())
    {
      return true;
    }
    else return false;
  }
  private void sendToFloor()
  {
    requestedFloor = cP.getRequestedFloor();
    if(requestedFloor != null)
    {
      while(isCabinMoving()) {}
      cabin.moveCabin(requestedFloor);
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
