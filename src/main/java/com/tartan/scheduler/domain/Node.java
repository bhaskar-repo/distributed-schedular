package com.tartan.scheduler.domain;

import com.tartan.scheduler.constants.Status;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class Node implements Callable<Status> {

    private Status status;
    private Queue<ChildTask> childTasks = new LinkedList<>();

    public void run() {
        childTasks.stream()
                .parallel()
                .filter(childTask -> !childTask.isCompleted())
                .forEach(childTask -> {
                    printNumbers(childTask);
                    childTask.markAsCompleted();
                });
        System.out.println();
        setStatus(Status.SUCCESS);
    }

    private void printNumbers(ChildTask childTask) {
        IntStream.range(childTask.getStartIndex(), childTask.getEndIndex() + 1)
                .forEach(index -> {
                    //if node goes down will store the state
                    if (Status.FAILURE == this.status) {
                        childTask.setStartIndex(index);
                        run();
                    }
                    System.out.print(index + "\t");
                });
    }

    public void assignChildTasks(List<ChildTask> childTasks) {
        this.childTasks.addAll(childTasks);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public Status call() throws Exception {
        run();
        return status;
    }
}