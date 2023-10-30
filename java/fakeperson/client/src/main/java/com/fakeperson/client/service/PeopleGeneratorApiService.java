package com.fakeperson.client.service;

import com.fakeperson.client.model.dto.Person;
import com.fakeperson.client.model.dto.PersonList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class PeopleGeneratorApiService {
//    private final RestTemplate restTemplate;
//    @Autowired
//    public PeopleGeneratorApiService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    public List<Person> getRandomPeople(int count) throws IOException, InterruptedException {

//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.set("ACCEPT_ENCODING", "gzip");
//
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//        var uri = URI.create("https://peoplegeneratorapi.live/api/person/" + count);
//
//        var response = restTemplate.exchange(uri, HttpMethod.GET, entity, PersonList.class);
//
//        if(response.getStatusCode().is2xxSuccessful()) {
//            return response.getBody();
//        } else {
//            return new PersonList();
//        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                    "https://peoplegeneratorapi.live/api/person/" + count))
                .GET()
                .build();
        HttpResponse<byte[]> response = client.send(request,
                HttpResponse.BodyHandlers.ofByteArray());
        var body = decompressGzip(response.body());
        System.out.println(body);
        return new ObjectMapper().readValue(body, PersonList.class);
    }

    private static String decompressGzip(byte[] compressedData) {
        try (GZIPInputStream gis = new GZIPInputStream(
                new ByteArrayInputStream(compressedData))) {
            return new String(gis.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) { return null; }
    }
}
