package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.dto.BlockRequestDto;
import com.hostfully.bookingservice.dto.BlockResponseDto;
import com.hostfully.bookingservice.dto.UpdateBlockRequestDto;

import java.math.BigInteger;

public interface BlockService {

    /**
     *create a new blocked booking of type blocked.
     * ONLY OWNER and MANAGER Roles have access to create a block
     * @param bookingRequestDto
     * @return {@code BlockResponseDto}
     */
    BlockResponseDto blockProperty(BlockRequestDto bookingRequestDto);

    /**
     *update arrivalDate and departureDate of a specific blocked booking
     * @param bookingRequestDto
     * @return {@code UpdateBlockRequestDto}
     */
    BlockResponseDto updateBlockProperty(UpdateBlockRequestDto bookingRequestDto);

    /**
     *delete a blocked booking
     * @param bookingId
     * @return void
     */
    void deleteBlockProperty(BigInteger bookingId);
}
