package com.milaboratory.mitools.cli;

/**
 * Created by dbolotin on 20/08/14.
 */
public interface Action {
    void go() throws Exception;

    String command();

    ActionParameters params();
}
