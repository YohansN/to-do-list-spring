package com.yohansdev.To.Do.List.models.dtos;

import java.util.Date;

public record UpdateTaskDto(Long id, String title, String description, Date deadline) { }
