package io.github.holgerbrandl.send2terminal.actions;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import io.github.holgerbrandl.send2terminal.connectors.ConnectorUtils;
import io.github.holgerbrandl.send2terminal.settings.S2TSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Holger Brandl
 */
public class KotlinImportUtil {

    private static Map<String, List<String>> file2imports = new HashMap<>();

    static {

        // from https://intellij-support.jetbrains.com/hc/en-us/community/posts/205423050-listener-for-ide-actions

//            MessageBus messageBus = ed.getProject().getMessageBus();
        MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        MessageBusConnection connection = messageBus.connect();
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                System.out.println("file opened");
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                System.out.println("file closed");

                file2imports.remove(file.getPath());
            }
        });

    }


    static void autoSendImports(Editor ed, @Nullable VirtualFile virtualFile) {
        FileType fileType = virtualFile.getFileType();

        boolean autoCommitImports = S2TSettings.getInstance().usePasteMode &&
                fileType.getName().toLowerCase().equals("kotlin");


        if (!autoCommitImports) {
            return;
        }

        List<String> lines = Arrays.stream(ed.getDocument().getText().split("\n"))
                .filter(s -> s.startsWith("import ")).collect(Collectors.toList());

        String filePath = virtualFile.getPath();
        if (!file2imports.containsKey(filePath)) {
            file2imports.put(filePath, new ArrayList<>());
        }

        List<String> imported = file2imports.get(filePath);
        lines.removeAll(imported);

        if (lines.size() > 0) {
            StringWriter stringWriter = joinLines(lines);
            ConnectorUtils.sendText(stringWriter.toString(), fileType);

            imported.addAll(lines);
        }
    }

    @NotNull
    private static StringWriter joinLines(List<String> lines) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter, true);
        for (String line : lines) {
            writer.println(line);
        }
        return stringWriter;
    }
}
