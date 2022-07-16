import javax.swing.*;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataListener;
import java.util.List;

public class JListList implements ListModel<String> {
    private final List<String> list;
    private final EventListenerList listeners = new EventListenerList();

    public JListList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public String getElementAt(int index) {
        return list.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(ListDataListener.class, l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(ListDataListener.class, l);
    }
}
