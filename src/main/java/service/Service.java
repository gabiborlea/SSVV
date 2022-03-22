package service;

import domain.*;
import repository.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Service {
    private CRUDRepository<String, Student> studentRepo;
    private CRUDRepository<String, Tema> temaRepo;
    private CRUDRepository<Pair<String, String>, Nota> notaRepo;

    public Service(CRUDRepository<String, Student> studentRepo, CRUDRepository<String, Tema> temaRepo, CRUDRepository<Pair<String, String>, Nota> notaRepo) {
        this.studentRepo = studentRepo;
        this.temaRepo = temaRepo;
        this.notaRepo = notaRepo;
    }

    public Iterable<Student> findAllStudents() { return studentRepo.findAll(); }

    public Iterable<Tema> findAllTeme() { return temaRepo.findAll(); }

    public Iterable<Nota> findAllNote() { return notaRepo.findAll(); }

    public int saveStudent(String id, String nume, int grupa) {
        Student student = new Student(id, nume, grupa);
        Student result = studentRepo.save(student);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveTema(String id, String descriere, int deadline, int startline) {
        Tema tema = new Tema(id, descriere, deadline, startline);
        Tema result = (Tema) temaRepo.save(tema);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveNota(String idStudent, String idTema, double valNota, int predata, String feedback) {
        if (studentRepo.findOne(idStudent) == null || temaRepo.findOne(idTema) == null) {
            return -1;
        }
        else {
            int deadline = temaRepo.findOne(idTema).getDeadline();

            if (predata - deadline > 2) {
                valNota =  1;
            } else {
                valNota =  valNota - 2.5 * (predata - deadline);
            }
            Nota nota = new Nota(new Pair(idStudent, idTema), valNota, predata, feedback);
            Nota result = notaRepo.save(nota);

            if (result == null) {
                return 1;
            }
            return 0;
        }
    }

    public int deleteStudent(String id) {
        Student result = (Student) studentRepo.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int deleteTema(String id) {
        Tema result = (Tema) temaRepo.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateStudent(String id, String numeNou, int grupaNoua) {
        Student studentNou = new Student(id, numeNou, grupaNoua);
        Student result = (Student) studentRepo.update(studentNou);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateTema(String id, String descriereNoua, int deadlineNou, int startlineNou) {
        Tema temaNoua = new Tema(id, descriereNoua, deadlineNou, startlineNou);
        Tema result = (Tema) temaRepo.update(temaNoua);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int extendDeadline(String id, int noWeeks) {
        Tema tema = (Tema) temaRepo.findOne(id);

        if (tema != null) {
            LocalDate date = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int currentWeek = date.get(weekFields.weekOfWeekBasedYear());

            if (currentWeek >= 39) {
                currentWeek = currentWeek - 39;
            } else {
                currentWeek = currentWeek + 12;
            }

            if (currentWeek <= tema.getDeadline()) {
                int deadlineNou = tema.getDeadline() + noWeeks;
                return updateTema(tema.getID(), tema.getDescriere(), deadlineNou, tema.getStartline());
            }
        }
        return 0;
    }

    public void createStudentFile(String idStudent, String idTema) {
        Nota nota = notaRepo.findOne(new Pair(idStudent, idTema));

        this.createFile(nota);
    }

    public void createFile(Nota gradeObj) {
        String               idStudent = gradeObj.getID().getObject1();
        Student student = studentRepo.findOne(idStudent);

        if(student == null){
            throw new IllegalStateException("Student not found in text file");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(student.getNume() + ".txt", false))) {
            notaRepo.findAll().forEach(nota -> {
                if (nota.getID().getObject1().equals(idStudent)) {
                    try {
                        bw.write("Tema: " + nota.getID().getObject2() + "\n");
                        bw.write("Nota: " + nota.getNota() + "\n");
                        bw.write("Predata in saptamana: " + nota.getSaptamanaPredare() + "\n");
                        bw.write("Deadline: " + temaRepo.findOne(nota.getID().getObject2()).getDeadline() + "\n");
                        bw.write("Feedback: " + nota.getFeedback() + "\n\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
