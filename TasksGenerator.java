package com.bee.am;

import org.apache.log4j.Logger;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TasksGenerator {

  public  TasksGenerator ()
    {
        _myApp = new App();
        initTasks();

    }

    private void initTasks() {
        log.info("Task scheduler launched ...");
        TimerTask task1 = new DeleteTask();
        TimerTask task2 = new SuspendTask();
        TimerTask task3 = new ResumeTask();
        TimerTask task4 = new ErrorCollectorTask();
        Timer timer = new Timer();
        timer.schedule(task1, 420000, TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
        timer.schedule(task2, 0, TimeUnit.MILLISECONDS.convert(3, TimeUnit.MINUTES));
        timer.schedule(task3, 0, TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES));
        timer.schedule(task4,0,  TimeUnit.MILLISECONDS.convert(10, TimeUnit.MINUTES));
    }
    class DeleteTask extends TimerTask {
        public void run() {
            log.info("delete_case task is starting ...");
            _myApp.startCProcess("DEL");
            log.info("delete_case task is finish !!!");
        }
    }
    class SuspendTask extends TimerTask {
        public void run() {
            log.info("cancel_case task is starting ...");
            _myApp.startCProcess("SUS");
            log.info("cancel_case task is finish !!!");
        }
    }

    class ResumeTask extends TimerTask {
        public void run() {
            log.info("resume_case task is starting ...");
            _myApp.startCProcess("RES");
            log.info("resume_case task is finish !!!");
        }
    }
  class  ErrorCollectorTask extends TimerTask{
        public void run() {
            log.info("Error collecting task is starting ...");
            _myApp._flushErrorCases();
            log.info("Error collecting task is finish !!!");
        }
  }
    private static final Logger log = Logger.getLogger(TasksGenerator.class);
    private App _myApp;

}
