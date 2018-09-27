package io.github.holgerbrandl.send2terminal.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class ClearImportCache extends AnAction implements DumbAware {

    public ClearImportCache() {
//        getTemplatePresentation().setIcon(RFileType.INSTANCE.getIcon());
    }


//    public RConsoleAction(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
//        super("", "", RFileType.INSTANCE.getIcon());
//    }


    @Override
    public void actionPerformed(@NotNull final AnActionEvent event) {
        KotlinImportUtil.file2imports.clear();
        final Project project = CommonDataKeys.PROJECT.getData(event.getDataContext());
        if (project == null) return;
/*

        // make sure that interpreter is set
        if (!RSettings.hasInterpreter()) {
            DialogBuilder db = new DialogBuilder();

            db.setTitle("Could not start R Console");
            db.setCenterPanel(new JLabel("No interpreter defined. You can set one under Preferences->Custom Languages->R"));
            db.addOkAction();
            db.show();

            return;
        }
*/
    }


    @Override
    public void update(@NotNull final AnActionEvent e) {
        final Project project = CommonDataKeys.PROJECT.getData(e.getDataContext());
        e.getPresentation().setVisible(project != null);
    }
}
