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
}
