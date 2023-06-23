package com.example.demo.repository;

import com.example.demo.models.HotelDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HotelRepository extends MongoRepository<HotelDetails, String> {
    List<HotelDetails> findByHotelName(String hotelName);
}
