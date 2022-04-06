package service;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Nota;
import domain.Student;
import domain.Tema;
import repository.NotaRepository;
import repository.StudentRepository;
import repository.TemaRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;
import validation.Validator;

public class WBTService {
    private static final Validator<Student> studentValidator = new StudentValidator();
    private static final Validator<Tema> temaValidator = new TemaValidator();
    private static final Validator<Nota> notaValidator = new NotaValidator();

    StudentRepository studentRepository;
    TemaRepository temaRepository;
    NotaRepository notaRepository;

    private Service service;

    @BeforeEach
    void init() throws IOException {
        this.studentRepository = new StudentRepository(studentValidator);
        this.temaRepository = new TemaRepository(temaValidator);
        this.notaRepository = new NotaRepository(notaValidator);
        this.service = new Service(studentRepository, temaRepository, notaRepository);

        this.service = new Service(studentRepository, temaRepository, notaRepository);
    }

    @Test
    void addTema_emptyId_ValidationException() {
        Assertions.assertEquals(1, service.saveTema("","desc", 1, 1));
    }

    @Test
    void addTema_nullId_ValidationException() {
        Assertions.assertEquals(1, service.saveTema(null, "desc", 1, 1));
    }

    @Test
    void addTema_emptyDescription_ValidationException() {
        Assertions.assertEquals(1, service.saveTema("id", "", 1, 1));
    }

    @Test
    void addTema_nullDescription_ValidationException() {
        Assertions.assertEquals(1, service.saveTema("id", null, 1, 1));
    }

    @Test
    void addTema_deadlineLessThan1_ValidationException() {
        Assertions.assertEquals(1, service.saveTema("id", "desc", 0, 1));
    }

    @Test
    void addTema_deadlineGreaterThan14_ValidationException() {
        Assertions.assertEquals(1, service.saveTema("id", "desc", 15, 1));
    }

    @Test
    void addTema_primireLessThan1_ValidationException() {
        Assertions.assertEquals(1, service.saveTema("id", "desc", 1, 0));
    }

    @Test
    void addTema_primireGreaterThan14_ValidationException() {
        Assertions.assertEquals(1, service.saveTema("id", "desc", 1, 15));
    }

    // @Test
    // void addTema_existingAssignment_ExistingAssignment() {
    //     Tema assignment = new Tema("1", "desc", 1, 1);
    //     Assertions.assertEquals(assignment,service.saveTema("1", "desc", 1, 1));
    // }

    @Test
    void addTema_correctAssignment_Null() {
        Assertions.assertEquals(0, service.saveTema("1", "desc", 12, 2));
    }
}
