package utils;

import dao.BaseDAO;
import sun.nio.ch.ThreadPool;

import java.util.ArrayList;
import java.util.concurrent.*;

public class TaskHandler {
    public static final long DEFAULT_THREAD_DELAY = 25;
    private static final long DEFAULT_THREAD_STANDBY_DELAY = 250;
    private Looper mLooper;
    private CustomExecutorService mExecutorService;
    private ArrayList<Runnable> mTaskPool;
    private Boolean mExit = false;

    public TaskHandler(String threadName) {
        mExecutorService = new CustomExecutorService();
        mLooper = new Looper(threadName);
        mTaskPool = new ArrayList<>();
    }

    public void startTask(Runnable runnable) {
        mTaskPool.add(runnable);
    }

    public void addToTaskPool(Runnable runnable) {
        mExecutorService.submit(runnable);
    }

    public Future<ArrayList<BaseDAO>> addToTaskPool(Callable<ArrayList<BaseDAO>> callable) {
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

    class CustomExecutorService extends ThreadPoolExecutor {

        public CustomExecutorService() {
            super(10, 200,
                    500L, TimeUnit.SECONDS,
                    new ArrayBlockingQueue(1000),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }

        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (t == null && r instanceof Future<?>) {
                try {
                    Future<?> future = (Future<?>) r;
                    if (future.isDone()) {
                        future.get();
                    }
                } catch (CancellationException ce) {
                    t = ce;
                } catch (ExecutionException ee) {
                    t = ee.getCause();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // ignore/reset
                }
            }
            if (t != null) {
                t.printStackTrace();
            }
        }
    }
}