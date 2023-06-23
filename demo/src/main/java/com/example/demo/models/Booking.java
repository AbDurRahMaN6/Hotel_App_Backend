package com.example.demo.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;


import java.time.LocalDate;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    private LocalDate startDate;
    private LocalDate endDate;

}
