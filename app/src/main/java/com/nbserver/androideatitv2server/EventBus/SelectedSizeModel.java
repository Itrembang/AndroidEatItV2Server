package com.nbserver.androideatitv2server.EventBus;

import com.nbserver.androideatitv2server.Model.SizeModel;

public class SelectedSizeModel {

    private SizeModel sizeModel;

    public SelectedSizeModel(SizeModel sizeModel) {
        this.sizeModel = sizeModel;
    }

    public SizeModel getSizeModel() {
        return sizeModel;
    }

    public void setSizeModel(SizeModel sizeModel) {
        this.sizeModel = sizeModel;
    }
}
