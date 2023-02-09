public class MessageDemande extends Message{
    private Position sourcePosition;
    private Direction sourceDirection;

    public MessageDemande(Integer src, Position srcPos, Direction dir, Position dest) {
        source = src;
        destinationPosition = dest;
        sourcePosition=srcPos;
        sourceDirection=dir;
    }
}
