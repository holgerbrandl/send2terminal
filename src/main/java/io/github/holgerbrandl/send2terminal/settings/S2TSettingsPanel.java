/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import io.github.holgerbrandl.send2terminal.Utils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Arrays;


public class S2TSettingsPanel {

    public static final String EVAL_TARGET_MACOS_ITERM = "iTerm";
    public static final String EVAL_TARGET_R = "R";
    public static final String EVAL_TARGET_WINDOWS_CONEMU = "Cmdr/ConEmu";
    public static final String EVAL_TARGET_MACOS_TERMINAL = "Terminal";
    public static final String EVAL_TARGET_NA = "<NA>";


    private JComponent rootPanel;
    private JTextField evalTitle1;
    private JTextField evalCode1;
    private JTextField evalTitle2;
    private JTextField evalTitle3;
    private JTextField evalCode2;
    private JTextField evalCode3;
    private JTextField evalTitle4;
    private JTextField evalCode4;
    private JComboBox codeEvalTarget;
    private JCheckBox keepFocusInEditorCheckBox;
    private JCheckBox usePasteModeForCheckBox;
    private TextFieldWithBrowseButton conemuPathField;


    public static String[] getEvalTargetOptions() {
        return Utils.isMacOSX() ?
                new String[]{EVAL_TARGET_MACOS_TERMINAL, EVAL_TARGET_MACOS_ITERM, EVAL_TARGET_R} : // macos
                Utils.isWindowsPlatform() ? new String[]{EVAL_TARGET_R, EVAL_TARGET_WINDOWS_CONEMU} : // windows
                        new String[]{EVAL_TARGET_NA}; //linux :-(
    }


    public JComponent getPanel() {
        return rootPanel;
    }


    public void load(@NotNull S2TSettings settings) {
        for (int i = 0; i < settings.getEvalActionPrefs().size(); i++) {
            EvalActionPref evalActionPref = settings.getEvalActionPrefs().get(i);
            switch (i) {
                case 0:
                    evalTitle1.setText(evalActionPref.getName());
                    evalCode1.setText(evalActionPref.getCode());
                    break;
                case 1:
                    evalTitle2.setText(evalActionPref.getName());
                    evalCode2.setText(evalActionPref.getCode());
                    break;
                case 2:
                    evalTitle3.setText(evalActionPref.getName());
                    evalCode3.setText(evalActionPref.getCode());
                    break;
                case 3:
                    evalTitle4.setText(evalActionPref.getName());
                    evalCode4.setText(evalActionPref.getCode());
                    break;
            }
        }

        codeEvalTarget.setSelectedItem(settings.codeSnippetEvalTarget == null ? getEvalTargetOptions()[0] : settings.codeSnippetEvalTarget);
        keepFocusInEditorCheckBox.setSelected(settings.keepFocusInEditor);
        usePasteModeForCheckBox.setSelected(settings.usePasteMode);
        conemuPathField.setText(settings.conemuPath != null ? settings.conemuPath : "NA");
    }


    public boolean isModified(@NotNull S2TSettings settings) {
        return true;
//                || !settings.arcInitializationFile.equals(arcInitializationFileField.getText());
    }


    public void save(@NotNull S2TSettings settings) {

        settings.evalActionPrefs = Arrays.asList(
                new EvalActionPref(evalTitle1.getText(), evalCode1.getText(), S2TSettings.SNIPACTION_1_DEF_SHORTCUT),
                new EvalActionPref(evalTitle2.getText(), evalCode2.getText(), S2TSettings.SNIPACTION_2_DEF_SHORTCUT),
                new EvalActionPref(evalTitle3.getText(), evalCode3.getText(), S2TSettings.SNIPACTION_3_DEF_SHORTCUT),
                new EvalActionPref(evalTitle4.getText(), evalCode4.getText(), S2TSettings.SNIPACTION_4_DEF_SHORTCUT)
        );

        settings.codeSnippetEvalTarget = codeEvalTarget.getSelectedItem().toString();
        settings.keepFocusInEditor = keepFocusInEditorCheckBox.isSelected();
        settings.usePasteMode = usePasteModeForCheckBox.isSelected();
        settings.conemuPath = conemuPathField.getText();
    }


    private void createUIComponents() {
        String[] evalTargetOptions = getEvalTargetOptions();
        codeEvalTarget = new JComboBox(new DefaultComboBoxModel(evalTargetOptions));

        conemuPathField = new TextFieldWithBrowseButton();

        final FileChooserDescriptor interpreterDescriptor = FileChooserDescriptorFactory.createSingleLocalFileDescriptor();
        conemuPathField.addBrowseFolderListener("Choose ConEmuC Path", "Choose ConEmuC Path", null, interpreterDescriptor);
    }
}
