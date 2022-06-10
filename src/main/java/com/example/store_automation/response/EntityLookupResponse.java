package com.example.store_automation.response;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class EntityLookupResponse<T> {

    public ResponseEntity<?> onFailure(String entityName) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityName + " with given params does not exist.");
    }

    public ResponseEntity<?> onSuccess(T entityDto) {
        return ResponseEntity.ok().body(entityDto);
    }

    public ResponseEntity<?> onFailureTest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
