package bd.course.work.controllers;


import bd.course.work.entities.Class;
import bd.course.work.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {

    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Class>> getAll() {
        return ResponseEntity.ok(classService.getAll());
    }
}
