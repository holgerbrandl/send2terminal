/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.connectors;


import com.intellij.openapi.fileTypes.FileType;
import io.github.holgerbrandl.send2terminal.Utils;
import io.github.holgerbrandl.send2terminal.settings.S2TSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;


/**
 * A connector using apple script
 *
 * @author Holger Brandl
 */
public class ConEmuConnector implements CodeLaunchConnector {

    public static void main(String[] args) {
//        new ConEmuConnector().submitCode("write.table(head(iris), file=\"~/Desktop/iris.txt\", sep=\"\\t\")\n", true, null);
        new ConEmuConnector().submitCode("ls -la", true, null);
    }


    private static void submitCodeInternal(@NotNull String codeSelection, @Nullable FileType fileType) {

        if (!Utils.isWindowsPlatform()) {
            ConnectorUtils.log.error("Can not submit code to ConEmu on nonWindows OS");
            return;
        }


        // post-fix cmds with enter (not necessary for line eval, but enter seems missing in expression eval)
        if (!codeSelection.endsWith("\n")) {
            codeSelection += "\n";
        }

//        String conEmuExecutable = "C:\\Users\\brandl\\Downloads\\cmder\\vendor\\conemu-maximus5\\ConEmu\\ConEmuC.exe";
        String conEmuExecutable = S2TSettings.getInstance().conemuPath;

        if (!new File(conEmuExecutable).isFile() || !new File(conEmuExecutable).canExecute()) {
            ConnectorUtils.log.error("Path to ConEmuC is invalid or missing: " + conEmuExecutable);
            return;
        }

        try {
            // see https://conemu.github.io/en/GuiMacro.html#Paste
//            Process p = new ProcessBuilder().command(conEmuExecutable, "-GuiMacro:0", "Paste(2,\"" + codeSelection + "\")").start();
//            Process p = new ProcessBuilder().command(conEmuExecutable, "-GuiMacro:0", "Paste(2,\"\"ls -la\"\")").start();
//            Process p = new ProcessBuilder().command(conEmuExecutable, "-GuiMacro:0", "Paste", "2", "ls -la\nhead\n").start();


            // wrap with double quotes as described at https://conemu.github.io/en/GuiMacro.html#Command_line
            String escapedSnippet = "\"" + codeSelection.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
            Process p = new ProcessBuilder().command(conEmuExecutable, "-GuiMacro:0", "Paste", "2", escapedSnippet).start();
            p.waitFor();
        } catch (final Exception e) {
            ConnectorUtils.log.error(e);
        }
    }


    @Override
    public void submitCode(@NotNull String codeSelection, boolean switchFocusToTerminal, FileType fileType) {
        submitCodeInternal(codeSelection, fileType);
    }
}
