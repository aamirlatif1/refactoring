package com.aamir.model;

import java.util.List;

public record Invoice(String customer, List<Performance> performances) {
}
