package net.koonts;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchWord {

    FetchWord() { }

    //static word getter
    public static String genNewWord() throws IOException, InterruptedException {
        //word via GET request
        //https://random-word-api.herokuapp.com/word
        String url = "https://random-word-api.herokuapp.com/word";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(url)).build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }
}
