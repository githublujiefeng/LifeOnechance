package com.learn.schedul;

import java.io.Serializable;

public class SchedulDTO implements Serializable {
    private String name;
    private String corn;
    private Class schedulClass;
    private int Status;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public Class getSchedulClass() {
        return schedulClass;
    }

    public void setSchedulClass(Class schedulClass) {
        this.schedulClass = schedulClass;
    }
}

