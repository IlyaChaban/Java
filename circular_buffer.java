public class CircularBuffer {
    private class Node {
        private Node next;
        private Node previous;
        private String value;

        public Node(Node next, Node previous, String value) {
            this.next = next;
            this.previous = previous;
            this.value = value;
        }
    }

    private int size;
    private Node startNode;
    private Node readPtr;
    private Node writePtr;
    private boolean full;
    private boolean empty;
    private int elementsInBuffer;

    public CircularBuffer(int size) {
        this.size = size;

        startNode = new Node(null, null, null);
        writePtr = startNode;
        readPtr = startNode;
        full = false;
        empty = true;
        elementsInBuffer = 0;

        Node tempLastNode = startNode;
        for (int i = 0; i < size - 1; i++) {
            Node newEmptyNode = new Node(null, null, null);
            if (i != size - 2) {
                tempLastNode.next = newEmptyNode;
                newEmptyNode.previous = tempLastNode;
                tempLastNode = newEmptyNode;
            } else {
                newEmptyNode.next = startNode;
                newEmptyNode.previous = tempLastNode;

                tempLastNode.next = newEmptyNode;
                startNode.previous = newEmptyNode;
            }
        }
    }

    public String readFromBuffer() {
        if (readPtr != writePtr || !empty) {
            elementsInBuffer--;
            full = false;
            readPtr = readPtr.next;
            if (elementsInBuffer == 0) {
                System.out.println("You have read the last element from the buffer which is: ");
                empty = true;
            }
            return readPtr.previous.value;
        } else {
            System.out.println("Buffer is empty");
            return null;
        }
    }

    public void writeToBuffer(String value) {
        if (writePtr != readPtr || !full) {
            writePtr.value = value;
            elementsInBuffer++;
            empty = false;
            writePtr = writePtr.next;
            if (elementsInBuffer == size) {
                System.out.println("You have added the last element to the buffer");
                full = true;
            }
        } else {
            System.out.println("Buffer is full");
        }
    }

    public static void main(String[] args) {
        System.out.println("Initializing buffer with 5 elements in it\n");
        CircularBuffer buff = new CircularBuffer(5);

        System.out.println("Adding 5 elements into the buffer");
        for (int i = 0; i < 5; i++) {
            System.out.println("Adding to buffer value: " + i);
            buff.writeToBuffer(String.valueOf(i));
        }

        System.out.println("\nLooking at the buffer");
        Node tempNode = buff.readPtr;
        for (int i = 0; i < 5; i++) {
            System.out.println(tempNode.value);
            tempNode = tempNode.next;
        }

        System.out.println("\nTrying to add more to the buffer which is full");
        buff.writeToBuffer("5");

        System.out.println("\nReading data from the buffer");
        for (int i = 0; i < 5; i++) {
            System.out.println(buff.readFromBuffer());
        }

        System.out.println("\nTrying to read from an empty buffer");
        System.out.println(buff.readFromBuffer());
    }
}