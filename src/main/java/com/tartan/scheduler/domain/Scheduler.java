package com.tartan.scheduler.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    List<Node> nodes = new ArrayList<>();

    //Assuming there are by default some nodes are being provided
    public void addDefaultNodes() {
        nodes.add(new Node());
        nodes.add(new Node());
        nodes.add(new Node());
    }

    //creates child tasks and divide them equally among nodes
    public void schedule(MasterTask masterTask) {
        divideLoadToAvailableNodes(masterTask);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(nodes.size());
        nodes.forEach(node -> scheduledExecutorService.schedule(node, 1, TimeUnit.SECONDS));
        try {
            scheduledExecutorService.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        masterTask.markAsCompleted(nodes);
        scheduledExecutorService.shutdown();
    }

    private void divideLoadToAvailableNodes(MasterTask masterTask) {
        masterTask.createChildTasks(nodes.size());
        int division = masterTask.getChildTasksSize() / nodes.size();
        int condition = nodes.size();
        if (masterTask.getChildTasksSize() < nodes.size()) {
            division = masterTask.getChildTasksSize() % nodes.size();
            condition = division;
        }
        int lastIndex = division;
        int temp = 0;
        int key = 0;
        List<ChildTask> childTasks = masterTask.getChildTasks();
        while (key < condition) {
            List<ChildTask> list = childTasks.subList(temp, lastIndex);
            nodes.get(key).assignChildTasks(list);
            key++;
            temp = lastIndex;
            lastIndex = lastIndex + division;
        }
        if (masterTask.getChildTasksSize() >= nodes.size()) {
            scheduleRemainingTasks(masterTask, lastIndex, temp, childTasks);
        }
    }

    //if the child tasks are odd then create sub tasks for that
    private void scheduleRemainingTasks(MasterTask masterTask, int lastIndex, int temp, List<ChildTask> childTasks) {
        if (masterTask.getChildTasksSize() % nodes.size() != 0) {
            nodes.get(nodes.size() - 1).assignChildTasks(childTasks.subList(temp, lastIndex));
        }
    }

    public void scaleUp() {
        //this is for scaling up
        nodes.add(new Node());
    }

    public void scaleDown() {
        //this is for scaling down
        nodes.remove(nodes.size() - 1);
    }

    public int getAvailableNodesSize() {
        return this.nodes.size();
    }

    public Node getNode(int nodeNumber) {
        return nodes.get(nodeNumber);
    }
}