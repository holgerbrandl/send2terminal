/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.connectors;

import com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CodeLaunchConnector {


    /**
     * Submit commands to R.
     *
     * @param codeSelection     array with commands
     * @param moveFousToEvalTarget if <code>true</code>, switch focus console, else does not change the focus.
     * @param fileType
     * @return <code>false</code>, if not successful, otherwise <code>true</code> (hint)
     */
    void submitCode(@NotNull String codeSelection, boolean moveFousToEvalTarget, @Nullable FileType fileType);
}
