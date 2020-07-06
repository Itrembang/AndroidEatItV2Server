package com.nbserver.androideatitv2server.Callback;

import com.nbserver.androideatitv2server.Model.OrderModel;

import java.util.List;

public interface IOrderCallbackListener {
    void onOrderLoadSuccess(List<OrderModel> orderList);
    void onOrderLoadFailed(String message);
}
