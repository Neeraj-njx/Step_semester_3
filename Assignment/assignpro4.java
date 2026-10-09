public class Main {

public static String normalizeCode(String raw) {
    String code = raw.trim();

    if (code.length() < 3) {
        return code.toUpperCase();
    }

    return code.substring(0, 3).toUpperCase()
            + code.substring(3);
}

public static String validateAndFormat(String code) {
    code = normalizeCode(code);

    if (code.length() != 13) {
        return "Invalid: code must be exactly 13 characters";
    }

    for (int i = 0; i < 3; i++) {
        if (!Character.isLetter(code.charAt(i))) {
            return "Invalid: publisher code must be 3 letters";
        }
    }

    for (int i = 3; i < 13; i++) {
        if (!Character.isDigit(code.charAt(i))) {
            return "Invalid: body must contain only digits";
        }
    }

    String publisher = code.substring(0, 3);
    String year = code.substring(3, 7);
    String catalog = code.substring(7, 13);

    StringBuilder result = new StringBuilder();
    result.append("[")
          .append(publisher)
          .append("] YEAR: ")
          .append(year)
          .append(" | CATALOG: ")
          .append(catalog);

    return result.toString();
}

public static void main(String[] args) {
    System.out.println(
        validateAndFormat(" pen2026004251 ")
    );

    System.out.println(
        validateAndFormat("12N2026004251")
    );
}


}
