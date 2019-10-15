package exercises;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import classes.Student;
import interfaces.Criterion;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExercise {

  public static final List<Student> students = getStudents();

  public static List<Student> getStudents() {
    List<Student> studentList = new ArrayList<>();
    studentList.add(new Student(3.2f, "Kun", "Zhou"));
    studentList.add(new Student(3.9f, "San", "Li"));
    studentList.add(new Student(3.2f, "Si", "Zhao"));
    studentList.add(new Student(3.1f, "Ke", "Wang"));
    studentList.add(new Student(2.99f, "Kun", "Zhao"));
    studentList.add(new Student(3.4f, "Long", "Hen"));

    return studentList;
  }

  public static void main(String[] args) {

//    Stream<Student> studentStream2 = students.stream();
//
//    List<Student> sortedStudents = studentStream2.sorted(Comparator.comparing(Student::getGpa).reversed().thenComparing(Student::getLastName)
//        .thenComparing(Student::getFirstName)).collect(Collectors.toList());
//
//
//    Criterion<Student> criterion = (Student s) -> s.getGpa() > 3.2;
//
//    showStudents(filter(students, criterion.inverse()));
//
//    System.out.println("---------------");
//
//    showStudents(sortedStudents);
    Stream<Student> studentStream = students.stream();
    Map<String, Long> map = studentStream
                                  .collect(groupingBy(s -> s.getLastName(), counting()));
    System.out.println(map);

  }

  public static <E> List<E> filter(List<E> elements, Criterion<E> crit) {
    List<E> elementList = new ArrayList<>();
    for (E element:elements) {
      if (crit.test(element)) {
        elementList.add(element);
      }
    }
    return elementList;
  }


  public static void showStudents(List<Student> students) {
    for (Student student : students) {
      System.out.println(student);
    }
  }
}
