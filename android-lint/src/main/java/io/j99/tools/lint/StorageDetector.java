package io.j99.tools.lint;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.*;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by apple on 5/23/16.
 */
public final class StorageDetector extends Detector implements Detector.ClassScanner {
    public static final Issue ISSUE = Issue.create("storageuse",
            "Don't use Environment.getExternalStorageDirectory",
            "Use Context.getFiles()",
            Category.SECURITY,
            5,
            Severity.WARNING,
            new Implementation(
                    StorageDetector.class,
                    Scope.CLASS_FILE_SCOPE));

    @Override
    public boolean appliesTo(@NonNull Context context, @NonNull File file) {
        return true;
    }

    @Override
    public List<String> getApplicableCallNames() {
        return Arrays.asList("getExternalStorageDirectory", "getExternalStoragePublicDirectory");
    }

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("getExternalStorageDirectory", "getExternalStoragePublicDirectory");
    }

    @Override
    public void checkCall(@NonNull ClassContext context, @NonNull ClassNode classNode, @NonNull MethodNode method, @NonNull MethodInsnNode call) {
        super.checkCall(context, classNode, method, call);
        String owner = call.owner;
        if (owner.startsWith("android/os/Environment")) {
            context.report(ISSUE,
                    method,
                    call,
                    context.getLocation(call),
                    "You must use `Context.getFiles()`");
        }
    }
}
