package org.edbt4j.composite;

import org.edbt4j.Node;
import org.edbt4j.Status;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParallelNode extends CompositeNode {

    public ParallelNode(List<Node> childNodes) {
        super(childNodes);
    }

    public ParallelNode(Node... childNodes) {
        this(Arrays.asList(childNodes));
    }

    @Override
    protected void onStart() {
        List<Node> childNodes = new LinkedList<>(getChildNodes());
        Collections.reverse(childNodes);
        childNodes.forEach(Node::start);
    }

    @Override
    protected void onChildStopped(Node node) {
        getChildNodes().forEach(childNode -> {
            if (childNode.getStatus() == Status.RUNNING) {
                childNode.abort();
            }
        });
        stop(node.getStatus());
    }
}
