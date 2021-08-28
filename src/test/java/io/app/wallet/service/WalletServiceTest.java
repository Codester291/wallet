package io.app.wallet.service;

import io.app.wallet.dto.CreditCardDTO;
import io.app.wallet.dto.IdentityCardDTO;
import io.app.wallet.dto.PassportDTO;
import io.app.wallet.dto.ResponseDTO;
import io.app.wallet.model.CreditCard;
import io.app.wallet.model.IdentityCard;
import io.app.wallet.model.Passport;
import io.app.wallet.model.Wallet;
import io.app.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WalletServiceTest {

    @Autowired
    WalletService walletService;

    @MockBean
    WalletRepository walletRepository;

    Wallet wallet;

    CreditCard creditCard;
    IdentityCard identityCard;
    Passport passport;

    CreditCardDTO creditCardDTO;
    IdentityCardDTO identityCardDTO;
    PassportDTO passportDTO;
    String date;
    Date expiryDate;

    @BeforeEach
    void setUp() throws ParseException {
        wallet = new Wallet();
        wallet.setType("card");

        date = "11-2025";
        expiryDate = new SimpleDateFormat("MM-yyyy").parse(date);

        creditCard = new CreditCard();
        creditCard.setFirstName("Walter");
        creditCard.setLastName("Erling");
        creditCard.setCardNumber("1234123412341234");
        creditCard.setCcv("321");
        creditCard.setExpiryDate(date);

        identityCard = new IdentityCard();
        identityCard.setIdentityNumber("12345123451");
        identityCard.setFirstName("Walter");
        identityCard.setLastName("Erling");

        passport = new Passport();
        passport.setFirstName("Walter");
        passport.setLastName("Erling");
        passport.setPassportNumber("A12345678");


        creditCardDTO = new CreditCardDTO();
        creditCardDTO.setFirstName("Walter");
        creditCardDTO.setLastName("Erling");
        creditCardDTO.setCardNumber("1234123412341234");
        creditCardDTO.setCcv("321");
        creditCardDTO.setExpiryDate(expiryDate);

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
    void addCreditCard() {
        Mockito.when(walletRepository.save(creditCard)).thenReturn(creditCard);
        ResponseDTO responseDTO = walletService.addCreditCard(creditCardDTO);
        assertThat(responseDTO.getStatusCode(), is("00"));
    }

    @Test
    void addIdentityCard() {
        Mockito.when(walletRepository.save(identityCard)).thenReturn(identityCard);
        ResponseDTO responseDTO = walletService.addIdentityCard(identityCardDTO);
        assertThat(responseDTO.getStatusCode(), is("00"));
    }

    @Test
    void addPassport() {
        Mockito.when(walletRepository.save(passport)).thenReturn(passport);
        ResponseDTO responseDTO = walletService.addPassport(passportDTO);
        assertThat(responseDTO.getStatusCode(), is("00"));
    }

    @Test
    void fetchAllInWallet() {
        Mockito.when(walletRepository.findAll()).thenReturn(List.of(wallet));
        ResponseDTO responseDTO = walletService.fetchAllInWallet();
        assertThat(responseDTO.getStatusCode(), is("00"));
    }

    @Test
    void fetchAllForUser() {
        Mockito.when(walletRepository.fetchAllByFirstName(Mockito.any())).thenReturn(List.of(wallet));
        ResponseDTO responseDTO = walletService.fetchAllForUser(Mockito.any());
        assertThat(responseDTO.getStatusCode(), is("00"));
    }
}