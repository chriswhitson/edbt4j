package org.edbt4j.decorator;

import org.edbt4j.Node;

public abstract class Decorator extends Node {

    private final Node innerNode;

    public Decorator(Node innerNode) {
        this.innerNode = innerNode;
    }

    public Decorator(String name, Node innerNode) {
        super(name);
        this.innerNode = innerNode;
    }

    @Override
    protected void onStart() {
        innerNode.start();
    }

    @Override
    protected void onChildStopped(Node childNode) {
        stop(childNode.getStatus());
    }

    @Override
    public void abort() {
        innerNode.abort();
        super.abort();
    }
}
