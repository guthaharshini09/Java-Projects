
import java.util.*;

public class AdaptiveQuizUsingExamEngine {

    static class Question {
        String text;
        String[] options;
        int answer;
        int difficulty;

        Question(String text, String[] options, int answer, int difficulty) {
            this.text = text;
            this.options = options;
            this.answer = answer;
            this.difficulty = difficulty;
        }
    }

    static Scanner sc = new Scanner(System.in);

    static List<Question> questions = Arrays.asList(
        new Question("Which keyword creates a class in Java?",
            new String[]{"class", "define", "struct", "new"}, 0, 1),

        new Question("Which type stores true or false?",
            new String[]{"int", "boolean", "char", "float"}, 1, 1),

        new Question("Which collection is a resizable array?",
            new String[]{"HashMap", "ArrayList", "TreeSet", "String"}, 1, 2),

        new Question("Which block handles exceptions?",
            new String[]{"if", "for", "catch", "switch"}, 2, 2),

        new Question("Which Stream operation filters elements?",
            new String[]{"map", "filter", "reduce", "count"}, 1, 3),

        new Question("What does encapsulation do?",
            new String[]{
                "Combines data and methods with controlled access",
                "Deletes all objects",
                "Repeats a loop",
                "Sorts an array"
            }, 0, 3)
    );

    static int findQuestion(int level, Set<Integer> asked) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).difficulty == level && !asked.contains(i)) {
                return i;
            }
        }

        // Find another available question if the current level is exhausted
        for (int i = 0; i < questions.size(); i++) {
            if (!asked.contains(i)) {
                return i;
            }
        }

        return -1;
    }

    static String difficultyName(int level) {
        if (level == 1)
            return "Easy";
        else if (level == 2)
            return "Medium";
        else
            return "Hard";
    }

    static int getAnswer() {
        while (true) {
            System.out.print("Enter your answer (A-D): ");
            String input = sc.nextLine().trim().toUpperCase();

            if (input.length() == 1 &&
                input.charAt(0) >= 'A' &&
                input.charAt(0) <= 'D') {
                return input.charAt(0) - 'A';
            }

            System.out.println("Invalid input! Please enter A, B, C, or D.");
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println(" ADAPTIVE QUIZ USING EXAM ENGINE");
        System.out.println("==================================");

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter roll number: ");
        String roll = sc.nextLine();

        int level = 2;
        int score = 0;
        int total = Math.min(5, questions.size());
        int attempted = 0;

        Set<Integer> asked = new HashSet<>();

        for (int i = 1; i <= total; i++) {
            int index = findQuestion(level, asked);

            if (index == -1) {
                System.out.println("No more questions available.");
                break;
            }

            asked.add(index);
            Question q = questions.get(index);
            attempted++;

            System.out.println("\nQuestion " + i + " of " + total);
            System.out.println("Difficulty: " + difficultyName(q.difficulty));
            System.out.println(q.text);

            for (int j = 0; j < q.options.length; j++) {
                System.out.println((char) ('A' + j) + ". " + q.options[j]);
            }

            int choice = getAnswer();

            if (choice == q.answer) {
                System.out.println("Correct answer!");
                score++;
                level = Math.min(3, level + 1);
            } else {
                System.out.println("Incorrect. Correct answer: "
                    + (char) ('A' + q.answer));
                level = Math.max(1, level - 1);
            }

            System.out.println("Next target difficulty: "
                + difficultyName(level));
        }

        System.out.println("\n========== FINAL RESULT ==========");

        System.out.println("Student: " + name);
        System.out.println("Roll Number: " + roll);
        System.out.println("Score: " + score + "/" + attempted);

        double percentage = attempted > 0
            ? score * 100.0 / attempted
            : 0.0;

        System.out.printf("Percentage: %.2f%%%n", percentage);

        if (percentage >= 80)
            System.out.println("Performance: Excellent");
        else if (percentage >= 50)
            System.out.println("Performance: Good");
        else
            System.out.println("Performance: Needs Improvement");

        sc.close();
    }
}