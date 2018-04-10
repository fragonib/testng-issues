package org.testng.issue.skip.test.result;

import org.testng.*;
import org.testng.xml.XmlTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class AttrsOnSkippedTestSuiteVerifier implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        // Empty
    }

    @Override
    public void onFinish(ISuite suite) {
        suite.getResults().values().stream()
                .map(ISuiteResult::getTestContext)
                .filter(testContext -> testContext.getName().startsWith("AttrsOnSkipped"))
                .forEach(this::verifyAttrsOnSkipped);
    }

    private void verifyAttrsOnSkipped(ITestContext testContext) {
        XmlTest currentXmlTest = testContext.getCurrentXmlTest();
        String failMode = currentXmlTest.getParameter(AttrsOnSkippedTestLabels.FAIL_MODE);
        System.out.println("Verifying test result on " + currentXmlTest.getName());
        if (failMode.equals(AttrsOnSkippedTestLabels.BEFORE))
            verifySkipOnLocalBefore(testContext);
        if (failMode.equals(AttrsOnSkippedTestLabels.TEST))
            verifySkipOnTest(testContext);
        if (failMode.equals(AttrsOnSkippedTestLabels.AFTER))
            verifySkipOnLocalAfter(testContext);
    }

    private void verifySkipOnLocalBefore(ITestContext testContext) {
        IResultMap skippedTests = testContext.getSkippedTests();
        assertThat(skippedTests.size()).as("Skipped tests").isEqualTo(1);
        ITestResult testResult = skippedTests.getAllResults().stream().findFirst().get();
        Set<String> attributeNames = testResult.getAttributeNames();
        assertThat(attributeNames).as("Attributes when SKIP on BEFORE")
                .contains(AttrsOnSkippedTestLabels.PARENT_BEFORE, AttrsOnSkippedTestLabels.LOCAL_AFTER, AttrsOnSkippedTestLabels.PARENT_AFTER);
    }

    private void verifySkipOnTest(ITestContext testContext) {
        IResultMap skippedTests = testContext.getSkippedTests();
        assertThat(skippedTests.size()).as("Skipped tests").isEqualTo(1);
        ITestResult testResult = skippedTests.getAllResults().stream().findFirst().get();
        assertThat(testResult.getAttributeNames()).as("Attributes when SKIP on TEST")
                .contains(AttrsOnSkippedTestLabels.PARENT_BEFORE, AttrsOnSkippedTestLabels.TEST, AttrsOnSkippedTestLabels.LOCAL_AFTER, AttrsOnSkippedTestLabels.PARENT_AFTER);
    }

    private void verifySkipOnLocalAfter(ITestContext testContext) {
        IResultMap passedTests = testContext.getPassedTests();
        assertThat(passedTests.size()).as("Passed tests").isEqualTo(1);
        ITestResult testResult = passedTests.getAllResults().stream().findFirst().get();
        assertThat(testResult.getAttributeNames()).as("Attributes when SKIP on AFTER")
                .contains(AttrsOnSkippedTestLabels.PARENT_BEFORE, AttrsOnSkippedTestLabels.LOCAL_BEFORE, AttrsOnSkippedTestLabels.TEST, AttrsOnSkippedTestLabels.PARENT_AFTER);
    }

}