package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "hotels")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelDetails {

    @Id
    private String id;
    private String hotelName;
    private String district;
    private String address;
    private String phoneNumber;
    private String image;
    private String desc;
    private List<Rooms> rooms;
}