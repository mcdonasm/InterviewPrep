import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Chapter 16 of Cracking the Coding Interview
 * Moderate problems
 */
public class ModerateProblems {

    /**
     * Number Swapper: Write a function to swap two numbers in place (that is, w/o temporary vars).
     */
    public static int[] numberSwapper(int num1, int num2) {
        num1 = num1 - num2;
        num2 = num2 + num1;
        num1 = num2 - num1;

        int[] numbers = new int[2];
        numbers[0] = num1;
        numbers[1] = num2;
        return numbers;
    }

    @Test
    public void numberSwapperTest() {
        swapAndTest(6, 18);
        swapAndTest(-123, 7784);
        swapAndTest(0, -45);
        swapAndTest(1000, -1000);
    }

    private void swapAndTest(int num1, int num2) {
        int[] swapped = numberSwapper(num1, num2);
        Assertions.assertTrue(num1 == swapped[1]);
        Assertions.assertTrue(num2 == swapped[0]);
    }

    /**
     * Word Frequencies: Design a method to find the frequency of occurrences of any given word in a book.
     * What if we were running this algorithm multiple times?
     *
     * Basic Version Assumptions:
     * Will only catch the exact tense of the word given, and doesn't catch pluralized versions of words given in singular,
     * and vice-a-versa.
     *
     * Extended Version Assumptions:
     * Only the singular
     * When a word ends with `'s` (the possessive), it should still count (i.e. `Frank's` counts as an occurrence of `Frank`,
     * `James'` counts as an occurrence of `James`)
     *
     * When a word is pluralized, it should still count.
     */
    public static int basicWordFrequencies(Path filePath, String searchWord) {
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            return -1;
        }

        Map<String, Integer> wordCount = new HashMap<>();
        for(String line: lines) {
            String[] words = line.split("/s");
            for(String word: words) {
                Integer currentCount = wordCount.get(word);
                wordCount.put(word, currentCount != null ? currentCount++ : 1);
            }
        }

        return -1;
    }

    @Test
    public void wordFrequenciesTest() {
        Path starTrekPath = Paths.get("C:\\Users\\Steven\\IdeaProjects\\InterviewPrep\\resources\\wikipediaText\\starTrek.txt");
        Assertions.assertTrue(basicWordFrequencies(starTrekPath, "worf") == 16);
    }
}
