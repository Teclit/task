package com.ecolepmn.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le nom du titre de la tâche ne doit pas être nul")
    @Size(min = 3, message = "Le nom du titre de la tâche doit être au moins 3 caractères")
    private String title;

    @NotNull(message = "Le nom de description de la tâche ne doit pas être nul")
    @Size(min = 3, message = "Tâches Description Le nom doit comporter au moins 3 caractères")
    private String description;

    private boolean completed;

}
