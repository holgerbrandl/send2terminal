/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


public class S2TConfigurable implements Configurable {

    private S2TSettingsPanel panel;


    public String getDisplayName() {
        return "Send Code To Terminal";
    }


    @Nullable
    public String getHelpTopic() {
        return null;
    }


    public JComponent createComponent() {
        panel = new S2TSettingsPanel();
        panel.load(S2TSettings.getInstance());
        return panel.getPanel();
    }


    public boolean isModified() {
        return panel.isModified(S2TSettings.getInstance());
    }


    public void apply() throws ConfigurationException {
        panel.save(S2TSettings.getInstance());
    }


    public void reset() {
        panel.load(S2TSettings.getInstance());
    }


    public void disposeUIResources() {
        // Anything???
    }
}
