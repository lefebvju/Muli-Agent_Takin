import java.util.LinkedList;
import java.util.Map;

public class Messagerie {
    Map<Integer, LinkedList<Message>> boite;

    public Message getFirstMessage(Integer i){
        return boite.get(i).getFirst();
    }

    public void removeFirstMessage(Integer i){
        boite.get(i).removeFirst();
    }

    public void addMessage(Integer i, Message m){
        boite.get(i).addLast(m);
    }
}
