package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


//import java.util.Date;
//import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Rooms {
    private String roomNumber;
    private boolean available;
    private String roomImage;
    private String price;
    private List<Booking> bookings;

}
