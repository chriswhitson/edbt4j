package org.edbt4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NodeTest {

    @Test
    void nameDefaultsToSimpleClassName() {
        TestNode testNode = new TestNode();

        assertThat(testNode.getName()).isEqualTo(TestNode.class.getSimpleName());
    }

    @Test
    void statusIsInactiveOnCreation() {
        TestNode testNode = new TestNode();

        assertThat(testNode.getStatus()).isEqualTo(Status.INACTIVE);
    }

    @Test
    void inactiveNodeCanBeStarted() {
        TestScheduler scheduler = new TestScheduler();
        TestNode testNode = new TestNode();
        testNode.setScheduler(scheduler);
        testNode.start();

        assertThat(testNode.getStatus()).isEqualTo(Status.RUNNING);
        assertThat(testNode.startCount).isEqualTo(1);
        assertThat(scheduler.nodeCount).isEqualTo(1);
    }

    @Test
    void nodeCannotBeStartedIfAlreadyRunning() {
        TestScheduler scheduler = new TestScheduler();
        TestNode testNode = new TestNode();
        testNode.setScheduler(scheduler);
        testNode.start();

        assertThatThrownBy(testNode::start)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void aStoppedNodeHasAStatusOfInactive() {
        TestScheduler scheduler = new TestScheduler();
        TestNode testNode = new TestNode();
        testNode.setScheduler(scheduler);
        testNode.start();
        testNode.tick();

        assertThat(testNode.getStatus()).isEqualTo(Status.SUCCESS);
    }

    private static class TestNode extends Node {

        private int startCount = 0;

        @Override
        protected void onStart() {
            startCount++;
            schedule();
        }

        @Override
        protected void onChildStopped(Node childNode) {

        }

        @Override
        public void tick() {
            stop(Status.SUCCESS);
        }
    }

    private static class TestScheduler implements Scheduler {

        private int nodeCount = 0;

        @Override
        public void add(Node node) {
            nodeCount++;
        }

        @Override
        public void update() {

        }
    }

}
