package com.example.bookYourCab.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverResponse {
    private String name;
    private int age;
    private String email;
}
