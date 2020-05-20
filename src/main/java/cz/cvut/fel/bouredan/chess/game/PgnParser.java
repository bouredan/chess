package cz.cvut.fel.bouredan.chess.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PgnParser {

    private static final Pattern TURN_REGEX_PATTERN = Pattern.compile("(\\d+)\\. \\S+ \\S+");

    public static Game loadGame(Path path) {
        String fileContent = null;
        try {
            fileContent = Files.readString(path).replace("\\n", "");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Matcher matcher = TURN_REGEX_PATTERN.matcher(fileContent);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
        }

        return null;
    }
}
