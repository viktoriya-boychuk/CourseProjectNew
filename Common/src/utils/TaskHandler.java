package utils;

import dao.BaseDAO;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskHandler {
    public static final long DEFAULT_THREAD_DELAY = 25;
    private static final long DEFAULT_THREAD_STANDBY_DELAY = 250;
    private Looper mLooper;
    private ExecutorService mExecutorService;
    private ArrayList<Runnable> mTaskPool;
    private Boolean mExit = false;

    public TaskHandler(String threadName) {
        mExecutorService = Executors.newCachedThreadPool();
        mLooper = new Looper(threadName);
        mTaskPool = new ArrayList<>();
    }

    public void startTask(Runnable runnable) {
        mTaskPool.add(runnable);
    }

    public void addToTaskPool(Runnable runnable) {
        mExecutorService.submit(runnable);
    }

    public Future<ArrayList <BaseDAO>> addToTaskPool(Callable<ArrayList <BaseDAO>> callable) {
        return mExecutorService.submit(callable);
    }

    public void stop() {
        Logger.logInfo("Thread stop", "Stopping " + getCurrentThreadName() + " thread");
        mExit = true;
        try {
            Thread.sleep(DEFAULT_THREAD_STANDBY_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mExecutorService.shutdown();
    }

    public void startInCurrentThread() {
        mLooper.run();
    }

    public void startInBackgroundThread() {
        mExecutorService.submit(mLooper);
    }

    public boolean status() {
        return mExit;
    }

    public String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    private class Looper implements Runnable {
        private String mThreadName;

        public Looper(String mThreadName) {
            this.mThreadName = mThreadName;
        }

        public String getThreadName() {
            return mThreadName;
        }

        @Override
        public void run() {
            while (!mExit) {
                ArrayList<Runnable> toRemove = new ArrayList<>();
                if (!mTaskPool.isEmpty()) {
                    ArrayList<Runnable> buffer = new ArrayList<>(mTaskPool);
                    for (Runnable runnable : buffer) {
                        addToTaskPool(runnable);
                        toRemove.add(runnable);
                    }
                    mTaskPool.removeAll(toRemove);
                }
                try {
                    Thread.sleep(DEFAULT_THREAD_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Logger.logInfo("Thread stop", "Thread " + getCurrentThreadName() + " stopped");
        }
    }

    public class TaskHandlerException extends Exception {}


}
