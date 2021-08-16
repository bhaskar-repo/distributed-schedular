package com.tartan.scheduler.domain;

import com.tartan.scheduler.constants.Status;

public class ChildTask {

    private int startIndex;
    private int endIndex;
    private Status status;

    public ChildTask(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.status = Status.FAILURE;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void markAsCompleted() {
        this.status = Status.SUCCESS;
    }

    public boolean isCompleted() {
        return this.status == Status.SUCCESS;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
