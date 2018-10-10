import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List l = new ArrayList();
        l.add(1);
        l.add(2);
        l.add(3);
        l.remove(0);
        System.out.println(l.get(0));

    }
}
