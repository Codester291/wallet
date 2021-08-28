package io.app.wallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCardDTO {

    private String firstName;
    private String lastName;

    @Size(max = 16, min = 16, message = "Card Number cannot be less or more than 16 digits")
    private String cardNumber;
    @Pattern(regexp = "^[0-9]+$", message = "CCV cannot be text")
    @Size(max = 3, min = 3, message = "CCV cannot be less or more than 3 digits")
    private String ccv;

    @JsonFormat(pattern = "MM-yyyy")
    private Date expiryDate;
}
