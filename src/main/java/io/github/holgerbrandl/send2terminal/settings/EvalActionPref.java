/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package io.github.holgerbrandl.send2terminal.settings;

import com.intellij.util.xmlb.annotations.Tag;


/**
 * DOCUMENT ME!
 *
 * @author Holger Brandl
 */
@Tag("evalactionconfig")
public class EvalActionPref {

    private String name;
    private String code;
    private String defShortCut;


    public EvalActionPref() {
    }


    public EvalActionPref(String name, String code, String defShortCut) {

        this.name = name;
        this.code = code;
        this.defShortCut = defShortCut;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getDefShortCut() {
        return defShortCut;
    }


    public void setDefShortCut(String defShortCut) {
        this.defShortCut = defShortCut;
    }
}
