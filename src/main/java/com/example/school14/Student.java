package com.example.school14;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    private Long id;
    private String first;
    private String last;
    private Integer age;
}
