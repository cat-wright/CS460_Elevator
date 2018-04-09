public class CabinRequest extends Request {
    Directions directions;
    Integer destination;
    Type type;
    public CabinRequest(Directions directions,Integer destination){
        this.directions = directions;
        this.destination = destination;
        type = Type.CABIN;
    }

    public Integer getButtonRequest(){
        // calls CabinButton.
        return 0;
    }

}
