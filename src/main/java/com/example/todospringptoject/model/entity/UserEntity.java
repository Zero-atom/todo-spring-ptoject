package com.example.todospringptoject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table_generator")
    @TableGenerator(name = "table_generator", table = "sequence_table", pkColumnName = "seq_name", valueColumnName = "seq_value", pkColumnValue = "user_sequence")
    private Long id;
    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")//cascade если удалится user каскадно удалятся задачи
    private List<TodoEntity> todos;

    //alt + insert

}
