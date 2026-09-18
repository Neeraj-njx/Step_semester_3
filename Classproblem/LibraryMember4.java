class LibraryMember4 {
    protected int booksBorrowed;

    public LibraryMember(String id, int borrowLimit) {
    }

    public void displayInfo() {
        System.out.print("General | Books: " + booksBorrowed + " | ");
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public static String batchPrint(LibraryMember[] members) {
        StringBuilder result = new StringBuilder();

        for (LibraryMember member : members) {
            member.displayInfo();

            if (member instanceof StudentMember) {
                StudentMember student = (StudentMember) member;
                result.append("Student | Course: ")
                      .append(student.getCourse())
                      .append(" | Books: ")
                      .append(student.getBooksBorrowed())
                      .append(" [Course via downcast: ")
                      .append(student.getCourse())
                      .append("] | ");
            } else {
                result.append("General | Books: ")
                      .append(member.getBooksBorrowed())
                      .append(" | ");
            }
        }

        return result.toString();
    }
}

class StudentMember extends LibraryMember {
    private String course;

    public StudentMember(String id, int borrowLimit, String course) {
        super(id, borrowLimit);
        this.course = course;
    }

    @Override
    public void displayInfo() {
        System.out.print("Student | ");
    }

    public String getCourse() {
        return course;
    }
}
