package com.example.bookYourCab.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequest {
    private String name;
    private int age;
    private String email;
}
