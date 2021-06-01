package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO {
    private Integer Id;
    private String name;
    private String address;


}
