package com.example.store_automation.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EntityUpdatingResponse<T> {


    public ResponseEntity<?> onFailure(String entityName) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body("There is problem with updating " + entityName + " with this parameter.");
    }

    public ResponseEntity<?> onSuccess(T entityDto) {
        return ResponseEntity.ok().body(entityDto);
    }
}
