package com.example.todospringptoject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoProjectionImpl implements TodoProjection {

    private String title;
    private Boolean completed;

}
