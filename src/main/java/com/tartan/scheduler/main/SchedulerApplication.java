package com.tartan.scheduler.main;

import com.tartan.scheduler.constants.Status;
import com.tartan.scheduler.domain.MasterTask;
import com.tartan.scheduler.domain.Scheduler;

import java.util.Scanner;

public class SchedulerApplication {
    public static void main(String[] args) throws InterruptedException {
        //I am taking an example of printing 1 to n as master task
        Scanner scanner = new Scanner(System.in);
        System.out.println("Print numbers from 1 to n");
        System.out.println("Enter total number of numbers n");
        int total = scanner.nextInt();
        Scheduler scheduler = new Scheduler();
        scheduler.addDefaultNodes();
        int size = scheduler.getAvailableNodesSize();
        if (total % size != 0) {
            scheduler.scaleUp();
        }
        //please comment any of the method to check the result at a time
        executeAfterScaleDown(total, scheduler);
        //executeAfterScaleUp(total, scheduler);

        scanner.close();
    }

    private static void executeAfterScaleUp(int total, Scheduler scheduler) {
        System.out.println("After scaling up ");
        scheduler.scaleUp();
        MasterTask masterTask2 = new MasterTask(total);
        scheduler.schedule(masterTask2);
        //explicitly making node to failure state it will store the failed state keep retrying
        scheduler.getNode(1).setStatus(Status.FAILURE);
        System.out.println(masterTask2.getStatus());
    }

    private static void executeAfterScaleDown(int total, Scheduler scheduler) {
        System.out.println("After scaling down");
        scheduler.scaleDown();
        MasterTask masterTask1 = new MasterTask(total);
        scheduler.schedule(masterTask1);
        //explicitly making node to failure state it will store the failed state keep retrying
        scheduler.getNode(1).setStatus(Status.FAILURE);
        System.out.println(masterTask1.getStatus());
    }
}
