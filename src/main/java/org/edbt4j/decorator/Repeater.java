package org.edbt4j.decorator;

import org.edbt4j.Node;
import org.edbt4j.Status;

public class Repeater extends Decorator {

    private final int limit;
    private final boolean stopOnFailure;
    private int count = 0;

    public Repeater(int limit, boolean stopOnFailure, Node innerNode) {
        super(innerNode);
        this.limit = limit;
        this.stopOnFailure = stopOnFailure;
    }

    public Repeater(String name, int limit, boolean stopOnFailure, Node innerNode) {
        super(name, innerNode);
        this.limit = limit;
        this.stopOnFailure = stopOnFailure;
    }

    @Override
    protected void onChildStopped(Node childNode) {
        if (childNode.getStatus() == Status.FAILURE && stopOnFailure) {
            stop(Status.FAILURE);
        } else if (++count == limit) {
            stop(Status.SUCCESS);
        } else {
            childNode.start();
        }
    }
}
