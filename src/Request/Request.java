package Request;
public class Request
{
    private Directions direction;
    private Integer destination;
    private Type type;

    public Request(Integer destination, Type type)
    {
        this.destination = destination;
        this.type = type;
    }

    public Integer getDestination()
    {
        return destination;
    }

    public void setDirection(Directions direction)
    {
        this.direction = direction;
    }

    public Directions getDirection() { return direction; }

    public Type getType() { return type; }

    @Override
    public boolean equals(Object o)
    {
        if(o == null) return false;
        if(o == this) return true;
        if(!(o instanceof Request)) return false;
        Request request = (Request) o;
        if(request.getDestination() == this.destination && request.getDirection() == this.direction && request.getType() == this.type)
        {
            return true;
        }
        else return false;
    }
}
