package io.app.wallet.repository;

import io.app.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query(nativeQuery = true, value = "select * from wallet where first_name = ?1")
    List<Wallet> fetchAllByFirstName(String firstName);
}
