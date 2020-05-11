package com.leverx.entity;

import com.leverx.type.SizeType;

public class Aviary {
    private SizeType type;
    private boolean clean;

    public Aviary(SizeType type, boolean clean) {
        this.type = type;
        this.clean = clean;
    }

    public void clean(){
        this.clean = true;
    }
}
