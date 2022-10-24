package com.otus.spring.ui.api.commands;

public interface Command {

    void run(Object input);

    Object getResult();

}
