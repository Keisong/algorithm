package list;

public class LockDListNode extends DListNode {
	boolean locked;
	 
    LockDListNode(Object i, DListNode p, DListNode n) {
        super(i, p, n);
        locked = false;
    }
}
