package io.j99.tools.lint;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.Collections;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.MethodInvocation;
import lombok.ast.Node;

/**
 * Created by apple on 5/23/16.
 */
public class LogDetector extends Detector implements Detector.JavaScanner {
    public static final Issue ISSUE = Issue.create(
            "LogUse",
            "Don't use System.out.print or System.out.println or System.err.print or System.err.println",
            "android.util.Log",
            Category.SECURITY, 5, Severity.ERROR,
            new Implementation(LogDetector.class, Scope.JAVA_FILE_SCOPE));

    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return Collections.<Class<? extends Node>>singletonList(MethodInvocation.class);
    }

    @Override
    public AstVisitor createJavaVisitor(final JavaContext context) {
        return new ForwardingAstVisitor() {
            @Override
            public boolean visitMethodInvocation(MethodInvocation node) {

                if (node.toString().startsWith("System.out.print")) {
                    context.report(ISSUE, node, context.getLocation(node),
                            "避免使用System.out.print");
                    return true;
                }
                if (node.toString().startsWith("System.out.println")) {
                    context.report(ISSUE, node, context.getLocation(node),
                            "避免使用System.out.println");
                    return true;
                }

                if (node.toString().startsWith("System.err.print")) {
                    context.report(ISSUE, node, context.getLocation(node),
                            "避免使用System.err.print");
                    return true;
                }
                if (node.toString().startsWith("System.err.println")) {
                    context.report(ISSUE, node, context.getLocation(node),
                            "避免使用System.err.println");
                    return true;
                }
//                JavaParser.ResolvedNode resolve = context.resolve(node);
//                if (resolve instanceof JavaParser.ResolvedMethod) {
//                    JavaParser.ResolvedMethod method = (JavaParser.ResolvedMethod) resolve;
//                    // 方法所在的类校验
//                    JavaParser.ResolvedClass containingClass = method.getContainingClass();
//                    if (containingClass.matches("android.util.Log")) {
//                        context.report(ISSUE, node, context.getLocation(node),
//                                "请使用Ln，避免使用Log");
//                        return true;
//                    }
//                }
                return super.visitMethodInvocation(node);
            }
        };
    }
}
