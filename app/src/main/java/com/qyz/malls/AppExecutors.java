package com.qyz.malls;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private final Executor mDiskIO;

    private AppExecutors(Executor diskIO){
        this.mDiskIO = diskIO;
    }

    public AppExecutors(){
        this(Executors.newSingleThreadExecutor());
    }

    public Executor getDiskIO(){
        return mDiskIO;
    }
}
