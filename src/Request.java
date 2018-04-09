public class Request {
    private Directions directions;
    private Integer destination;
    private Type type;

    public Request(){

    }

    public Request(Directions directions, Integer destination, Type type){
        this.directions = directions;
        this.destination = destination;
        this.type = type;
    }

    public Integer getButtonRequest(){
        // calls either cabin button or floor button.
        return 0;
    }
}
