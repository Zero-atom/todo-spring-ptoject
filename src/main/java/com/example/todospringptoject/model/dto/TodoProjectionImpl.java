package com.example.todospringptoject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TodoProjectionImpl implements TodoProjection {
    private final String title;
    private final Boolean completed;
}
