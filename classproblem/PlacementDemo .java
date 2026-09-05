class PlacementRecord {
    String studentName;
    String company;
    double packageLpa;

    PlacementRecord(String studentName, String company, double packageLpa) {
        this.studentName = studentName;
        this.company = company;
        this.packageLpa = packageLpa;
    }

    void printRecord() {
        System.out.println(studentName + " -> " + company + " @ " + packageLpa + " LPA");
    }
}

public class PlacementDemo {
    public static void main(String[] args) {

        PlacementRecord[] records = {
            new PlacementRecord("Arun", "Wipro", 5.0),
            new PlacementRecord("Priya", "Accenture", 6.5),
            new PlacementRecord("Vijay", "Cognizant", 4.8)
        };

        for (PlacementRecord record : records) {
            record.printRecord();
        }
    }
}
