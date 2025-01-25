package com.example.bookYourCab.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private String name;
    private int age;
    private String email;
}
