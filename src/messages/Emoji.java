package messages;

public enum Emoji {
    GRINNING(":grinning:", "😃"),
    JOY(":joy:", "😂"),
    SMILING_HEART(":smiling_heart:", "🥰"),
    SUNGLASSES(":sunglasses:", "😎"),
    KISS(":kiss:", "😘"),
    THUMBSUP(":thumbsup:", "👍"),
    THUMBSDOWN(":thumbsdown:", "👎"),
    FIST(":fist:", "✊"),
    HAND(":hand:", "✋"),
    V(":v:", "✌️"),
    CLAP(":clap:", "👏"),
    EYES(":eyes:", "👀"),
    EAR(":ear:", "👂"),
    NOSE(":nose:", "👃"),
    MOUTH(":mouth:", "👄"),
    TONGUE(":tongue:", "👅"),
    RED_HEART(":heart:", "❤️"),
    YELLOW_HEART(":yellow_heart:", "💛"),
    GREEN_HEART(":green_heart:", "💚"),
    BLUE_HEART(":blue_heart:", "💙"),
    PURPLE_HEART(":purple_heart:", "💜"),
    BROKEN_HEART(":broken_heart:", "💔"),
    SPARKLING_HEART(":sparkling_heart:", "💖"),
    HEARTPULSE(":heartpulse:", "💗"),
    HEART_EYES(":heart_eyes:", "😍"),
    INNOCENT(":innocent:", "😇"),
    SLEEPING(":sleeping:", "😴"),
    SMILING_IMP(":smiling_imp:", "😈"),
    MASK(":mask:", "😷");

    private final String string;
    private final String emoji;

    Emoji(String string, String emoji) {
        this.string = string;
        this.emoji = emoji;
    }

    private String getString() {
        return string;
    }

    private String getEmoji() {
        return emoji;
    }

    public static String replaceEmojis(String input) {
        for(Emoji emoji : Emoji.values()) {
            input = input.replaceAll("(?i)" + emoji.getString(), emoji.getEmoji());
        }
        return input;
    }
}
