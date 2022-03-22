package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import repository.*;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServiceTest {
    private static final Validator<Student> studentValidator = new StudentValidator();
    private static final Validator<Tema> temaValidator = new TemaValidator();
    private static final Validator<Nota> notaValidator = new NotaValidator();

    StudentXMLRepository studentRepository;
    TemaXMLRepository temaRepository;
    NotaXMLRepository notaRepository;

    private Service service;

    @BeforeEach
    public void init(){
        this.studentRepository = new StudentXMLRepository(studentValidator, "src/main/resources/studentiTest.txt");
        this.temaRepository = new TemaXMLRepository(temaValidator, "src/main/resources/temeTest.txt");
        this.notaRepository = new NotaXMLRepository(notaValidator, "src/main/resources/notaTest.txt");
        this.service = new Service(studentRepository, temaRepository, notaRepository);
    }

    @Test
    public void testSaveStudent_TC1(){
        String id = "";
        String name = "Ewald";
        int group = 111;
        int expected  = 1;

        int actual = this.service.saveStudent(id, name, group);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudent_TC2(){
        String id = null;
        String name = "Ewald";
        int group = 111;
        int expected  = 1;

        int actual = this.service.saveStudent(id, name, group);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudent_TC3(){
        String id = "1";
        String name = "";
        int group = 111;
        int expected  = 1;

        int actual = this.service.saveStudent(id, name, group);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudent_TC4(){
        String id = "1";
        String name = null;
        int group = 111;
        int expected  = 1;

        int actual = this.service.saveStudent(id, name, group);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudent_TC5(){
        String id = "1";
        String name = "Ewald";
        int group = 109;
        int expected  = 1;

        int actual = this.service.saveStudent(id, name, group);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudent_TC6(){
        String id = "1";
        String name = "Ewald";
        int group = 939;
        int expected  = 1;

        int actual = this.service.saveStudent(id, name, group);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudent_TC7(){
        String id = "1";
        String name1 = "Ewald";
        String name2 = "Gabriel";
        int group1 = 111;
        int group2 = 931;
        int expected1 = 0;
        int expected2 = 0;

        int actual1 = this.service.saveStudent(id, name1, group1);
        int actual2 = this.service.saveStudent(id, name2, group2);

        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    public void testSaveStudent_TC8(){
        String id = "1";
        String name = "Ewald";
        int group = 111;
        int expected = 0;

        int actual = this.service.saveStudent(id, name, group);

        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void testSaveStudent_TC9(){
        String id = "1";
        String name = "Ewald";
        int group = 931;
        int expected = 0;

        int actual = this.service.saveStudent(id, name, group);

        Assertions.assertEquals(expected, actual);
    }




}
