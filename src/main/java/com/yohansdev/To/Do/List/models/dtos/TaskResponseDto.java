package com.yohansdev.To.Do.List.models.dtos;

import java.util.Date;

public record TaskResponseDto(String title, String description, Date deadline) {
}
