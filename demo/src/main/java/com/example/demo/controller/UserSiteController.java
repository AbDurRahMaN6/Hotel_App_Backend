package com.example.demo.controller;

import com.example.demo.models.HotelDetails;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public class UserSiteController {
    @Autowired
    HotelRepository hotelRepository;
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDetails>> getUserCheckHotels(@RequestParam(required = false) String hotelName) {
        try {
            List<HotelDetails> hotels = new ArrayList<HotelDetails>();

            if (hotelName == null)
                hotelRepository.findAll().forEach(hotels::add);
            else
                hotelRepository.findByHotelName(hotelName).forEach(hotels::add);

            if (hotels.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(hotels, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
