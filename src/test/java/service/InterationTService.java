package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaRepository;
import repository.StudentRepository;
import repository.TemaRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.io.IOException;

public class InterationTService {

    private static final Validator<Student> studentValidator = new StudentValidator();
    private static final Validator<Tema> temaValidator = new TemaValidator();
    private static final Validator<Nota> notaValidator = new NotaValidator();

    StudentRepository studentRepository;
    TemaRepository temaRepository;
    NotaRepository notaRepository;

    private Service service;

    @BeforeEach
    void init() {
        this.studentRepository = new StudentRepository(studentValidator);
        this.temaRepository = new TemaRepository(temaValidator);
        this.notaRepository = new NotaRepository(notaValidator);
        this.service = new Service(studentRepository, temaRepository, notaRepository);

        this.service = new Service(studentRepository, temaRepository, notaRepository);
    }

    @Test
    public void testSaveStudent() {
        String id = "5";
        String name = "Gabi";
        int group = 111;

        int actual = service.saveStudent(id, name, group);

        Assertions.assertEquals(0, actual);
    }

    @Test
    public void testSaveTema() {
        String id = "1";
        String description = "description";
        int deadline = 1;
        int startline = 1;

        int result = service.saveTema(id, description, deadline, startline);

        Assertions.assertEquals(0, result);
    }

    @Test
    public void testSaveNota(){
        String idStudent = "1";
        String idAssignment = "123";
        int valGrade = 10;
        int predata = 7;
        String feedback = "Ok";

        int actual = service.saveNota(idStudent, idAssignment, valGrade, predata, feedback);

        Assertions.assertEquals(-1, actual);
    }

    @Test
    public void testIntegration(){
        String idStudent = "5";
        String name = "Andrei";
        int group = 111;

        String idAssignment = "1";
        String description = "some desc";
        int deadline = 1;
        int startline = 1;

        int valGrade = 10;
        int predata = 7;
        String feedback = "Ok";

        int resStudent = service.saveStudent(idStudent, name, group);
        int resAssignemnt = service.saveTema(idAssignment, description, deadline, startline);
        int resGrade = service.saveNota(idStudent, idAssignment, valGrade, predata, feedback);

        Assertions.assertEquals(0, resStudent);
        Assertions.assertEquals(0, resAssignemnt);
        Assertions.assertEquals(0, resGrade);
    }

}
