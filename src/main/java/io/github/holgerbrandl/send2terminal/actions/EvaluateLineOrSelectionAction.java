/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.holgerbrandl.send2terminal.connectors.ConnectorUtils;


/**
 * Event handler for the "Run Selection" action within an Arc code editor - runs the currently selected text within the
 * current REPL.
 */
public class EvaluateLineOrSelectionAction extends AnAction {


    public void actionPerformed(AnActionEvent actionEvent) {
        Editor ed = actionEvent.getData(PlatformDataKeys.EDITOR);
        if (ed == null) {
            return;
        }

        VirtualFile virtualFile = actionEvent.getData(PlatformDataKeys.VIRTUAL_FILE);
        FileType fileType = virtualFile.getFileType();

        KotlinImportUtil.autoSendImports(ed, virtualFile);


        String text = ed.getSelectionModel().getSelectedText();
        if (StringUtil.isEmptyOrSpaces(text)) {
            ed.getSelectionModel().selectLineAtCaret();
            text = ed.getSelectionModel().getSelectedText();
        }

        ConnectorUtils.sendText(text, fileType);
    }


}
