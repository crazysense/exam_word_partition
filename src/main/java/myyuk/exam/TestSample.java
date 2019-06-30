package myyuk.exam;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TestSample {
    public static void main(String[] args) throws Exception {
        Map<Character, Integer> map = new HashMap<>();
        Pattern p = Pattern.compile("^[a-zA-Z].*$");

        BufferedReader br = Files.newBufferedReader(Paths.get("D:\\development\\projects\\word-partition\\src\\main\\resources\\sample\\words.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            if (p.matcher(line).matches()) {
                char c = Character.toLowerCase(line.charAt(0));
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }

        System.out.println(map);
    }
}
