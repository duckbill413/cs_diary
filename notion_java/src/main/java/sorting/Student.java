package sorting;

import java.util.Comparator;

class Student implements Comparator<Student> {
    String name;
    int age;
    int classroom;

    public Student(String name, int age, int classroom) {
        this.name = name;
        this.age = age;
        this.classroom = classroom;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getClassroom() {
        return classroom;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", classroom=" + classroom +
                '}' + '\n';
    }

    @Override
    public int compare(Student o1, Student o2) {
        if (o1.classroom < o2.classroom)
            return 1;
        else if (o1.classroom == o2.classroom) {
            if (o1.age > o2.age)
                return 1;
            else if (o1.age == o2.age) {
                if (o1.name.compareTo(o2.name) == -1)
                    return 1;
                else return -1;
            } else return -1;
        } else return -1;
    }
}
