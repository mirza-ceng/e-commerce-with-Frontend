/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.Exception;

import java.util.Date;

public class ErrorDetails {
    private final Date timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() { return timestamp; }
    public String getMessage() { return message; }
    public String getDetails() { return details; }
}