//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Smart Interview Preparation Portal (SIPP)
// A Java console-based project demonstrating core DSA concepts

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static QuizService quizService = new QuizService();

    public static void main(String[] args) {
        System.out.println("Welcome to SIPP - Smart Interview Preparation Portal");
        seedData();

        while (true) {
            System.out.println("\n1. Start Quiz by Topic\n2. Search Questions\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    quizService.startQuiz();
                    break;
                case 2:
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.nextLine();
                    quizService.searchQuestions(keyword);
                    break;
                case 3:
                    System.out.println("Thank you for using SIPP!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void seedData() {
        quizService.addTopic("DSA", "Arrays");
        quizService.addTopic("DSA", "Strings");

        quizService.addQuestion("Arrays", new Question("Find max subarray sum", "Kadane's Algorithm", "Hard"));
        quizService.addQuestion("Arrays", new Question("Merge two sorted arrays", "Use two-pointer technique", "Medium"));
        quizService.addQuestion("Strings", new Question("Check palindrome", "Two pointers", "Easy"));
    }
}

class Question {
    String text;
    String answer;
    String difficulty;

    public Question(String text, String answer, String difficulty) {
        this.text = text;
        this.answer = answer;
        this.difficulty = difficulty;
    }
}

class QuizService {
    Map<String, List<Question>> topicMap = new HashMap<>();
    Trie trie = new Trie();
    Scanner scanner = new Scanner(System.in);

    public void addTopic(String parent, String topic) {
        topicMap.put(topic, new ArrayList<>());
    }

    public void addQuestion(String topic, Question q) {
        topicMap.get(topic).add(q);
        trie.insert(q.text);
    }

    public void startQuiz() {
        System.out.println("Available Topics:");
        for (String topic : topicMap.keySet()) {
            System.out.println("- " + topic);
        }
        System.out.print("Enter topic: ");
        String topic = scanner.nextLine();
        if (!topicMap.containsKey(topic)) {
            System.out.println("Topic not found.");
            return;
        }
        List<Question> questions = topicMap.get(topic);
        if (questions.isEmpty()) {
            System.out.println("No questions available in this topic.");
            return;
        }
        for (Question q : questions) {
            System.out.println("\nQuestion: " + q.text);
            System.out.print("Your answer: ");
            String ans = scanner.nextLine();
            System.out.println("Correct Answer: " + q.answer);
        }
    }

    public void searchQuestions(String keyword) {
        System.out.println("Results for '" + keyword + "':");
        List<String> matches = trie.startsWith(keyword);
        for (String match : matches) {
            System.out.println("- " + match);
        }
    }
}

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isWord = false;
}

class Trie {
    TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.isWord = true;
    }

    public List<String> startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return new ArrayList<>();
            node = node.children.get(c);
        }
        List<String> result = new ArrayList<>();
        dfs(node, new StringBuilder(prefix), result);
        return result;
    }

    private void dfs(TrieNode node, StringBuilder path, List<String> result) {
        if (node.isWord) result.add(path.toString());
        for (char c : node.children.keySet()) {
            path.append(c);
            dfs(node.children.get(c), path, result);
            path.setLength(path.length() - 1);
        }
    }
}