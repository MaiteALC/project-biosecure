package br.com.biosecure.dto.response;

public sealed interface CustomerDto permits
        br.com.biosecure.dto.response.CustomerResponseDto,
        br.com.biosecure.dto.response.CustomerSummaryDto
{}
