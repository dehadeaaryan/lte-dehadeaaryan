public class DLLtest {
    public static void main(String[] args) {
        System.out.println("Testing");

        DLList<Integer> intlist;

        boolean ok;

        intlist = new DLList<Integer>();

        for (int i = 0; i < 10; i ++) {
            intlist.insertFirst(i);
        }

        ok = intlist.seekFirst();

        while (ok) {
            System.out.println("Index = " + intlist.getIndex() + " Data = " + intlist.getData());
            ok = intlist.seekNext();
        }

        intlist.deleteLast();

        System.out.println();
        ok = intlist.seekFirst();

        while (ok) {
            System.out.println("Index = " + intlist.getIndex() + " Data = " + intlist.getData());
            ok = intlist.seekNext();
        }

        intlist.deleteFirst();

        System.out.println();
        ok = intlist.seekFirst();

        while (ok) {
            System.out.println("Index = " + intlist.getIndex() + " Data = " + intlist.getData());
            ok = intlist.seekNext();
        }

        intlist.seek(3);
        intlist.delete();

        System.out.println();
        ok = intlist.seekFirst();

        while (ok) {
            System.out.println("Index = " + intlist.getIndex() + " Data = " + intlist.getData());
            ok = intlist.seekNext();
        }

        intlist.seek(4);
        intlist.insert(5);

        System.out.println();
        ok = intlist.seekFirst();

        while (ok) {
            System.out.println("Index = " + intlist.getIndex() + " Data = " + intlist.getData());
            ok = intlist.seekNext();
        }
    }
}


