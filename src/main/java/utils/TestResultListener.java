package utils;

import logging.Log;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class TestResultListener implements TestExecutionExceptionHandler {
    
    private static final String NEW_LINE = "<br />";
    
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        Log.logFail("Test failed" + NEW_LINE + throwable + getFullStackTrace(throwable));
        throw throwable;
    }
    
    private String getFullStackTrace(Throwable throwable) {
        StringBuilder fullStackTrace = new StringBuilder();
        StackTraceElement[] trace = throwable.getStackTrace();
        
        for (StackTraceElement traceElement : trace)
            fullStackTrace.append(NEW_LINE + "at ").append(traceElement);
        
        return String.valueOf(fullStackTrace);
    }
}
