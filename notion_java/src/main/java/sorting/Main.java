package sorting;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 오름 차순 Comparator 정렬
        List<Integer> list = Arrays.asList(3, 10, 5, 7, 4, 2, 6, 9, 1, 7, 8);
        Collections.sort(list, (a, b) -> {
            if (a > b)
                return 1;
            else if (a == b)
                return 0;
            else return -1;
        });
        System.out.println(list);

        List<Student> students = new ArrayList<>();
        students.add(new Student("김선아", 15, 1));
        students.add(new Student("나희정", 15, 2));
        students.add(new Student("김윤종", 15, 3));
        students.add(new Student("김현철", 15, 1));
        students.add(new Student("강나영", 15, 2));
        students.add(new Student("송근석", 16, 3));
        students.add(new Student("이수한", 16, 1));
        students.add(new Student("정지환", 16, 2));
        students.add(new Student("나영", 17, 3));
        students.add(new Student("신하나", 17, 1));

        students.sort(Comparator.comparing(Student::getClassroom)
                .thenComparing(Student::getAge, Comparator.reverseOrder())
                .thenComparing(Student::getName));

        System.out.println(students); // classroom:asc, age: desc, name: asc

        Collections.sort(students, new Student());
        System.out.println(students); // classroom:desc, age: asc, name: desc
    }
}
