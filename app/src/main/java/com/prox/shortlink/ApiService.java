package com.prox.shortlink;

import com.prox.shortlink.response.EditLinkResponse;
import com.prox.shortlink.response.GetAllLinkResponse;
import com.prox.shortlink.response.LoginResponse;
import com.prox.shortlink.response.OverviewResponse;
import com.prox.shortlink.response.StoreLinkResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("links/overview")
    Call<OverviewResponse> getOverviewData(@Header("Authorization") String authorization);

    @GET("links/get")
    Call<GetAllLinkResponse> getLinks(
            @Header("Authorization") String authorization,
            @Query("page") int page
    );

    @FormUrlEncoded
    @POST("links/store")
    Call<StoreLinkResponse> storeLink(
            @Header("Authorization") String authorization,
            @Field("link") String link,
            @Field("short_link") String shortLink,
            @Field("password") String password,
            @Field("privacy") String privacy
    );

    @FormUrlEncoded
    @PUT("links/update/{currentShortLink}")
    Call<EditLinkResponse> updateLink(
            @Header("Authorization") String authorization,
            @Path("currentShortLink") String currentShortLink,
            @Field("short_link") String newShortLink,
            @Field("link") String link,
            @Field("password") String password,
            @Field("privacy") String privacy
    );
}
