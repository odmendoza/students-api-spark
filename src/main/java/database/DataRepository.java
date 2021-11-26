package database;

import models.Student;

import java.util.ArrayList;
import java.util.Optional;

public class DataRepository {

    private ArrayList<Student> students;

    public DataRepository(ArrayList<Student> students) {
        this.students = students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Student> getStudents(){
        return this.students;
    }

    public Optional<Student>searchByPos(int pos){
        return this.students.stream().filter(student -> student.getPos() == pos).findFirst();
    }

    public ArrayList<Student>searchByName(String name){
        ArrayList searchedByName = new ArrayList();
        for(Student student: this.getStudents()){
            if (student.getName().contains(name)) {
                searchedByName.add(student);
            }
        }
        return searchedByName;
    }

    public ArrayList<Student>searchByLastname(String lastname){
        ArrayList searchedByLastname = new ArrayList();
        for(Student student: this.getStudents()){
            if (student.getLastname().contains(lastname)) {
                searchedByLastname.add(student);
            }
        }
        return searchedByLastname;
    }

    public ArrayList<Student>searchByAge(int age){
        ArrayList searchedByAge = new ArrayList();
        for(Student student: this.getStudents()){
            if (student.getAge() == age) {
                searchedByAge.add(student);
            }
        }
        return searchedByAge;
    }

    public boolean addStudent(Student newStudent) {
        ArrayList<Student> newStudentsList = this.getStudents();
        try {
            newStudentsList.add(newStudent);
            this.setStudents(newStudentsList);
        } catch (Exception exception) {
            return false;
        }

        return true;
    }

    public boolean updateStudent(int studentPos, Student student) {
        var updatedStudent = this.searchByPos(studentPos);
        if (updatedStudent.isPresent()){
            ArrayList<Student> newStudentsList = this.getStudents();
            try {
                int i = newStudentsList.indexOf(updatedStudent.get());
                newStudentsList.set(i, student);
                this.setStudents(newStudentsList);
            } catch (Exception exception) {
                return false;
            }
        } else {
            return false;
        }
        return true;

    }

    public boolean deleteByPos(int pos) {
        var deletedStudent = this.searchByPos(pos);
        if (deletedStudent.isPresent()){
            ArrayList<Student> newStudentsList = this.getStudents();
            try {
                newStudentsList.remove(deletedStudent.get());
                this.setStudents(newStudentsList);
            } catch (Exception exception) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
