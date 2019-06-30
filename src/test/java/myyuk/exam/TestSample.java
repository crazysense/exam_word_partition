package myyuk.exam;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestSample {

    @Test
    public void test() throws IOException {
        Long[] expected = new Long[]{
                30851L, 24332L, 38933L, 22778L, 16815L, 15870L, 14466L, 18662L, 15220L, 4158L, 6167L, 14093L, 25194L,
                16174L, 15061L, 40956L, 3218L, 21286L, 50571L, 25223L, 23791L, 6816L, 11672L, 609L, 1740L, 1822L
        };

        Map<Character, Long> map = new HashMap<>();
        Pattern p = Pattern.compile("^[a-zA-Z].*$");

        InputStream is = getClass().getClassLoader().getResourceAsStream("sample/words.txt");
        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (p.matcher(line).matches()) {
                    char c = Character.toLowerCase(line.charAt(0));
                    map.put(c, map.getOrDefault(c, 0L) + 1);
                }
            }
        }

        char c = 'a';
        for (Long anExpected : expected) {
            assertEquals(anExpected, map.get(c++));
        }
    }
}
