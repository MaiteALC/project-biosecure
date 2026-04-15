package br.com.biosecure.dto.response;

public sealed interface CustomerResponseDto permits
        CustomerFullResponseDto,
        CustomerSummaryResponseDto
{}
