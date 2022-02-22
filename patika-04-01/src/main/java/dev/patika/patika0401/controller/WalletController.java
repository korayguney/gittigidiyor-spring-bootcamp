package dev.patika.patika0401.controller;

import dev.patika.patika0401.dto.WalletDTO;
import dev.patika.patika0401.model.Wallet;
import dev.patika.patika0401.model.WalletServiceTransactionLogger;
import dev.patika.patika0401.service.WalletService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.text.MessageFormat.format;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/save-wallet")
    public ResponseEntity<Wallet> saveWallet(@RequestBody @Valid WalletDTO walletDTO) {

        Optional<Wallet> resultOptional = walletService.save_wallet(walletDTO);

        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/deposit/{customerId}/{currency}/{amount}")
    public ResponseEntity<Wallet> depositToWallet(@PathVariable long customerId,
                                                  @PathVariable("currency") String currencyName,
                                                  @PathVariable double amount) {
        Optional<Wallet> resultOptional = walletService.deposit(customerId, currencyName, amount);

        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/withdraw/{customerId}/{currency}/{amount}")
    public ResponseEntity<Wallet> withdrawFromWallet(@PathVariable long customerId,
                                                  @PathVariable("currency") String currencyName,
                                                  @PathVariable double amount) {
        Optional<Wallet> resultOptional = walletService.withdraw(customerId, currencyName, amount);

        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    /*
        Write an endpoint to get all wallets from DB (use customer id with requestparam
        and if customer id is not exist, get all wallets with same endpoint)
     */

    @GetMapping("/getWallets")
    public ResponseEntity<List<Wallet>> getWallets(@RequestParam int customerId){
        throw new UnsupportedOperationException("Unsopprrteead..................");
    //    Optional<List<Wallet>> wallets = walletService.getWallets(customerId);

     //   return new ResponseEntity<>(wallets.get(), HttpStatus.OK);
    }

    @GetMapping("/get-transactions-by-date")
    public ResponseEntity<Page<List<WalletServiceTransactionLogger>>> getAllTransactionsWithDate(
            @ApiParam(value = "transaction query for wallet usage", example = "05/07/2021", required = true)
            @RequestParam String transactionDate,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @PageableDefault(page = 0, size = 10) Pageable pageable){
        return new ResponseEntity<>(this.walletService.getAllTransactionsWithDate(transactionDate, pageNumber, pageSize, pageable), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(Exception e) {
        String errorId = UUID.randomUUID().toString();
        if (e != null) {
            String logMessage = format("PAYMENT_ENGINE_REDIRECT_ERROR: errorId: {0}, message: {1}", errorId, e.getMessage());
        }
        return new ResponseEntity<>(errorId + " <-------------", HttpStatus.OK);
    }



}
