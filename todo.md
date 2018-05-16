Send To Console Brainstorming
=============================

* mandatory dependency on Send2Console / or add suggestion balloon


* send to console: jump to next line after eval (option?)
* eval current top-level expression (option?)
* also add options to send line to current run console instead
* later: potentially add separate impl for R4intellij for more smooth integration

* shortcut to evaluate current expression and proceed


* R Session has almost complete implementation for console, objects, etc

Windows Support
* I think FindWindow and SendMessage are the functions you want to use, in general.
* Tinn-R: It also pops up additional menu and toolbar when it detects Rgui running on the same computer. These addons interact with the R console and allow to submit code in part or in whole and to control R directly.
    * It seems to have some limitations
* Maybe DOM is a solution: rdom, RDCOMClient
* Or most promising, we could try to use the windows API via VBScript or C#

