

public class AccessChecker {

    static String classifyAccess(String fieldModifier, String accessorContext) {

        switch (fieldModifier) {

            case "private":
                return accessorContext.equals("SAME_CLASS")
                        ? "ALLOWED"
                        : "DENIED";

            case "default":
                return accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE")
                        ? "ALLOWED"
                        : "DENIED";

            case "protected":
                if (accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE")
                        || accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "public":
                return "ALLOWED";

            default:
                return "DENIED";
        }
    }

    static String summarizeByModifier(String[][] attempts) {

        String[] modifiers = {"private", "default", "protected", "public"};

        int[] allowed = new int[4];
        int[] denied = new int[4];

        for (String[] attempt : attempts) {

            String modifier = attempt[0];
            String context = attempt[1];

            String result = classifyAccess(modifier, context);

            int index = -1;

            for (int i = 0; i < modifiers.length; i++) {
                if (modifiers[i].equals(modifier)) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                if (result.equals("ALLOWED")) {
                    allowed[index]++;
                } else {
                    denied[index]++;
                }
            }
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < modifiers.length; i++) {

            if (i > 0) {
                result.append(" | ");
            }

            result.append(modifiers[i])
                  .append(": ")
                  .append(allowed[i])
                  .append(" allowed / ")
                  .append(denied[i])
                  .append(" denied");
        }

        return result.toString();
    }

    static String firstDeniedAttempt(String[][] attempts) {

        for (int i = 0; i < attempts.length; i++) {

            String modifier = attempts[i][0];
            String context = attempts[i][1];

            if (classifyAccess(modifier, context).equals("DENIED")) {
                return modifier + " via " + context
                        + " (attempt #" + (i + 1) + ")";
            }
        }

        return "None Denied";
    }
}
