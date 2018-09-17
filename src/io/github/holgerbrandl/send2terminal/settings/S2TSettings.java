/*
 * Copyright 2011 Holger Brandl
 *
 * This snippet is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.annotations.AbstractCollection;
import com.intellij.util.xmlb.annotations.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Settings of our R plugins
 */
@State(
        name = "Send2Terminal",
        storages = @Storage("send-2-terminal-settings.xml")
)
public class S2TSettings implements PersistentStateComponent<S2TSettings> {

    public static final String SNIPACTION_1_DEF_SHORTCUT = "meta alt H";
    public static final String SNIPACTION_2_DEF_SHORTCUT = "meta alt S";
    public static final String SNIPACTION_3_DEF_SHORTCUT = "meta alt T";
    public static final String SNIPACTION_4_DEF_SHORTCUT = "meta alt T";
    // custom eval actions
    public List<EvalActionPref> evalActionPrefs = new ArrayList<EvalActionPref>();
    public String codeSnippetEvalTarget = S2TSettingsPanel.getEvalTargetOptions()[0];
    public boolean keepFocusInEditor = true;
    public boolean usePasteMode = false;

    {
        evalActionPrefs.add(new EvalActionPref("head+nrow", "head(%snippet%); nrow(%snippet%);", SNIPACTION_1_DEF_SHORTCUT));
        evalActionPrefs.add(new EvalActionPref("structure", "str(%snippet%);", SNIPACTION_2_DEF_SHORTCUT));
        evalActionPrefs.add(new EvalActionPref("help", "?%snippet%", SNIPACTION_3_DEF_SHORTCUT));
        evalActionPrefs.add(new EvalActionPref("summarize", "summarize(%snippet%);", SNIPACTION_4_DEF_SHORTCUT));
    }


    public static S2TSettings getInstance() {
        return ServiceManager.getService(S2TSettings.class);
    }


    public S2TSettings getState() {
        return this;
    }


    public void loadState(@NotNull S2TSettings that) {
        this.evalActionPrefs = that.getEvalActionPrefs();
        this.codeSnippetEvalTarget = that.codeSnippetEvalTarget;
        this.keepFocusInEditor = that.keepFocusInEditor;
        this.usePasteMode = that.usePasteMode;
    }


    @Tag("evalActionPrefs")
    @AbstractCollection(surroundWithTag = false)
    public List<EvalActionPref> getEvalActionPrefs() {
        return evalActionPrefs;
    }


    public void setEvalActionPrefs(List<EvalActionPref> evalActionPrefs) {
        this.evalActionPrefs = evalActionPrefs;
    }
}
