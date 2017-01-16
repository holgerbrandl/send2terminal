/*
 * Copyright 2011-2011 Gregory Shrago
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.holgerbrandl.send2terminal.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import io.github.holgerbrandl.send2terminal.settings.EvalActionPref;
import io.github.holgerbrandl.send2terminal.settings.S2TSettings;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;


/**
 * @author brandl
 */
public class CodeEvalActionRegistry extends AbstractProjectComponent {

    public CodeEvalActionRegistry(Project project) {
        super(project);

        DefaultActionGroup actionMenu = (DefaultActionGroup) ActionManager.getInstance().getAction("CodeSnippetActionMenu");

//        DefaultActionGroup myActionGroup = new DefaultActionGroup("R Code Evaluation", true);
//        ActionManager.getInstance().registerAction("MyMenuID", myActionGroup);
//
//        actionMenu.add(myActionGroup);

//        KeyStroke keyStroke = KeyStroke.getKeyStroke("control shift S");
//        KeyboardShortcut shortcut = new KeyboardShortcut(keyStroke, null);
//
//        CustomShortcutSet shortcuts = new CustomShortcutSet(shortcut);
//        AnAction action = new ConfigurableEvalAction("mein haus", "blabal", shortcuts);
//        myActionGroup.add(action, new Constraints(Anchor.LAST, "after"));
//        ActionManager.getInstance().registerAction("customREval1", action);

        S2TSettings instance = S2TSettings.getInstance();
        if (instance != null) {
            for (EvalActionPref actionPref : instance.getEvalActionPrefs()) {
                String actionID = "send2terminal_action_" + instance.getEvalActionPrefs().indexOf(actionPref);
                if (ActionManager.getInstance().getAction(actionID) != null)
                    continue;

                AnAction action = new ConfigurableEvalAction(actionPref.getName(), actionPref.getCode(), CustomShortcutSet.fromString(actionPref.getDefShortCut()));
                actionMenu.add(action, new Constraints(Anchor.LAST, "after"));
                ActionManager.getInstance().registerAction(actionID, action);
            }
        }
    }


    @NonNls
    @NotNull
    public String getComponentName() {
        return "CodeEvalActionRegistry";
    }

}
