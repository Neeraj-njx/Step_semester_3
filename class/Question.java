import java.util.*;

abstract class Question {
    private final int id;
    private final String text;
    private final int points;

    public Question(int id, String text, int points) {
        this.id = id;
        this.text = text;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {
    private final String correctOption;

    public MultipleChoiceQuestion(
            int id,
            String text,
            int points,
            String correctOption) {

        super(id, text, points);
        this.correctOption = correctOption;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctOption.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    public TrueFalseQuestion(
            int id,
            String text,
            int points,
            boolean correctAnswer) {

        super(id, text, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return Boolean.parseBoolean(answer) == correctAnswer;
    }
}

class ShortAnswerQuestion extends Question {
    private final String correctAnswer;

    public ShortAnswerQuestion(
            int id,
            String text,
            int points,
            String correctAnswer) {

        super(id, text, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }
}

class Examination {
    private final String name;
    private final List<Question> questions = new ArrayList<>();

    public Examination(String name) {
        this.name = name;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getName() {
        return name;
    }
}

class Student {
    private final int id;
    private final String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum AttemptStatus {
    IN_PROGRESS,
    SUBMITTED
}

class Attempt {
    private final Student student;
    private final Examination examination;

    private final Map<Question, String> answers =
            new HashMap<>();

    private AttemptStatus status = AttemptStatus.IN_PROGRESS;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    public void answer(Question question, String answer) {
        if (status == AttemptStatus.SUBMITTED) {
            throw new IllegalStateException(
                    "Cannot change answers for a submitted examination.");
        }

        answers.put(question, answer);
        System.out.println(
                "Answer recorded for Question "
                        + question.getId());
    }

    public void submit() {
        if (status == AttemptStatus.SUBMITTED) {
            throw new IllegalStateException(
                    "Examination already submitted.");
        }

        status = AttemptStatus.SUBMITTED;

        int total = 0;
        int score = 0;

        System.out.println(
                "\nExam " + examination.getName()
                        + " submitted by "
                        + student.getName());

        for (Question question : examination.getQuestions()) {
            total += question.getPoints();

            String answer = answers.get(question);

            boolean correct =
                    answer != null &&
                    question.evaluate(answer);

            if (correct) {
                score += question.getPoints();

                System.out.println(
                        "Question " + question.getId()
                                + ": Correct ("
                                + question.getPoints()
                                + " points)");
            } else {
                System.out.println(
                        "Question " + question.getId()
                                + ": Incorrect (0 points)");
            }
        }

        System.out.println(
                "Total score: " + score + "/" + total);
    }
}

class ExaminationSystem {
    private final Map<String, Attempt> attempts =
            new HashMap<>();

    public Attempt startExam(
            Student student,
            Examination examination) {

        String key = student.getName()
                + "-" + examination.getName();

        if (attempts.containsKey(key)) {
            throw new IllegalStateException(
                    "Student already has an attempt.");
        }

        Attempt attempt =
                new Attempt(student, examination);

        attempts.put(key, attempt);

        System.out.println(
                examination.getName()
                        + " started by "
                        + student.getName());

        return attempt;
    }
}

public class ExaminationDemo {
    public static void main(String[] args) {

        Student student =
                new Student(1, "Student 1");

        Examination exam =
                new Examination("Exam A");

        Question q1 =
                new MultipleChoiceQuestion(
                        1,
                        "Which is correct?",
                        5,
                        "C");

        Question q2 =
                new TrueFalseQuestion(
                        2,
                        "Java supports OOP.",
                        5,
                        false);

        exam.addQuestion(q1);
        exam.addQuestion(q2);

        ExaminationSystem system =
                new ExaminationSystem();

        Attempt attempt =
                system.startExam(student, exam);

        attempt.answer(q1, "C");
        attempt.answer(q2, "True");

        attempt.submit();

        try {
            attempt.answer(q1, "A");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
