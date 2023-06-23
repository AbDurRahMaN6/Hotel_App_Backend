package com.example.demo.controller;

import com.example.demo.models.Booking;
import com.example.demo.models.HotelDetails;
import com.example.demo.models.Rooms;
import com.example.demo.repository.HotelRepository;
import com.example.demo.request.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @PostMapping("/hotels")
    public ResponseEntity<HotelDetails> createHotels(@RequestBody HotelDetails hotel) {
        try {
            HotelDetails _hotel = hotelRepository
                    .save(new HotelDetails(
                            hotel.getId(),
                            hotel.getHotelName(),
                            hotel.getDistrict(),
                            hotel.getAddress(),
                            hotel.getPhoneNumber(),
                            hotel.getImage(),
                            hotel.getDesc(),
                            hotel.getRooms()));
            return new ResponseEntity<>(_hotel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDetails>> getSearchHotels(@RequestParam(required = false) String hotelName) {
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

    @GetMapping("/hotels/{id}")

    public ResponseEntity<HotelDetails> getDevicesById(@PathVariable("id") String id) {
        Optional<HotelDetails> hotelsData = hotelRepository.findById(id);

        if (hotelsData.isPresent()) {
            return new ResponseEntity<>(hotelsData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<HotelDetails> updateHotels(@PathVariable("id") String id, @RequestBody HotelDetails hotel) {
        Optional<HotelDetails> hotelData = hotelRepository.findById(id);


        if (hotelData.isPresent()) {
            HotelDetails _hotel = hotelData.get();
            _hotel.setHotelName(hotel.getHotelName());
            _hotel.setDistrict(hotel.getDistrict());
            _hotel.setAddress(hotel.getAddress());
            _hotel.setPhoneNumber(hotel.getPhoneNumber());
            _hotel.setImage(hotel.getImage());
            _hotel.setDesc(hotel.getDesc());
            _hotel.setRooms(hotel.getRooms());

            HotelDetails hotelPersisted = hotelRepository.save(_hotel);
            return new ResponseEntity<>(hotelPersisted, HttpStatus.OK);
        }
        return null;


    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<HttpStatus> deleteHotels(@PathVariable("id") String id) {
        try {
            hotelRepository.deleteById(String.valueOf(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/hotels/{hotelId}/rooms")
    public ResponseEntity<HotelDetails> createRoomsForHotel(
            @PathVariable("hotelId") String hotelId, @RequestBody List<Rooms> rooms){
        try {
            Optional<HotelDetails> optionalHotel = hotelRepository.findById(hotelId);
            if (optionalHotel.isPresent()) {
                HotelDetails hotel = optionalHotel.get();
                hotel.getRooms().addAll(rooms);hotelRepository.save(hotel);
                return new ResponseEntity<>(hotel, HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/hotels/{hotelId}/rooms/{roomNumber}")
    public ResponseEntity<String> deleteRoomFromHotel(
            @PathVariable String hotelId,
            @PathVariable String roomNumber) {

        try {
            Optional<HotelDetails> optionalHotel = hotelRepository.findById(hotelId);
            if (optionalHotel.isPresent()) {
                HotelDetails hotel = optionalHotel.get();
                List<Rooms> rooms = hotel.getRooms();
                boolean roomFound = false;

                for (Rooms room : rooms) {
                    if (room.getRoomNumber().equals(roomNumber)) {
                        rooms.remove(room);
                        roomFound = true;
                        break;
                    }
                }

                if (roomFound) {
                    hotelRepository.save(hotel);
                    return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Room not found", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("Hotel not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/hotels/{hotelId}/rooms/{roomNumber}")
    public ResponseEntity<String> updateRoomInHotel(
            @PathVariable String hotelId,
            @PathVariable String roomNumber,
            @RequestBody Rooms updatedRoom) {

        try {
            Optional<HotelDetails> optionalHotel = hotelRepository.findById(hotelId);
            if (optionalHotel.isPresent()) {
                HotelDetails hotel = optionalHotel.get();
                List<Rooms> rooms = hotel.getRooms();
                boolean roomFound = false;

                for (Rooms room : rooms) {
                    if (room.getRoomNumber().equals(roomNumber)) {
                        room.setAvailable(updatedRoom.isAvailable());
                        room.setRoomImage(updatedRoom.getRoomImage());
                        room.setPrice(updatedRoom.getPrice());
                        room.setBookings(updatedRoom.getBookings());
                        roomFound = true;
                        break;
                    }
                }

                if (roomFound) {
                    hotelRepository.save(hotel);
                    return new ResponseEntity<>("Room updated successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Room not found", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("Hotel not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Book a room
    @PostMapping("/hotels/{id}/rooms/roomNumber/bookings")
    public ResponseEntity<String> bookRoom(
            @PathVariable String hotelId,
            @PathVariable String roomNumber,
            @RequestBody Booking booking
    ) {
        Optional<HotelDetails> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            List<Rooms> rooms = hotel.get().getRooms();
            Optional<Rooms> room = rooms.stream().filter(r -> r.getRoomNumber().equals(roomNumber)).findFirst();
//                    getId().equals(roomId)).findFirst();
            if (room.isPresent()) {
                if (isRoomAvailable(room.get(), booking.getStartDate(), booking.getEndDate())) {
                    room.get().getBookings().add(booking);
                    hotelRepository.save(hotel.get());
                    return ResponseEntity.ok("Room booked successfully.");
                } else {
                    return ResponseEntity.badRequest().body("Room is not available for the specified dates.");
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Helper method to check room availability for booking dates
    private boolean isRoomAvailable(Rooms room, LocalDate startDate, LocalDate endDate) {
        for (Booking booking : room.getBookings()) {
            if (startDate.isBefore(booking.getEndDate()) && endDate.isAfter(booking.getStartDate())) {
                return false; // Room is already booked for some or all of the requested dates
            }
        }
        return true; // Room is available for booking
    }

}

