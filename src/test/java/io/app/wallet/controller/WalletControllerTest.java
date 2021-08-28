package io.app.wallet.controller;

import com.google.gson.Gson;
import io.app.wallet.dto.CreditCardDTO;
import io.app.wallet.dto.IdentityCardDTO;
import io.app.wallet.dto.PassportDTO;
import io.app.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class WalletControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    WalletController walletController;

    @Mock
    WalletService walletService;

    CreditCardDTO creditCardDTO;
    IdentityCardDTO identityCardDTO;
    PassportDTO passportDTO;

    Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();

        creditCardDTO = new CreditCardDTO();
        creditCardDTO.setFirstName("Walter");
        creditCardDTO.setLastName("Erling");
        creditCardDTO.setCardNumber("1234123412341234");
        creditCardDTO.setCcv("321");
        creditCardDTO.setExpiryDate(null);

        identityCardDTO = new IdentityCardDTO();
        identityCardDTO.setIdentityNumber("12345123451");
        identityCardDTO.setFirstName("Walter");
        identityCardDTO.setLastName("Erling");

        passportDTO = new PassportDTO();
        passportDTO.setFirstName("Walter");
        passportDTO.setLastName("Erling");
        passportDTO.setPassportNumber("A12345678");
    }

    @Test
    void createCreditCard() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/wallet/credit")
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(creditCardDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addIdentityCard() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/wallet/identity")
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(identityCardDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addPassport() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/wallet/passport")
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(passportDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void fetchAllInWallet() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/wallet"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void fetchAllForUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/wallet/user")
                .param("firstName", "Walter"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenInputIsInvalid() throws Exception {
        IdentityCardDTO identityCardDTO = new IdentityCardDTO();
        identityCardDTO.setFirstName("");
        identityCardDTO.setLastName("");
        identityCardDTO.setIdentityNumber("1223");
        mockMvc.perform(MockMvcRequestBuilders.post("/wallet/identity")
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(identityCardDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }
}