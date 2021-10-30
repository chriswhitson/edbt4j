package org.edbt4j.decorator;

import org.edbt4j.Node;
import org.edbt4j.Status;

public class AlwaysSucceed extends Decorator {

    public AlwaysSucceed(Node innerNode) {
        super(innerNode);
    }

    public AlwaysSucceed(String name, Node innerNode) {
        super(name, innerNode);
    }

    @Override
    protected void onChildStopped(Node childNode) {
        stop(Status.SUCCESS);
    }
}
