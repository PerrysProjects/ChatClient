package messages;

public class Swear {
    private static final String[] swears = new String[]{"arse", "arsehead", "arsehole", "ass", "asshole", "bastard", "bitch", "bloody",
            "bollocks", "brotherfucker", "bugger", "bullshit", "child-fucker", "Christ on a bike", "Christ on a cracker",
            "cock", "cocksucker", "crap", "cunt", "damn", "damn it", "dick", "dickhead", "dyke", "fatherfucker", "frigger",
            "fuck", "goddamn", "godsdamn", "hell", "holy shit", "horseshit", "in shit", "Jesus Christ", "Jesus fuck",
            "Jesus H. Christ", "Jesus Harold Christ", "Jesus wept", "Jesus,Mary and Joseph", "kike", "motherfucker", "nigga",
            "nigger", "nigra", "piss", "prick", "pussy", "shit", "shit ass", "shite", "sisterfucker", "slut", "son of a bitch",
            "son of a whore", "spastic", "sweet Jesus", "turd", "twat", "wanker"};

    public static String replaceSwears(String input) {
        for(String swear : swears) {
            input = input.replaceAll("(?i)" + swear, "*".repeat(swear.length()));
        }
        return input;
    }
}
