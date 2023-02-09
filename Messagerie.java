import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Messagerie {
    Map<Integer, LinkedList<Message>> boite;

    public Messagerie(){
        boite = new HashMap<>();
    }

    public void initAgent(Integer id){
        boite.put(id,new LinkedList<>());
    }

    public Message getFirstMessage(Integer i){
        synchronized (boite.get(i)) {
            if(boite.get(i).size() == 0) {
                return null;
            }
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
