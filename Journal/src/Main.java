import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var weeks = 4;
        List<Group> groupList = new ArrayList<>();
        Group group1 = new Group("G1");
        group1.addStudent(new Student("s1"));
        group1.addStudent(new Student("s2"));
        group1.addStudent(new Student("s3"));
        group1.addStudent(new Student("s4"));
        group1.addStudent(new Student("s5"));


        Group group2 = new Group("G2");
        group2.addStudent(new Student("s6"));
        group2.addStudent(new Student("s7"));
        group2.addStudent(new Student("s8"));
        group2.addStudent(new Student("s9"));
        group2.addStudent(new Student("s10"));


        Group group3 = new Group("G3");
        group3.addStudent(new Student("s11"));
        group3.addStudent(new Student("s12"));
        group3.addStudent(new Student("s13"));
        group3.addStudent(new Student("s14"));
        group3.addStudent(new Student("s15"));

        groupList.add(group1);
        groupList.add(group2);
        groupList.add(group3);

        Journal journal = new Journal(groupList, weeks);

        TeacherThread thread = new TeacherThread("t1", journal, groupList, weeks);
        TeacherThread thread2 = new TeacherThread("t2", journal, groupList, weeks);
        TeacherThread thread3 = new TeacherThread("t3", journal, groupList, weeks);
        TeacherThread thread4 = new TeacherThread("t4", journal, groupList, weeks);
        TeacherThread thread5 = new TeacherThread("t5", journal, groupList, weeks);

        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        try
        {
            thread.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
        }
        catch ( InterruptedException ignored )
        {

        }

        journal.print();
    }
}