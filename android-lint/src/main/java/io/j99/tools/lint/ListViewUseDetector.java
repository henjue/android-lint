package io.j99.tools.lint;

import com.android.annotations.NonNull;
import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.*;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Collection;

import static com.android.SdkConstants.GRID_VIEW;
import static com.android.SdkConstants.LIST_VIEW;

/**
 * Created by apple on 5/23/16.
 */
public class ListViewUseDetector extends ResourceXmlDetector {
    public static final Issue ISSUE = Issue.create("listviewuse",
            "Don't use ListView",
            "Use RecycleView",
            Category.MESSAGES,
            5,
            Severity.WARNING,
            new Implementation(
                    ListViewUseDetector.class,
                    Scope.RESOURCE_FILE_SCOPE));

    @NonNull
    @Override
    public Speed getSpeed() {
        return Speed.FAST;
    }

    @Override
    public Collection<String> getApplicableElements() {
        return Arrays.asList(LIST_VIEW, GRID_VIEW);
    }

    @Override
    public boolean appliesTo(@NonNull ResourceFolderType folderType) {
        return folderType == ResourceFolderType.LAYOUT;
    }

    @Override
    public void visitElement(@NonNull XmlContext context, @NonNull Element element) {
        super.visitElement(context, element);
        if (element.getTagName().equals("ListView") || element.getTagName().equals("GridView")) {
            context.report(ISSUE, element, context.getLocation(element), "Please use android.support.v7.widget.RecyclerView");
        }
    }
}
