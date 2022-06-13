package com.example.store_automation.response;

import com.example.store_automation.model.dto.SalesDto;
import com.example.store_automation.model.entity.ProductInBranch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TransferResponse<T> {

    public ResponseEntity<?> insufficientQuantity(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    public ResponseEntity<?> onFailureSelling(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(message);
    }

    public ResponseEntity<?> onSuccessSelling(SalesDto salesDto) {
        return ResponseEntity.status(HttpStatus.OK).
                body(salesDto);
    }

    public ResponseEntity<?> onFailureReturning(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(message);
    }

    public ResponseEntity<?> onSuccessReturning(T dto) {
        return ResponseEntity.status(HttpStatus.OK).
                body(dto);
    }
}
