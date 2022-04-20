package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TemaRepository;
import repository.NotaRepository;
import repository.StudentRepository;
import validation.TemaValidator;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.Validator;

public class TopDownService {
    private static final Validator<Student> studentValidator = new StudentValidator();
    private static final Validator<Tema> TemaValidator = new TemaValidator();
    private static final Validator<Nota> NotaValidator = new NotaValidator();

    StudentRepository repo1;
    TemaRepository repo2;
    NotaRepository repo3;

    private Service service;

    @BeforeEach
    public void init(){
        repo1 = new StudentRepository(studentValidator);
        repo2 = new TemaRepository(TemaValidator);
        repo3 = new NotaRepository(NotaValidator);

        service = new Service(repo1, repo2, repo3);
    }

    @Test
    public void testSaveStudent() {
        // saveStudent
        //Arrange
        String id = "5";
        String name = "Andrei";
        int group = 111;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(0, actual);
    }

    @Test
    public void testSaveTema() {
        // saveStudent + saveTema
        //Arrange
        String idStudent = "5";
        String name = "Andrei";
        int group = 111;

        String idTema = "1";
        String description = "some desc";
        int deadline = 1;
        int startline = 1;

        //Act
        int resStudent = service.saveStudent(idStudent, name, group);
        int resAssignemnt = service.saveTema(idTema, description, deadline, startline);

        // Assert
        Assertions.assertEquals(0, resStudent);
        Assertions.assertEquals(0, resAssignemnt);
    }

    @Test
    public void testSaveNota(){
        // saveStudent + saveTema + saveNota
        //Arrange
        String idStudent = "5";
        String name = "Andrei";
        int group = 111;

        String idTema = "1";
        String description = "some desc";
        int deadline = 1;
        int startline = 1;

        int valNota = 10;
        int predata = 7;
        String feedback = "Ok";

        //Act
        int resStudent = service.saveStudent(idStudent, name, group);
        int resAssignemnt = service.saveTema(idTema, description, deadline, startline);
        int resNota = service.saveNota(idStudent, idTema, valNota, predata, feedback);

        //Assert
        Assertions.assertEquals(0, resStudent);
        Assertions.assertEquals(0, resAssignemnt);
        Assertions.assertEquals(0, resNota);
    }

}
