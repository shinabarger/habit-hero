package com.test.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by adamm on 3/13/2017.
 */
public class FBConnection {
    public static final String FB_APP_ID = FBLogin.FB_APP_ID;
    public static final String FB_APP_SECRET = FBLogin.FB_APP_SECRET;
    public static final String REDIRECT_URI = "http://localhost:8080/mainPage";

    static String accessToken = "";

    public String getFBAuthUrl() {
        String fbLoginUrl = "";

        fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
                + FBConnection.FB_APP_ID + "&redirect_uri="
                + REDIRECT_URI
                + "&scope=email";

        return fbLoginUrl;
    }

    public String getFBGraphUrl(String code) {
        String fbGraphUrl = "";

        fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=" + FBConnection.FB_APP_ID + "&redirect_uri="
                + REDIRECT_URI
                + "&client_secret=" + FB_APP_SECRET + "&code=" + code;

        return fbGraphUrl;
    }

    public String getAccessToken(String code) {
        if (accessToken.equals("")) {
            URL fbGraphURL;
            try {
                fbGraphURL = new URL(getFBGraphUrl(code));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid code received " + e);
            }
            URLConnection fbConnection;
            StringBuffer b = null;
            try {
                fbConnection = fbGraphURL.openConnection();
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(
                        fbConnection.getInputStream()));
                String inputLine;
                b = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                    b.append(inputLine + "\n");
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect with Facebook "
                        + e);
            }

            accessToken = b.toString();
            if (accessToken.startsWith("{")) {
                throw new RuntimeException("ERROR: Access Token Invalid: "
                        + accessToken);
            }
        }
        return accessToken;
    }
}
