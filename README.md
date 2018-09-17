Send To Terminal
================

Intellij plugin to send line or selection to terminal  

The following modes are supported

*   Send current line or selection (default shortcut `meta alt ENTER`)
*   Send current and move focus to next line with expression (default shortcut `meta alt shift ENTER`)

Supported evaluation targets are

*   Terminal (MacOS)
*   iTerm2 (MacOS)
*   R GUI (MacOS)
*   R GUI (Windows)


##  Kotlin Support

The plugin comes with special support for Kotlin, namely

1. Paste Mode Support:

With the stock Kotlin-REPL, one can not evaluate certain multi-line expressions such as
```kotlin
listOf("foo", "bar")
    .map{it+"2"}
```

Please vote for [KT-13319](https://youtrack.jetbrains.net/issue/KT-13319) to push for a REPL paste-mode. Alternatively you could use `kshell` from https://github.com/khud/sparklin and enable the paste mode support in the preferences of this plugin.

2. Automatic Import Detection

The plugin will detect imports in a kotlin document and will evaluate imports in the target terminal prior to the user selection/expression.


3. Expression Guessing

The plugin will guess the scope of the expression under the cursor when using the shortcut `ctr+alt+shift+enter`. The scope barriers are named fuctions or the file itself.


## Custom shortcut actions

Up to 4 custom actions can be defined to send the current selection/line to the evaluation target. Below some examples are shown for `R`
![](docs/.README_images/r_settings_example.png)

Those shortcut actions are exposed via the context menu and can be assigned to custom keyboard bindings:
![](docs/.README_images/contect_menu.png)



## How to build?

Clone from `https://github.com/holgerbrandl/send2terminal`, open project in Intellij, and click "Run".


## Support & Development

Feel welcome to suggest features or improvments by submitting an [issue](https://github.com/holgerbrandl/send2terminal/issues)


## References

* [Official Plugin Page ](https://plugins.jetbrains.com/idea/plugin/9409-send-to-terminal) in Jetbrains` Plugin Repository