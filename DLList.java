public class DLList <T> {
    
    public class Node <T> {

        public T data;
        public Node <T> previous;
        public Node <T> next;

        Node (T data) {
            this.data = data;
            previous = null;
            next = null;
        }

        Node (T data, Node <T> previous, Node <T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }

    }



    // Constructors

    public DLList() {
        clear();
    }

    public DLList(DLList <T> other) {
        clear();
    }



    // Data Members

    private Node <T> start;
    private Node <T> end;
    private Node <T> current;

    int size, index;



    // Member Functions

    // clear method to clear the list
    public void clear() {
        start = null;
        end = null;
        current = null;
        size = 0;
        index = -1;
    }

    // getSize method to get the size of the list
    public int getSize() {
        return size;
    }

    // getIndex method to get the index of the current node
    public int getIndex() {
        return index;
    }

    // isEmpty method to check if the list is empty
    public boolean isEmpty() {
        return (size == 0);
    }

    // atStart method to check if the current node is at the start of the list
    public boolean atFirst() {
        return (index == 0);
    }

    // atLast method to check if the current node is at the end of the list
    public boolean atLast() {
        return (index == size - 1);
    }

    // getData method to get the data of the current node
    public T getData() {
        return isEmpty() ? null : current.data;
    }

    // setData method to set the data of the current node
    public T setData(T data) {
        if (!isEmpty()) {
            current.data = data;
            return data;
        }
        return null;
    }

    
    
    // seek method to seek to a specific node
    public boolean seek(int index) {

        if (isEmpty()) {
            return false;
        } else if (index < 0 || index >= size) {
            return false;
        } else if (index == 0) {
            current = start;
            this.index = 0;
            return true;
        } else if (index == size - 1) {
            current = end;
            this.index = size - 1;
            return true;
        } else if (index < this.index) {
            while (index != this.index) {
                this.index--;
                current = current.previous;
            }
            return true;
        } else if (index > this.index) {
            while (index != this.index) {
                this.index++;
                current = current.next;
            }
            return true;
        }
        return true;
    }

    // seekStart method to seek to the start of the list
    public boolean seekFirst() {
        return seek(0);
    }

    // seekEnd method to seek to the end of the list
    public boolean seekLast() {
        return seek(size - 1);
    }

    // seekPrevious method to seek to the previous node
    public boolean seekPrevious() {
        return seek(index - 1);
    }

    // seekNext method to seek to the next node
    public boolean seekNext() {
        return seek(index + 1);
    }

    
    
    
    // insert method to insert a node at the current position
    public boolean insert(T data) {

        Node <T> node;

        Node <T> previous = null;
        Node <T> next = null;

        if (isEmpty()) {
            previous = null;
            next = null;
            node = new Node <T> (data, previous, next);
            current = node;
            start = current;
            end = current;
            size++;
            index++;
        } else if (atFirst()) {
            previous = null;
            next = current;
            node = new Node <T> (data, previous, next);
            current.previous = node;
            current = node;
            start = current;
            size++;
        } else if (atLast()) {
            previous = current;
            next = null;
            node = new Node <T> (data, previous, next);
            current.next = node;
            current = node;
            end = current;
            size++;
            index++;
        } else {
            previous = current.previous;
            next = current;
            node = new Node <T> (data, previous, next);
            current.previous = node;
            current = node;
            previous.next = current;
            size++;
        }

        return true;
    }

    // insertFirst method to insert a node at the start of the list
    public boolean insertFirst(T data) {
        seekFirst();
        return insert(data);
    }

    // insertLast method to insert a node at the end of the list
    public boolean insertLast(T data) {
        seekLast();
        return insert(data);
    }

    
    
    
    // delete method to remove the current node
    public boolean delete() {

        if (isEmpty()) {
            return false;
        } else if (atFirst()) {
            current = current.next;
            current.previous = null;
            start = current;
            size--;
        } else if (atLast()) {
            current = current.previous;
            current.next = null;
            end = current;
            size--;
            index--;
        } else {
            current.previous.next = current.next;
            current.next.previous = current.previous;
            current = current.next;
            size--;
        }

        return true;
    }

    // deleteFirst method to remove the first node
    public boolean deleteFirst() {
        if (isEmpty()) return false;
        seekFirst();
        return delete();
    }

    // deleteLast method to remove the last node
    public boolean deleteLast() {
        if (isEmpty()) return false;
        seekLast();
        return delete();
    }



}
