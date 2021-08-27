package io.app.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityCardDTO {

    private String firstName;
    private String lastName;
    @Size(max = 11, min = 11, message = "Identity Number cannot be less or more than 11 digits")
    private String identityNumber;

}
