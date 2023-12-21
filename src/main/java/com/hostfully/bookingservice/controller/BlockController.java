package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.dto.BlockRequestDto;
import com.hostfully.bookingservice.dto.BlockResponseDto;
import com.hostfully.bookingservice.dto.UpdateBlockRequestDto;
import com.hostfully.bookingservice.service.BlockService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockController {

    private final BlockService blockService;

    /**
     *create a new blocked booking of type blocked.
     * ONLY OWNER and MANAGER Roles have access to create a block
     * @param bookRequestDto
     * @return {@code BlockResponseDto}
     */
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BlockResponseDto> createBlock(@Valid @RequestBody BlockRequestDto bookRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(blockService.blockProperty(bookRequestDto));
    }

    /**
     *update arrivalDate and departureDate of a specific blocked booking
     * @param bookRequestDto
     * @return {@code UpdateBlockRequestDto}
     */
    @PutMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BlockResponseDto> updateBlock(@Valid @RequestBody UpdateBlockRequestDto bookRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(blockService.updateBlockProperty(bookRequestDto));
    }

    /**
     *delete a blocked booking
     * @param bookingId
     * @return void
     */
    @DeleteMapping(value = "{bookingId}")
    public ResponseEntity deleteBlock(@Valid @NotNull @PathVariable BigInteger bookingId){
        blockService.deleteBlockProperty(bookingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
