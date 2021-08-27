package io.app.wallet.service;

import io.app.wallet.dto.CreditCardDTO;
import io.app.wallet.dto.IdentityCardDTO;
import io.app.wallet.dto.PassportDTO;
import io.app.wallet.dto.ResponseDTO;

public interface WalletService {

    ResponseDTO addCreditCard(CreditCardDTO creditCardDTO);
    ResponseDTO addIdentityCard(IdentityCardDTO identityCardDTO);
    ResponseDTO addPassport(PassportDTO passportDTO);
    ResponseDTO fetchAllInWallet();
    ResponseDTO fetchAllForUser(String firstName);

}
