package com.example.demo.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "payments")
public class Payment {
    @Id
    private String id;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private int cvv;
    private double amount;
}
