package functionProgramming;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FunctionProgrammingTask {
    public static void main(String[] args) {
        String str = "yourapp the quick over fox brown fox fox jumps over the lazy dog cat privet poka ";
        List<String> arrayList = Arrays.asList(str.split(" "));

        System.out.println("Output:");
        System.out.println("В тексте " + arrayList.size() + " слов");

        System.out.println("TOP10:");

        arrayList.stream()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .sorted((e1, e2) ->
                {int res = 0; if (e1.getValue() == e2.getValue()) {
                    res = e1.getKey().compareTo(e2.getKey());
                };
                    return res;})
                .limit(10)
                .forEach(e -> System.out.println(e.getValue() + " - " + e.getKey()));
    }
}
