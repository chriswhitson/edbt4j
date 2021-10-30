package org.edbt4j;

public abstract class Node {

    private final String name;
    private Scheduler scheduler;
    private Status status = Status.INACTIVE;
    private Node parent;

    public Node() {
        this.name = this.getClass().getSimpleName();
    }

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setParent(Node node) {
        this.parent = node;
    }

    public final void start() {
        if (status == Status.RUNNING)
            throw new RuntimeException(String.format("Node %s is already running", name));

        status = Status.RUNNING;
        onStart();
    }

    protected final void stop(Status status) {
        this.status = status;
        if (this.parent != null) {
            this.parent.onChildStopped(this);
        }
    }

    public void abort() {
        this.status = Status.INACTIVE;
    }

    public void tick() {

    }

    protected void schedule() {
        this.scheduler.add(this);
    }

    protected abstract void onStart();

    // TODO this only makes sense for composites and *maybe* decorators . Maybe replace parent with Function<Node> callback?
    protected abstract void onChildStopped(Node childNode);

    @Override
    public String toString() {
        return (parent != null ? parent.toString() : "") + "/" + getClass().getSimpleName() + "[" + hashCode() + "](" + status + ")";
    }
}
