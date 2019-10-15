package exercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapExercise {

  public static void main(String[] args) {
    Map<String, String> hashMap = new HashMap<>();

    hashMap.computeIfAbsent("test", key -> new StringBuilder(key).reverse().toString());

    hashMap.compute("test", (key, value) -> key.toUpperCase());

    hashMap.merge("test", "case one", String::concat);

    System.out.println(hashMap);




    Map<String, Integer> shop1 = new HashMap<>();
    shop1.put("William", 1);
    shop1.put("Jim", 2);
    shop1.put("Fred", 3);

    Map<String, Integer> shop2 = new HashMap<>();
    shop2.put("William", 3);
    shop2.put("Jim", 4);
    shop2.put("Fred", 6);

    Map<String, Integer> shop3 = new HashMap<>();
    shop3.put("William", 14);
    shop3.put("Jim", 1);
    shop3.put("Fred", 4);

    List<Map<String, Integer>> stores = new ArrayList<>(Arrays.asList(shop1, shop2, shop3));

    Map<String, Integer> totals = new HashMap<>();

    for (Map<String, Integer> m : stores) {
      m.forEach((k, v) -> totals.merge(k, v, (v1, v2) -> v1 + v2));
    }

    System.out.println(totals);
  }

}
