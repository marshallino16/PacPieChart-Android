package com.genyus.pacpiechart.library;

/**
 * Created by genyus on 18/09/15.
 */
public enum PacpieState {

    WAIT(0),
    IS_READY_TO_DRAW(1),
    IS_DRAW(2),
    START_INC(3);

    public int stateCode;

    PacpieState(int stateCode){
        this.stateCode = stateCode;
    }
}
