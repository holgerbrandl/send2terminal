/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.connectors;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import io.github.holgerbrandl.send2terminal.Utils;
import io.github.holgerbrandl.send2terminal.settings.S2TSettings;
import io.github.holgerbrandl.send2terminal.settings.S2TSettingsPanel;


/**
 * Returns an appropriate R-connector for the user platform
 *
 * @author Holger Brandl
 */
public class ConnectorUtils {

    static final Logger log = Logger.getInstance("#ConnectorUtils");


    public static CodeLaunchConnector getPlatformConnector() {
        if (Utils.isMacOSX()) {
            return new AppleScriptConnector();

        } else if (Utils.isWindowsPlatform()) {
            String evalTarget = S2TSettings.getInstance().codeSnippetEvalTarget;
            if (evalTarget.equals(S2TSettingsPanel.EVAL_TARGET_WINDOWS_CONEMU)) {
                return new ConEmuConnector();

            } else if (evalTarget.equals(S2TSettingsPanel.EVAL_TARGET_R)) {
                return new RGWLauncher();
            }
        }
//        }else if (Utils.isLinux()) {


        log.error("Platform connector for code snippet evaluation not yet supported or missing ");
        return null;
    }


    public static void sendText(String text, FileType fileType) {
        CodeLaunchConnector codeLaunchConnector = getPlatformConnector();
        if (codeLaunchConnector != null && text != null) {
            codeLaunchConnector.submitCode(text, !S2TSettings.getInstance().keepFocusInEditor, fileType);
        }

        //todo maybe we should do some else here in case there is no connector?
    }
}
