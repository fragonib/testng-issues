package org.testng.issue.skip.test.result;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.xml.XmlTest;


public abstract class AttrsOnSkippedTestParent {

    // ------------------------------------------------------------ Configuration

    @BeforeMethod(alwaysRun = true)
    protected void parentBeforeMethod(XmlTest context, ITestResult result) {
        if (context.getParameter(AttrsOnSkippedTestLabels.FAIL_MODE).equals(AttrsOnSkippedTestLabels.BEFORE))
            failIntentionally(AttrsOnSkippedTestLabels.PARENT_BEFORE);
        log(AttrsOnSkippedTestLabels.PARENT, AttrsOnSkippedTestLabels.BEFORE);
        reportTestData(result, AttrsOnSkippedTestLabels.PARENT_BEFORE, AttrsOnSkippedTestLabels.DUMMY_ATTR_VALUE);
    }

    @AfterMethod(alwaysRun = true)
    protected void parentAfterMethod(XmlTest context, ITestResult result) {
        if (context.getParameter(AttrsOnSkippedTestLabels.FAIL_MODE).equals(AttrsOnSkippedTestLabels.AFTER))
            failIntentionally(AttrsOnSkippedTestLabels.PARENT_AFTER);
        log(AttrsOnSkippedTestLabels.PARENT, AttrsOnSkippedTestLabels.AFTER);
        reportTestData(result, AttrsOnSkippedTestLabels.PARENT_AFTER, AttrsOnSkippedTestLabels.DUMMY_ATTR_VALUE);
    }


    // ------------------------------------------------------------ API

    protected void reportTestData(String key, Object data) {
        Reporter.getCurrentTestResult().setAttribute(key, data);
    }

    protected void reportTestData(ITestResult result, String key, Object data) {
        result.setAttribute(key, data);
    }

    protected void log(String module, String method) {
        String msg = String.format("In %s %s", module, method);
        System.out.println(msg);
    }

    protected void skipIntentionally(String msg) {
        throw new SkipException(msg);
    }

    protected void failIntentionally(String msg) {
        throw new RuntimeException(msg);
    }

}