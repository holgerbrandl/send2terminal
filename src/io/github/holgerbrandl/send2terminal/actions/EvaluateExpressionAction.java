package io.github.holgerbrandl.send2terminal.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.holgerbrandl.send2terminal.connectors.ConnectorUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author Holger Brandl
 */
public class EvaluateExpressionAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent actionEvent) {

        final EditorEx editor = (EditorEx) CommonDataKeys.EDITOR.getData(actionEvent.getDataContext());

        if (editor == null) {
            return;
        }

        final Project project = editor.getProject();
        if (project == null) {
            return;
        }

        final PsiFile psiFile = PsiManager.getInstance(project).findFile(editor.getVirtualFile());
        if (psiFile != null) {
            final CaretModel caretModel = editor.getCaretModel();
            PsiElement element = psiFile.findElementAt(caretModel.getOffset());

            if (element == null) return; // should never happen, but who knows

            // grow it until we hit the file barrier
            while (element.getParent() != null && !isStopBarrier(element.getParent())) {
                element = element.getParent();
            }

            VirtualFile virtualFile = actionEvent.getData(PlatformDataKeys.VIRTUAL_FILE);
            if (virtualFile != null) {
                KotlinImportUtil.autoSendImports(editor, virtualFile);
            }

            String expressionText = element.getText();

//            editor.getSelectionModel().setSelection(element.getStartOffsetInParent(), element.getStartOffsetInParent()+ expressionText.length());

            ConnectorUtils.sendText(expressionText, psiFile.getFileType());

            // set caret to next downstream element
            // todo add preference to either select or move caret transition after eval
            PsiElement nextSibling = PsiTreeUtil.skipSiblingsForward(element, PsiWhiteSpace.class, LeafPsiElement.class);

            if (nextSibling != null) {
//                int siblingTextPos = nextSibling.getTextOffset() + nextSibling.getTextLength();
                int siblingTextPos = nextSibling.getTextOffset();
                editor.getCaretModel().getCurrentCaret().moveToOffset(siblingTextPos);
            }
        }
    }

    private boolean isStopBarrier(PsiElement parent) {
        return parent instanceof PsiFile || isFunDef(parent) || isScriptExpression(parent);
    }

    private boolean isFunDef(PsiElement element) {
        PsiElement parentParent = element.getParent();
        return parentParent.getClass().getName().contains("KtNamedFunction");
    }

    private boolean isScriptExpression(@NotNull PsiElement element) {
//        PsiElement parent = element.getParent();

        // what a mess ...
        return element.getParent() != null &&
//                element.getParent().getParent()!=null &&
//                element.getParent().getParent().getParent() !=null &&
                element.getParent().getClass().getName().endsWith("psi.KtScript");
    }
}
