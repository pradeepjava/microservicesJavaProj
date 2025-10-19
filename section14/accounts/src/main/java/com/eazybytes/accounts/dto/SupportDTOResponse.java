package com.eazybytes.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
public class SupportDTOResponse {
    private String message;
    Map<String, String> contactDetails;
    List<String> onCallSupport;
}
