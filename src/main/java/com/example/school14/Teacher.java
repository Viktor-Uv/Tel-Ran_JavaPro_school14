package com.example.school14;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher {
    private Long id;
    private String name;
    protected boolean working = true;
}
