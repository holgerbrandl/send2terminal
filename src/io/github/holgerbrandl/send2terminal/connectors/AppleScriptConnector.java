/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.connectors;


import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import io.github.holgerbrandl.send2terminal.Utils;
import io.github.holgerbrandl.send2terminal.settings.S2TSettings;

import java.io.IOException;


/**
 * A connector using apple script
 *
 * @author Holger Brandl
 */
public class AppleScriptConnector implements CodeLaunchConnector {

    public static void main(String[] args) {
        new AppleScriptConnector().submitCode("write.table(head(iris), file=\"~/Desktop/iris.txt\", sep=\"\\t\")\n", true);
    }


    private static void submitCodeInternal(String rCommands, boolean switchFocusToTerminal) {
        try {

            if (Utils.isMacOSX()) {
                Runtime runtime = Runtime.getRuntime();

                String dquotesExpandedText = rCommands.replace("\\", "\\\\");
                dquotesExpandedText = dquotesExpandedText.replace("\"", "\\\"");

                // trim to remove tailing newline for blocks and especially line evaluation
                dquotesExpandedText = dquotesExpandedText.trim();


                String evalTarget = S2TSettings.getInstance().codeSnippetEvalTarget;
//                String evalTarget = "R64";

//                http://stackoverflow.com/questions/1870270/sending-commands-and-strings-to-terminal-app-with-applescript

                String evalSelection;
                if (evalTarget.equals("Terminal")) {
                    evalSelection = "tell application \"" + "Terminal" + "\" to do script \"" + dquotesExpandedText + "\" in window 0";

                    if (switchFocusToTerminal) {
                        evalSelection = "tell application \"Terminal\" to activate\n" + evalSelection;
                    }

                } else if (evalTarget.equals("iTerm")) {
                    evalSelection = "tell application \"iTerm\" to tell current session of current terminal  to write text  \"" + dquotesExpandedText + "\"";
                    if (switchFocusToTerminal) {
                        evalSelection = "tell application \"iTerm\" to activate\n" + evalSelection;
                    }

                } else {
                    if (switchFocusToTerminal) {
                        evalSelection = "tell application \"" + evalTarget + "\" to activate\n" +
                                "tell application \"" + evalTarget + "\" to cmd \"" + dquotesExpandedText + "\"";
                    } else {
                        evalSelection = "tell application \"" + evalTarget + "\" to cmd \"" + dquotesExpandedText + "\"";
                    }
                }

                String[] args = {"osascript", "-e", evalSelection};

                runtime.exec(args);
            }
        } catch (IOException e1) {
            ConnectorUtils.log.error(e1);
        }
    }


    @Override
    public void submitCode(String rCommands, boolean switchFocusToTerminal) {
        // If code is long split it up into chunks, because terminal does not accept more than 1024 characters
        // See http://unix.stackexchange.com/questions/204815/terminal-does-not-accept-pasted-or-typed-lines-of-more-than-1024-characters

        Iterable<String> textChunks = Splitter.fixedLength(1000).split(rCommands);

        textChunks.forEach(chunk -> submitCodeInternal(chunk, false));

        if (switchFocusToTerminal) {
            submitCodeInternal("", true);
        }

    }
}
