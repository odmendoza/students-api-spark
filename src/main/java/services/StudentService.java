package services;

import static spark.Spark.*;

import com.google.gson.Gson;
import database.DataRepository;
import models.Student;

import java.util.ArrayList;

public class StudentService {

    public static void main(String[] args) {

        /* Create initial objects */

        ArrayList<Student> students = new ArrayList<Student>();
        students.add(new Student(0, "Danilo", "Mendoza", 22));
        students.add(new Student(1, "Luis", "Erazo", 22));

        DataRepository data = new DataRepository(students);

        get("/students", (req, res) -> {
            Gson gson = new Gson();
            res.type("application/json");
            var stdnts = data.getStudents();
            if (!stdnts.isEmpty()) {
                res.status(200);
            } else {
                halt(404);
            }

            return gson.toJson(stdnts);
        });

        get("/student/:pos", (req, res) -> {
            var studentPos = Integer.valueOf(req.params(":pos"));
            Gson gson = new Gson();
            res.type("application/json");
            var student = data.searchByPos(studentPos);
            if (student.isPresent()) {
                res.status(200);
            } else {
                halt(404);
            }

            return gson.toJson(student.get());
        });

        get("/students/filter", (req, res) -> {
            var name = req.queryParams("name");
            var lastname = req.queryParams("lastname");
            var age = req.queryParams("age");
            Gson gson = new Gson();

            res.type("application/json");
            ArrayList<Student> stdnts = new ArrayList<>();
            if (name != null) {
                stdnts = data.searchByName(name);
            }
            if (lastname != null) {
                stdnts = data.searchByLastname(lastname);
            }
            if (age != null) {
                stdnts = data.searchByAge(Integer.parseInt(age));
            }
            if (!stdnts.isEmpty() && name != null || lastname != null || age != null) {
                res.status(200);
            } else {
                halt(404);
            }
            return gson.toJson(stdnts);
        });

        post("/student", "application/json", (req, res) -> {
            res.type("application/json");
            Student newStudent = new Gson().fromJson(req.body(), Student.class);
            var studentAdded = data.addStudent(newStudent);
            if (studentAdded) {
                res.status(201);
            } else {
                halt(404);
            }

            return new Gson().toJson(newStudent);
        });

        put("/student/:pos", "application/json", (req, res) -> {
            var studentPos = Integer.valueOf(req.params(":pos"));
            Student studentToUpdate = new Gson().fromJson(req.body(), Student.class);
            var studentUpdated = data.updateStudent(studentPos, studentToUpdate);
            res.type("application/json");
            if (studentUpdated) {
                res.status(201);
            } else {
                halt(404);
            }

            return new Gson().toJson(studentToUpdate);
        });

        delete("/student/:pos", (req, res) -> {
            var studentPos = Integer.valueOf(req.params(":pos"));
            Gson gson = new Gson();
            res.type("application/json");
            var studentDeleted = data.deleteByPos(studentPos);
            if (studentDeleted) {
                res.status(204);
                // res.status(201);
            } else {
                halt(404);
            }

            var message = "Deletion successful";

            return gson.toJson(message);
        });
    }
}
