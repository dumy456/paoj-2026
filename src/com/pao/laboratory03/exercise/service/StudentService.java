package com.pao.laboratory03.exercise.service;
import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
public class StudentService {
    List<Student> students;
    private StudentService() {
        this.students = new ArrayList<>();
    }
    private static class Holder {
        private static final StudentService INSTANCE = new StudentService();
    }
    public static StudentService getInstance() {
        return StudentService.Holder.INSTANCE;
    }
    public void addStudent(String name,int age){
        Student student=new Student(name,age);
        if(students.contains(student)){
            throw new RuntimeException();
        }else{
            students.add(student);
        }
    }
    public Student findByName(String name){

        for(Student s: students){
            if(s.getName().equals(name)){
                return s;

            }
        }
        throw new StudentNotFoundException("");
    }
    public void addGrade(String studentname,Subject subject, double grade){
        Student student=findByName(studentname);
        student.addGrade(subject,grade);
    }
    public void printAllStudents(){
        for(Student s:students){
            System.out.println(s);
            System.out.println(s.getGrades());
        }
    }
    public void printTopStudents(){
        students.sort((s1,s2)-> Double.compare(s2.getAverage(),s1.getAverage()));
        System.out.println(students);
    }
    public Map<Subject, Double> getAveragePerSubject(){
        Map<Subject, Double> avgSubject = new HashMap<>();
        for(Subject subject: Subject.values())
        {
            double sum = 0;
            int count = 0;
            for(Student st: students){
                Double grade = st.getGrades().get(subject);
                if(grade != null)
                {
                    sum += grade;
                    count++;
                }
                if(count > 0){
                    avgSubject.put(subject, sum/count);
                }
            }
        }
        return avgSubject;
    }
}
