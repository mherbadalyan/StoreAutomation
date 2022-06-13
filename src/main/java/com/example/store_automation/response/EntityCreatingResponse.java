package com.example.store_automation.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EntityCreatingResponse<T> {

    public ResponseEntity<?> onFailure(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(message);
    }

    public ResponseEntity<?> onSuccess(T entityDto) {
        return ResponseEntity.ok().body(entityDto);
    }
}
