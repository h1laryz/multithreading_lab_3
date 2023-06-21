import java.util.List;
import java.util.Random;

public class TeacherThread extends Thread
{
    private final String name;
    private final Journal journal;
    private final List<Group> groupList;
    private final int weeks;

    public TeacherThread(String name, Journal journal, List<Group> groupList, int weeks) {
        this.name = name;
        this.journal = journal;
        this.groupList = groupList;
        this.weeks = weeks;
    }

    @Override
    public void run()
    {
        Random random = new Random();
        for ( int i = 0; i < weeks; ++i )
        {
            for ( Group g : groupList )
            {
                for ( Student s : g.getStudentList() )
                {
                    var mark = random.nextInt(0, 101);
                    journal.addGrade(s, i, mark);
                    System.out.println(name + " added mark to " + g.getGroupName() + " " + s.getName() + " mark is: " + mark);
                    var sleepTime = random.nextInt(1000);
                    try
                    {
                        Thread.sleep(sleepTime);
                    }
                    catch ( InterruptedException ex )
                    {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
}
