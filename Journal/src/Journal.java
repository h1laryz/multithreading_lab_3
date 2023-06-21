import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Journal
{
    private final HashMap<Student, HashMap<Integer, LinkedBlockingQueue<Integer>>> marks = new HashMap<Student, HashMap<Integer, LinkedBlockingQueue<Integer>>>();
    private List<Group> groupList = new ArrayList<>();

    public Journal(List<Group> groups, int weeks)
    {
        groupList = groups;

        for ( Group g : groupList )
        {
            for ( Student s : g.getStudentList() )
            {
                marks.put(s, new HashMap<Integer, LinkedBlockingQueue<Integer>>());
                for ( int i = 0; i < weeks; ++i )
                {
                    marks.get(s).put(i, new LinkedBlockingQueue<>());
                }
            }
        }
    }
    public void addGrade(Student s, int week, int grade)
    {
        if ( grade > 100 || grade < 0 )
        {
            throw new RuntimeException("Grade has to be between 0 and 100");
        }

        marks.get(s).get(week).add(grade);
    }

    public void print()
    {
        for ( Student s : marks.keySet() )
        {
            System.out.print("Student: " + s.getName());
            HashMap<Integer, LinkedBlockingQueue<Integer>> weekMarksMap = marks.get(s);
            for ( int week : weekMarksMap.keySet() )
            {
                System.out.print("\n\tWeek: " + week + "\n\tMarks: ");
                var marks = weekMarksMap.get(week);
                for ( int mark : marks )
                {
                    System.out.print(mark + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
