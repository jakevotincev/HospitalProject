package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO implements Serializable {
    private static final long serialVersionUID = -2986042890589815474L;
    private Integer Id;
    private String name;
    private String address;
}
