package com.yohansdev.To.Do.List.models.dtos;

import jakarta.validation.constraints.*;

import java.util.Date;

public record CreateTaskDto(
        @NotBlank(message = "O título é obrigatório.")
        @Size(max = 255, message = "O tamanho máximo é de 255 caracteres.")
        String title,
        @NotBlank(message = "A descrição é obrigatória.")
        @Size(max = 255, message = "O tamanho máximo é de 255 caracteres.")
        String description,
        @NotNull(message = "O prazo de entrega é obrigatório.")
        @FutureOrPresent(message = "O prazo não pode ser anterior a data de hoje.")
        Date deadline) { }
