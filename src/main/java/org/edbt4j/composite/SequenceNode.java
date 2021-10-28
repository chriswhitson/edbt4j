package org.edbt4j.composite;

import org.edbt4j.Node;
import org.edbt4j.Status;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SequenceNode extends CompositeNode {

    private Iterator<Node> iter;

    public SequenceNode(List<Node> childNodes) {
        super(childNodes);
    }

    public SequenceNode(Node... childNodes) {
        this(Arrays.asList(childNodes));
    }

    @Override
    protected void onStart() {
        this.iter = getChildNodes().iterator();
        if (iter.hasNext()) iter.next().start();
        else stop(Status.FAILURE);
    }

    @Override
    protected void onChildStopped(Node node) {
        if (node.getStatus() == Status.FAILURE) stop(Status.FAILURE);
        else if (node.getStatus() == Status.SUCCESS && iter.hasNext()) iter.next().start();
        else stop(Status.FAILURE);
    }
}
