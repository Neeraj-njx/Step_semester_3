public class AccessChecker {

    static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "private":
                return accessorContext.equals("SAME_CLASS")
                        ? "ALLOWED" : "DENIED";

            case "default":
                return (accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED" : "DENIED";

            case "protected":
                return (accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED" : "DENIED";

            case "public":
                return "ALLOWED";

            default:
                return "DENIED";
        }
    }

    static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;

        for (String[] attempt : attempts) {
            if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) {
                allowed++;
            } else {
                denied++;
            }
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }
}
