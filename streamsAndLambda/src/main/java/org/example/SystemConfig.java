package org.example;

// Assume RangeCheck is defined correctly elsewhere
public class SystemConfig {


    //@RangeCheck annotation must be applied here
    @RangeCheck( min = 1,max = 6)
    private int maxThreads = 8;

    //@RangeCheck annotation must be applied here
    @RangeCheck(min = 100,max = 5000)
    private int timeoutSeconds = 2500;

    public SystemConfig(int maxThreads, int timeoutSeconds) {
        this.maxThreads = maxThreads;
        this.timeoutSeconds = timeoutSeconds;
    }

    // A simple method for logging successful checks
    public static void logSuccess(String message) {
        System.out.println("SUCCESS: " + message);
    }
}

// File: ConfigValidationException.java (Unchecked Exception)
// Student must create this simple custom unchecked exception class
// ...

