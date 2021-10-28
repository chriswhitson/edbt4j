package org.edbt4j;

public abstract class Node {

    private Scheduler scheduler;
    private Status status = Status.INACTIVE;
    private Node parent;

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setParent(Node node) {
        this.parent = node;
    }

    public final void start() {
        status = Status.RUNNING;
        onStart();
    }

    protected final void stop(Status status) {
        this.status = status;
        if (this.parent != null) {
            this.parent.onChildStopped(this);
        }
    }

    public void tick() {

    }

    protected void schedule() {
        this.scheduler.add(this);
    }

    protected abstract void onStart();

    public void abort() {
        this.status = Status.INACTIVE;
    }

    protected abstract void onChildStopped(Node node);

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return (parent != null ? parent.toString() : "") + "/" + getClass().getSimpleName() + "[" + hashCode() + "](" + status + ")";
    }
}
