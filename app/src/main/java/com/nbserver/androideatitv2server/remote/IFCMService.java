package com.nbserver.androideatitv2server.remote;

import com.nbserver.androideatitv2server.Model.FCMResponse;
import com.nbserver.androideatitv2server.Model.FCMSendData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAH-nfcPY:APA91bGS1KhVJsUilGOUKD69saIi3hLhYlPdTvleLirFVJ8dLrFTi693PAlpqHZgRea86HByT-05PbQcAmiZPbdeB5_aoqKxhY-sEAWYqwxYyjYCDt5WylL-uxjpeSgWsP6ZCEUiCx-I"
    })
    @POST("fcm/send")
    Observable<FCMResponse> sendNotification(@Body FCMSendData body);

}