Version History of Send2Terminal
===============================

## 1.3

* Improved expression detection heuristics for kotlin
* Added automatic import push-2-console for Kotlin files: All imports of the current Kotlin file  will be automatically pushed to the console before pushing the actual user code. To clear the internal import cache (which the plugin is using to remember the imports it had pushed already) the user can use the default key binding `ctrl alt meta c`

## v1.2

* Paste mode support when sending kotlin code to terminal. Currently this feature is just enabled for Kotlin source files


## v1.1

* Added additional action to evaluate current most top-level expression in file
* Large text blocks are now chunked before sending them to terminal


## v1.0 

Initial Release.
 
This plugin was originally developed as part of [R language support for Intellij](https://github.com/holgerbrandl/r4intellij) but it now evolved separately.
            
            