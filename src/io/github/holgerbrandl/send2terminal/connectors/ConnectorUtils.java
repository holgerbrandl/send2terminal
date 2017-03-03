/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.connectors;

import com.intellij.openapi.diagnostic.Logger;
import io.github.holgerbrandl.send2terminal.Utils;
import io.github.holgerbrandl.send2terminal.settings.S2TSettings;


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
            return new RGWLauncher();
//        }else if (Utils.isLinux()) {
        } else {
            log.error("Platform not yet supported for code snippet evaluation");
            return null;
        }
    }


    public static void sendText(String text) {
        CodeLaunchConnector codeLaunchConnector = getPlatformConnector();
        if (codeLaunchConnector != null) {
            codeLaunchConnector.submitCode(text, !S2TSettings.getInstance().keepFocusInEditor);
        }
    }
}
