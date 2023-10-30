package com.fakeperson.client.model.dto;

public record Address(String streetAddress, String city, String state, String country, int zip, int geonameId,
                      String phoneNumber, String ipAddress, String countryCode) {
}
