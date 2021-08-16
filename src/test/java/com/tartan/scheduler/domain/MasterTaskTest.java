package com.tartan.scheduler.domain;

import com.tartan.scheduler.constants.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MasterTaskTest {

    private MasterTask masterTask;

    @BeforeAll
    public void setUp() {
        masterTask = new MasterTask(10);
    }

    @Test
    public void givenNodeSizeShouldCreateSubTasks() {
        //given
        int nodeSize = 3;
        //when
        masterTask.createChildTasks(nodeSize);
        //then
        Assertions.assertEquals(4, masterTask.getChildTasksSize());
    }

    @Test
    public void givenNodesStatusShouldUpdateTheStatusToSuccess() {
        //given
        Node node1 = new Node();
        node1.setStatus(Status.SUCCESS);
        Node node2 = new Node();
        node2.setStatus(Status.SUCCESS);
        List<Node> nodes = Arrays.asList(node1, node2);
        //when
        masterTask.markAsCompleted(nodes);
        //then
        Assertions.assertEquals(Status.SUCCESS, masterTask.getStatus());
    }

    @Test
    public void givenNodesStatusShouldUpdateTheStatusToFailure() {
        //given
        Node node1 = new Node();
        node1.setStatus(Status.SUCCESS);
        Node node2 = new Node();
        node2.setStatus(Status.FAILURE);
        List<Node> nodes = Arrays.asList(node1, node2);
        //when
        masterTask.markAsCompleted(nodes);
        //then
        Assertions.assertEquals(Status.FAILURE, masterTask.getStatus());
    }
}
