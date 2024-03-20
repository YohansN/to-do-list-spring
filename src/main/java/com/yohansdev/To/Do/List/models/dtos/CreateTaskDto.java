package com.yohansdev.To.Do.List.models.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CreateTaskDto(
        @NotBlank(message = "O título é obrigatório.")
        String title,
        @NotBlank(message = "A descrição é obrigatória.")
        String description,
        @NotNull(message = "O prazo de entrega é obrigatório.")
        @FutureOrPresent(message = "O prazo não pode ser anterior a data de hoje.")
        Date deadline) { }
