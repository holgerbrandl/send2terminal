## Roadmap

* Allow for language specific shortcut actions via extension point


## paste mode

```bash

#https://stackoverflow.com/questions/4996334/send-a-key-code-to-an-application-without-activating-it-first
osascript -e '
activate application "Terminal"
tell application  "System Events"
    keystroke "d" using {control down}
end tell
activate me
'



#https://discussions.apple.com/thread/6820749

osascript -e '
tell application "System Events"
    set currentApplication to name of 1st process whose frontmost is true
end tell

activate application "Terminal"
tell application  "System Events"
    keystroke "d" using {control down}
end tell

activate application currentApplication
'

osascript -e '
tell application "System Events"
    set currentApplication to path to frontmost application as text
end tell

activate application "Terminal"
tell application  "System Events"
    keystroke "d" using {control down}
end tell

activate application currentApplication
'
```


https://apple.stackexchange.com/questions/158854/how-to-differentiate-between-applications-with-the-same-name-in-applescripts
> When you type `tell application "whatever"` and you click compile, if `whatever` does not exists, it will appear a window that let you choose the application. Try this way.


## ConEmu Support

paste docs <https://conemu.github.io/en/GuiMacro.html#Paste>

from <https://conemu.github.io/en/GuiMacro.html#Command_line> -GuiMacro:0  --> 0 -> Special value means 'Use active tab/split of the first found ConEmu window


```
C:\Users\brandl\Downloads\cmder\vendor\conemu-maximus5\ConEmu.exe -GuiMacro:0 Paste(2,"ls")

C:\Users\brandl\Downloads\cmder\vendor\conemu-maximus5\ConEmu\ConEmuC64.exe -GuiMacro:0 Paste(2,"ls")

C:\Users\brandl\Downloads\cmder\vendor\conemu-maximus5\ConEmu\ConEmuC64.exe -GuiMacro:0 Paste(2,"foo(\"lala\")")

C:\Users\brandl\Downloads\cmder\vendor\conemu-maximus5\ConEmu\ConEmuC64.exe -GuiMacro:0 Paste 2 foo(\"lala\")
C:\Users\brandl\Downloads\cmder\vendor\conemu-maximus5\ConEmu\ConEmuC64.exe -GuiMacro:0 Paste 2 "foo( \"lala\")"
C:\Users\brandl\Downloads\cmder\vendor\conemu-maximus5\ConEmu\ConEmuC64.exe -GuiMacro:0 Paste 2 "foo(\"lala\")"

C:\Users\brandl\Downloads\cmder\vendor\conemu-maximus5\ConEmu\ConEmuC64.exe  -GuiMacro:0 Keys("Return")
```