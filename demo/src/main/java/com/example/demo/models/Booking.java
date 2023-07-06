package com.example.demo.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    private LocalDate startDate;
    private LocalDate endDate;
    private String username;
    private String mobileNo;
    @Size(max = 50)
    @Email
    private String email;
}
