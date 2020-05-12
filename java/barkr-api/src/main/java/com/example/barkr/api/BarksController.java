package com.example.barkr.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Simple all-in-one REST API controller for storing and retrieving Barks. Barks (posts) are stored in memory.
 * <p>
 * If we were to support, say, persistence, we would push that concern down to some service layer object
 * (and possibly some persistence/repository object below that).
 */
@RestController
public class BarksController {

    // we'll store our barks (posts) in a hash map from generated ID to Bark object
    private Map<String, Bark> barks = new HashMap<>();

    /**
     * Handler for HTTP POST requests for saving Barks (posts).
     *
     * @param bark
     * @return
     */
    @PostMapping("/barks")
    public Bark saveBark(@RequestBody Bark bark) {
        if (bark.getId() != null) {
            Bark savedBark = saveBarkInternal(bark);
            if (null != savedBark) {
                return savedBark;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        } else {
            // the incoming bark already has an ID, so cowardly bail on saving it again
            return bark;
        }
    }

    /**
     * Handler for HTTP GET requests for fetching previously-stored Barks (posts).
     *
     * @param barkId
     * @return
     */
    @GetMapping("/barks/{barkId}")
    public Bark getBark(@PathVariable String barkId) {
        Bark bark = getBarkInternal(barkId);
        if (null != bark) {
            return bark;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Saves the Bark - generate a UUID and use it as the key into the barks map.
     */
    private Bark saveBarkInternal(Bark bark) {
        if (null != bark) {
            Bark barkToSave = new Bark(UUID.randomUUID().toString(),
                    LocalDateTime.now(),
                    bark.getTitle(),
                    bark.getContent()
            );
            this.barks.put(barkToSave.getId(), barkToSave);
            return barkToSave;
        }
        return null;
    }

    /**
     * Returns the Bark (post) from the underlying map with the specified ID, or null if no such Bark exists.
     *
     * @param barkId
     * @return
     */
    private Bark getBarkInternal(String barkId) {
        Bark bark = this.barks.get(barkId);
        return bark;
    }
}
