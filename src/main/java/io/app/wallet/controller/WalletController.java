package io.app.wallet.controller;

import io.app.wallet.dto.CreditCardDTO;
import io.app.wallet.dto.IdentityCardDTO;
import io.app.wallet.dto.PassportDTO;
import io.app.wallet.dto.ResponseDTO;
import io.app.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wallet")
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/credit")
    public ResponseDTO createCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO) {
        return walletService.addCreditCard(creditCardDTO);
    }

    @PostMapping("/identity")
    public ResponseDTO addIdentityCard(@RequestBody @Valid IdentityCardDTO identityCardDTO) {
        return walletService.addIdentityCard(identityCardDTO);
    }

    @PostMapping("/passport")
    public ResponseDTO addPassport(@RequestBody @Valid PassportDTO passportDTO) {
        return walletService.addPassport(passportDTO);
    }

    @GetMapping
    public ResponseDTO fetchAllInWallet() {
        return walletService.fetchAllInWallet();
    }

    @GetMapping("/user")
    public ResponseDTO fetchAllForUser(@RequestParam String firstName) {
        return walletService.fetchAllForUser(firstName);
    }
}
