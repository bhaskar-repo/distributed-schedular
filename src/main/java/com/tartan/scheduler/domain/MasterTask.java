package com.tartan.scheduler.domain;

import com.tartan.scheduler.constants.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MasterTask {

    private int totalSize;
    private Status status;
    private List<ChildTask> childTasks = new ArrayList<>();

    public MasterTask(int totalNumber) {
        this.totalSize = totalNumber;
    }

    public void createChildTasks(int nodeSize) {

        int division = this.totalSize / nodeSize;
        int condition = division * nodeSize;
        if (this.totalSize < nodeSize) {
            division = this.totalSize % nodeSize;
            condition = division;
        }
        int startIndex = 1;
        int endIndex = division;
        while (endIndex <= condition) {
            this.childTasks.add(new ChildTask(startIndex, endIndex));
            startIndex = endIndex + 1;
            endIndex = endIndex + division;
        }

        if (this.totalSize >= nodeSize) {
            addRemainingChildTasks(nodeSize, startIndex);
        }
    }

    private void addRemainingChildTasks(int nodeSize, int startIndex) {
        int remainingSize = totalSize % nodeSize;

        if (remainingSize != 0) {
            this.childTasks.add(new ChildTask(startIndex, totalSize));
        }
    }

    public List<ChildTask> getChildTasks() {
        return Collections.unmodifiableList(childTasks);
    }

    public int getChildTasksSize() {
        return this.childTasks.size();
    }

    //if all the nodes are completed execution then update master task status
    public void markAsCompleted(List<Node> nodes) {
        boolean isCompletedAll = nodes.stream().allMatch(node -> node.getStatus() == Status.SUCCESS);
        if (isCompletedAll) {
            this.status = Status.SUCCESS;
            return;
        }
        this.status = Status.FAILURE;
    }

    public Status getStatus() {
        return status;
    }
}
