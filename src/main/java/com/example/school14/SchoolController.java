package com.example.school14;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// для работы с thymeleaf
// методы этого контроллера возвращают не объекты,
// а названия темплейтов
/*
    Thymeleaf Documentation: https://www.thymeleaf.org/documentation.html

    Spring and Thymeleaf 3: Expressions
    https://www.baeldung.com/spring-thymeleaf-3-expressions

    Thymeleaf utility methods for Strings
    https://frontbackend.com/thymeleaf/thymeleaf-utility-methods-for-strings
 */
@Controller
public class SchoolController {

    private List<Student> students = new ArrayList<>(
            Arrays.asList(
                    new Student(1L, "Max", "Petrov", 19),
                    new Student(2L, "Dima", "Alexeev", 18),
                    new Student(3L, "Leon", "Sergeev", 21),
                    new Student(4L, "Masha", "Samoilova", 15),
                    new Student(5L, "Dina", "Ivanova", 19)
            )
    );

    private Teacher teacher = new Teacher(10L, "Maria Ivanovna", true);

    // GET http://localhost:8080/students
    @GetMapping("/students")
    public String getAll(Model model) {
        // model нужна чтобы биндить внутрь шаблона thymeleaf любые объекты из метода
        String time = "" + System.currentTimeMillis();
        // биндинг объекта в шаблон
        model.addAttribute("time", time);
        model.addAttribute("students", students);
        return "list";
    }

    @GetMapping("/student/delete/{id}")
    public String deleteStudentById(@PathVariable Long id) {
        // удаляем студента из списка
        students.removeIf(student -> student.getId().equals(id));
        // возвращаем броузер в урл который генерирует обновлённый список
        return "redirect:/students";
    }

    @GetMapping("/student/update/{id}")
    public String updateStudent(
            @PathVariable Long id,
            Model model
    ) {
        Student student = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
        model.addAttribute("student", student);
        return "change-student";
    }

    @PostMapping("/student/{id}")
    public String updateStudentById(
            @PathVariable Long id,
            // @Valid студент будет проверен правилами валидации
            // @ModelAttribute студент будет автоматически забинден в шаблон
            @Valid @ModelAttribute Student student, // результаты проверки на соответствие правилам валидации
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "change-student";
        }

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.set(i, student);
                break;
            }
        }
        return "redirect:/students";
    }
}
