package io.app.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportDTO {

    private String firstName;
    private String lastName;
    private String middleName;

    @Size(max = 9, min = 9, message = "Passport number should not be less or more than 9 digits")
    private String passportNumber;
}
