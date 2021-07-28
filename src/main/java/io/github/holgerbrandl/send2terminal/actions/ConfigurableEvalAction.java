/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.actions;

import com.intellij.openapi.actionSystem.CustomShortcutSet;


/**
 * DOCUMENT ME!
 *
 * @author Holger Brandl
 */
public class ConfigurableEvalAction extends AbstactEvalTextAction {


    private String codeTemplate;


    public ConfigurableEvalAction(String name, String codeTemplate, CustomShortcutSet shortcuts) {
        super(name, codeTemplate, shortcuts);

        this.codeTemplate = codeTemplate;
    }


    @Override
    protected String getEvalCmd(String selectedText) {
        return codeTemplate.replace("%snippet%", selectedText);
    }


    @Override
    public boolean isDumbAware() {
        return true;
    }
}
