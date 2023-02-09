public class MessageDemande extends Message{
    private Position sourcePosition;
    private Direction sourceDirection;

    public MessageDemande(Integer src, Integer dest, Position pos, Direction dir) {
        source = src;
        destination=dest;
        sourcePosition=pos;
        sourceDirection=dir;
    }
}
