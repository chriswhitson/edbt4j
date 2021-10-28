package org.edbt4j;

import java.util.Deque;
import java.util.LinkedList;

public class BehaviourTree implements Scheduler {

    private Node rootNode;
    private Deque<Node> nodes = new LinkedList<>();

    public BehaviourTree(Node rootNode) {
        this.rootNode = rootNode;
        this.rootNode.setScheduler(this);
    }

    @Override
    public void add(Node node) {
        if (node == null) throw new NullPointerException("cannot schedule a null node");
        nodes.addFirst(node);
    }

    @Override
    public void update() {
        nodes.add(null);

        Node currentNode = nodes.pop();
        while (currentNode != null) {
            if (currentNode.getStatus() == Status.INACTIVE) {
                System.out.println("inactive: " + currentNode);
                currentNode = nodes.pop();
                continue;
            }

            currentNode.tick();
            System.out.println(currentNode);
            if (currentNode.getStatus() == Status.RUNNING) {
                nodes.add(currentNode);
            }

            currentNode = nodes.pop();
        }

//        if (nodes.isEmpty()) {
//            this.start();
//        }
    }

    public void start() {
        rootNode.start();
    }

    public boolean isComplete() {
        return nodes.isEmpty();
    }
}


