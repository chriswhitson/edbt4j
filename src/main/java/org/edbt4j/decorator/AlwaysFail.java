package org.edbt4j.decorator;

import org.edbt4j.Node;
import org.edbt4j.Status;

public class AlwaysFail extends Decorator {

    public AlwaysFail(Node innerNode) {
        super(innerNode);
    }

    public AlwaysFail(String name, Node innerNode) {
        super(name, innerNode);
    }

    @Override
    protected void onChildStopped(Node childNode) {
        stop(Status.FAILURE);
    }
}
