package com.pao.laboratory03.exercise.model;
import com.pao.laboratory03.exercise.exception.InvalidGradeException;
import com.pao.laboratory03.exercise.exception.InvalidStudentException;

import java.util.*;
public class Student {
    private String name;
    private int age;
    private Map<Subject,Double> grades;
    public Student(String name,int age){
        this.name=name;
        this.age=age;
        grades=new HashMap<>();
        if (age<18 || age>60){
            throw new InvalidStudentException("");
        }
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Map<Subject, Double> getGrades() {
        return grades;
    }
    public void addGrade(Subject subject,double grade){
        if(grade<1 || grade>10){
            throw new InvalidGradeException("");
        }else{
            grades.put(subject,grade);
        }
    }
    public double getAverage(){
        double medie=0;
        if(grades.isEmpty()){
            return 0;
        }else {
            for (Map.Entry<Subject, Double> entry : grades.entrySet()) {
                medie += entry.getValue();
            }
            medie /= grades.size();
            return medie;
        }
    }
    @Override
    public String toString() {
        return "Student{name=" + name + ", age=" + age + " ,avg= "+getAverage()+"}";
    }
}
