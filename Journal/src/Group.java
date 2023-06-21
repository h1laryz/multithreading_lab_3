import java.util.ArrayList;
import java.util.List;

public class Group
{
    private String groupName;
    private final List<Student> studentList = new ArrayList<Student>();

    public Group(String groupName)
    {
        this.groupName = groupName;
    }
    public String getGroupName()
    {
        return groupName;
    }

    public List<Student> getStudentList()
    {
        return studentList;
    }

    public void addStudent(Student s)
    {
        studentList.add(s);
    }
}
