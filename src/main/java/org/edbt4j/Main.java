package org.edbt4j;

import org.edbt4j.composite.ParallelNode;
import org.edbt4j.composite.SelectorNode;
import org.edbt4j.composite.SequenceNode;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        BehaviourTree bt = new BehaviourTree(
                new SelectorNode(
                        new DebugAction(),
                        new SequenceNode(
                                new SelectorNode(
                                        new DebugAction(),
                                        new DebugAction(),
                                        new DebugAction(),
                                        new DebugAction()),
                                new DebugAction(),
                                new DebugAction(),
                                new DebugAction()),
                        new ParallelNode(
                                new DebugAction(),
                                new DebugAction(),
                                new DebugAction())));

        bt.start();

        while (!bt.isComplete()) {
            System.out.println("next tick");
            bt.update();
        }
    }

    private static class DebugAction extends Node {
        private static int debuggerCount = 0;
        private int id = debuggerCount++;
        private int tickCount = 0;
        private int limit = new Random().nextInt(5);



        @Override
        protected void onStart() {
            schedule();
        }

        @Override
        public void tick() {
            tickCount++;
            System.out.printf("ticking %d (%d/%d)\n", id, tickCount, limit);
            if (tickCount >= limit) {
                Status result = limit % 2 == 0 ? Status.SUCCESS : Status.FAILURE;
                System.out.printf("completed %d: %s\n", id, result);
                stop(result);
            }
        }

        @Override
        protected void onChildStopped(Node childNode) {

        }

        @Override
        public String toString() {
            return super.toString() + String.format("(%d/%d)", tickCount, limit);
        }
    }
}
