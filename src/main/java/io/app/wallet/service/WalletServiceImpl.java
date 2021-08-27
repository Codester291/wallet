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
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static io.app.wallet.util.Constants.*;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;

    private final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Override
    public ResponseDTO addCreditCard(CreditCardDTO creditCardDTO) {
        try {
            var todayDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            logger.info("Today's Date: {}", todayDate);
            if(todayDate.after(creditCardDTO.getExpiryDate())) {
                return new ResponseDTO("98", EXPIRED);
            }
            var creditCard = new CreditCard();

            var calendar = Calendar.getInstance();
            calendar.setTime(creditCardDTO.getExpiryDate());
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            creditCard.setFirstName(creditCardDTO.getFirstName());
            creditCard.setLastName(creditCardDTO.getLastName());
            creditCard.setCardNumber(creditCardDTO.getCardNumber());
            creditCard.setCcv(creditCardDTO.getCcv());
            creditCard.setExpiryDate(month + "/" + year);
            creditCard.setType(CREDIT_CARD);

            return new ResponseDTO("00", SUCCESSFUL, walletRepository.save(creditCard));
        } catch (Exception ex) {
            logger.info("Exception Occurred in Add Credit Card - Cause: {}", ex.getMessage());
            return new ResponseDTO("XX", ERROR_OCCURRED, null);
        }
    }

    @Override
    public ResponseDTO addIdentityCard(IdentityCardDTO identityCardDTO) {
        try {
            var identityCard = new IdentityCard();

            identityCard.setFirstName(identityCardDTO.getFirstName());
            identityCard.setLastName(identityCardDTO.getLastName());
            identityCard.setIdentityNumber(identityCardDTO.getIdentityNumber());
            identityCard.setType(ID_CARD);

            return new ResponseDTO("00", SUCCESSFUL, walletRepository.save(identityCard));

        } catch (Exception e) {
            logger.info("Exception Occurred in Add Identity Card - Cause: {}", e.getMessage());
            return new ResponseDTO("XX", ERROR_OCCURRED);
        }
    }

    @Override
    public ResponseDTO addPassport(PassportDTO passportDTO) {
        try {
            var passport = new Passport();

            if (!passportDTO.getPassportNumber().startsWith("A")) {
                return new ResponseDTO("98", FAILED, "Passport number is invalid");
            }

            passport.setFirstName(passportDTO.getFirstName());
            passport.setLastName(passportDTO.getLastName());
            passport.setMiddleName(passportDTO.getMiddleName());
            passport.setPassportNumber(passportDTO.getPassportNumber());
            passport.setType(PASSPORT);

            return new ResponseDTO("00", SUCCESSFUL, walletRepository.save(passport));
        } catch (Exception e) {
            logger.info("Exception Occurred in Add Passport - Cause: {}", e.getMessage());
            return new ResponseDTO("XX", ERROR_OCCURRED);
        }
    }

    @Override
    public ResponseDTO fetchAllInWallet() {
        try {
            return ResponseDTO.builder()
                    .statusCode("00")
                    .statusMessage(SUCCESSFUL)
                    .data(walletRepository.findAll())
                    .build();
        } catch (Exception ex) {
            logger.info("Exception Occurred while fetching all in wallet - Cause: {}", ex.getMessage());
            return ResponseDTO.builder()
                    .statusCode("99")
                    .statusMessage(FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDTO fetchAllForUser(String firstName) {
        try {
            List<Wallet> walletForUser = walletRepository.fetchAllByFirstName(firstName);
            return new ResponseDTO("00", SUCCESSFUL, walletForUser);
        } catch (Exception e) {
            logger.info("Exception Occurred in Fetch For User - Cause: {}", e.getMessage());
            return new ResponseDTO("XX", ERROR_OCCURRED);
        }
    }
}
