import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Transliteration of the cyrillic string into latin and the latin into cyrillic
 *
 * @autor _drukpakunle_
 */
public final class Transliterator {

    private static final Map<String, String> letters = new HashMap<>();

    private enum Direction {
        CYRILLIC_TO_LATIN,
        LATIN_TO_CYRILLIC
    }

    static {
        letters.put("a", "а");
        letters.put("b", "б");
        letters.put("v", "в");
        letters.put("g", "г");
        letters.put("d", "д");
        letters.put("e", "е");
        letters.put("yo", "ё");
        letters.put("zh", "ж");
        letters.put("z", "з");
        letters.put("i", "и");
        letters.put("j", "й");
        letters.put("k", "к");
        letters.put("l", "л");
        letters.put("m", "м");
        letters.put("n", "н");
        letters.put("o", "о");
        letters.put("p", "п");
        letters.put("r", "р");
        letters.put("s", "с");
        letters.put("t", "т");
        letters.put("u", "у");
        letters.put("f", "ф");
        letters.put("h", "х");
        letters.put("ts", "ц");
        letters.put("ch", "ч");
        letters.put("sh", "ш");
        letters.put("`", "ъ");
        letters.put("y", "иы");
        letters.put("'", "ь");
        letters.put("yu", "ю");
        letters.put("ya", "я");
        letters.put("x", "кс");
        letters.put("w", "в");
        letters.put("q", "ку");
        letters.put("iy", "ий");
    }

    public static String transliterate(String string, Direction direction) {
        String text = string.toLowerCase();
        Map<String, String> dictionary = getDictionary(direction);
        StringBuilder sb = new StringBuilder(text.length());

        ListIterator<String> iterator = text.chars()
                .mapToObj(key -> String.valueOf((char) key))
                .collect(Collectors.toList()).listIterator();

        while (iterator.hasNext()) {
            String oneCharKey = iterator.next();
            String twoCharsKey = iterator.hasNext() ? oneCharKey + iterator.next() : null;

            sb.append(dictionary.getOrDefault(twoCharsKey, dictionary.getOrDefault(oneCharKey, oneCharKey)));

            if (twoCharsKey != null && !dictionary.containsKey(twoCharsKey)) {
                iterator.previous();
            }
        }

        return sb.toString();
    }

    private static Map<String, String> getDictionary(Direction direction) {
        Map<String, String> dictionary = new HashMap<>(letters);

        if (direction.equals(Direction.CYRILLIC_TO_LATIN)) {
            dictionary.remove("w");
            dictionary = dictionary.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        }

        return dictionary;
    }
}
