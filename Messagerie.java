import java.util.LinkedList;
import java.util.Map;

public class Messagerie {
    Map<Integer, LinkedList<Message>> boite;

    public Message getFirstMessage(Integer i){
        synchronized (boite.get(i)) {
            return boite.get(i).getFirst();
        }
    }

    public void removeFirstMessage(Integer i){
        synchronized (boite.get(i)) {
            boite.get(i).removeFirst();
        }
    }

    public void addMessage(Integer i, Message m) {
        synchronized (boite.get(i)) {
            boite.get(i).addLast(m);
        }
    }
}
