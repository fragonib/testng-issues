package org.testng.issue.skip.test.result;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;


@Listeners(AttrsOnSkippedTestSuiteVerifier.class)
public class AttrsOnSkippedTest extends AttrsOnSkippedTestParent {

    // ------------------------------------------------------------ Configuration

    @BeforeMethod(alwaysRun = true)
    protected void localBeforeMethod(XmlTest context, ITestResult result) {
        if (context.getParameter(AttrsOnSkippedTestLabels.FAIL_MODE).equals(AttrsOnSkippedTestLabels.BEFORE))
            skipIntentionally(AttrsOnSkippedTestLabels.LOCAL_BEFORE);
        log(AttrsOnSkippedTestLabels.LOCAL, AttrsOnSkippedTestLabels.BEFORE);
        reportTestData(result, AttrsOnSkippedTestLabels.LOCAL_BEFORE, AttrsOnSkippedTestLabels.DUMMY_ATTR_VALUE);
    }

    @AfterMethod(alwaysRun = true)
    protected void localAfterMethod(XmlTest context, ITestResult result) {
        if (context.getParameter(AttrsOnSkippedTestLabels.FAIL_MODE).equals(AttrsOnSkippedTestLabels.AFTER))
            skipIntentionally(AttrsOnSkippedTestLabels.LOCAL_AFTER);
        log(AttrsOnSkippedTestLabels.LOCAL, AttrsOnSkippedTestLabels.AFTER);
        reportTestData(result, AttrsOnSkippedTestLabels.LOCAL_AFTER, AttrsOnSkippedTestLabels.DUMMY_ATTR_VALUE);
    }


    // ------------------------------------------------------------ Tests

    @Test
    protected void localTest(XmlTest context) {
        log(AttrsOnSkippedTestLabels.LOCAL, AttrsOnSkippedTestLabels.TEST);
        reportTestData(AttrsOnSkippedTestLabels.TEST, AttrsOnSkippedTestLabels.DUMMY_ATTR_VALUE);
        if (context.getParameter(AttrsOnSkippedTestLabels.FAIL_MODE).equals(AttrsOnSkippedTestLabels.TEST))
            skipIntentionally(AttrsOnSkippedTestLabels.TEST);
    }

}