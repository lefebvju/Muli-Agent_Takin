public class MessageDemande extends Message{
    public Position sourcePosition;
    public Direction sourceDirection;

    public MessageDemande(Integer src, Position srcPos, Direction dir, Position dest) {
        source = src;
        destinationPosition = dest;
        sourcePosition=srcPos;
        sourceDirection=dir;
    }
}
