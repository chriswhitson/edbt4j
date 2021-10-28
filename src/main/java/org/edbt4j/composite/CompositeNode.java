package org.edbt4j.composite;

import org.edbt4j.Node;
import org.edbt4j.Scheduler;
import org.edbt4j.Status;

import java.util.List;

public abstract class CompositeNode extends Node {

    private final List<Node> childNodes;

    public CompositeNode(List<Node> childNodes) {
        this.childNodes = childNodes;
    }

    @Override
    public void setScheduler(Scheduler scheduler) {
        super.setScheduler(scheduler);
        childNodes.forEach(node -> {
            node.setScheduler(scheduler);
            node.setParent(this);
        });
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    @Override
    public void abort() {
        childNodes.forEach(node -> {
            if (node.getStatus() == Status.RUNNING) {
                node.abort();
            }
        });
        super.abort();
    }

}
