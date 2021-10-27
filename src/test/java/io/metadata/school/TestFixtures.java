package io.metadata.school;

import io.metadata.school.model.entity.Course;
import io.metadata.school.model.entity.Student;
import io.metadata.school.model.entity.User;
import io.metadata.school.model.enums.UserRole;

import java.util.List;

public class TestFixtures {
    private TestFixtures() {
    }

    public static User buildUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail(String.format("email_%s@test.com", id));
        user.setFirstName(String.format("firstName_%s", id));
        user.setSecondName(String.format("secondName_%s", id));
        user.setPassword("password");
        user.setRole(UserRole.ROLE_STUDENT);

        return user;
    }

    public static User buildUser(Long id, UserRole userRole) {
        User user = buildUser(id);
        user.setRole(userRole);

        return user;
    }

    public static Course buildCourse(Long id) {
        Course course = new Course();
        course.setId(id);
        course.setName("name_" + id);
        return course;
    }

    public static Course buildCourse(Long id, Student student) {
        Course course = new Course();
        course.setId(id);
        course.setName("name_" + id);

        course.setStudents(List.of(student));

        return course;
    }

    public static Student buildStudent(Long id, User user) {
        Student student = new Student();
        student.setId(id);
        student.setUser(user);

        return student;
    }
}
