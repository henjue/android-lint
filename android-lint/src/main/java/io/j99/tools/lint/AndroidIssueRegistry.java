package io.j99.tools.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

/**
 * The list of issues that will be checked when running <code>lint</code>.
 */
@SuppressWarnings("unused")
public class AndroidIssueRegistry extends IssueRegistry {
    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(ListViewUseDetector.ISSUE, StorageDetector.ISSUE, LogDetector.ISSUE);
    }
}