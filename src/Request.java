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

    public void setDirection(Directions direction)
    {
        this.direction = direction;
    }
}
