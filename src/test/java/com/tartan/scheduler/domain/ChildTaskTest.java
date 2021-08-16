package com.tartan.scheduler.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChildTaskTest {

    private ChildTask childTask;

    @BeforeAll
    public void setUp() {
        childTask = new ChildTask(1, 4);
    }

    @Test
    public void givenNodesStatusShouldUpdateTheStatusToSuccess() {
        //when
        childTask.markAsCompleted();
        //then
        Assertions.assertTrue(childTask.isCompleted());
    }
}
