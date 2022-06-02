package com.example.store_automation.response;

import com.example.store_automation.model.dto.SalesDto;
import com.example.store_automation.model.entity.ProductInBranch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TransferResponse<T> {

    public ResponseEntity<?> onFailure() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transfer with given params does not exist.");
    }

    public ResponseEntity<?> onSuccess() {
        return ResponseEntity.ok().body("Successfully money transfer.");
    }

    public ResponseEntity<?> insufficientQuantity() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient products in the branch.");
    }

    public ResponseEntity<?> incorrectAccount() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong account number in request.");
    }

    public ResponseEntity<?> incorrectCardNumber() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong card number in request.");
    }

    public ResponseEntity<?> blockedCard() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Selected card is not active.");
    }

    public ResponseEntity<?> onFailureSelling() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body("There is not product with this id in this branch or requested quantity of product.");
    }

    public ResponseEntity<?> onSuccessSelling(SalesDto salesDto) {
        return ResponseEntity.status(HttpStatus.OK).
                body(salesDto);
    }

    public ResponseEntity<?> onFailureReturning() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body("Can't return product with given parameter.");
    }

    public ResponseEntity<?> onSuccessReturning(T dto) {
        return ResponseEntity.status(HttpStatus.OK).
                body(dto);
    }
}
