package com.yohansdev.To.Do.List.models.dtos;

import jakarta.validation.constraints.*;

import java.util.Date;

public record UpdateTaskDto(@NotNull(message = "O ID não pode ser nulo.")
                            @Positive(message = "O ID deve ser positivo.") Long id,
                            @Size(max = 255, message = "O tamanho máximo é de 255 caracteres.")
                            String title,
                            @Size(max = 255, message = "O tamanho máximo é de 255 caracteres.")
                            String description,
                            @FutureOrPresent(message = "O prazo não pode ser anterior a data de hoje.")
                            Date deadline) { }
