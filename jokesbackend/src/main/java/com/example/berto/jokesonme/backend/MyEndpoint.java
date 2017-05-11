/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.berto.jokesonme.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;
import com.example.MyJokes;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.jokesonme.berto.example.com",
                ownerName = "backend.jokesonme.berto.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    @ApiMethod(name = "getJoke")
    public MyJokes getJoke(@Nullable @Named("mMyJokes") String mMyjokes) {
        MyJokes response = new MyJokes();
        response.getmMyJokes();
        return response;
    }

}
