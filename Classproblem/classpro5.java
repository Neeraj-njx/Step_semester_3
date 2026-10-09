public class Main {


public static String normalizeReference(String raw) {
    String reference = raw.trim();

    if (reference.length() < 3) {
        return reference.toUpperCase();
    }

    return reference.substring(0, 3).toUpperCase()
            + reference.substring(3);
}

public static String validateAndFormat(String reference) {
    reference = normalizeReference(reference);

    if (reference.length() != 14) {
        return "Invalid: reference must be exactly 14 characters";
    }

    for (int i = 0; i < 3; i++) {
        if (!Character.isLetter(reference.charAt(i))) {
            return "Invalid: bank code must be 3 letters";
        }
    }

    for (int i = 3; i < 14; i++) {
        if (!Character.isDigit(reference.charAt(i))) {
            return "Invalid: body must contain only digits";
        }
    }

    String bankCode = reference.substring(0, 3);
    String date = reference.substring(3, 9);
    String sequence = reference.substring(9, 14);

    StringBuilder result = new StringBuilder();
    result.append("[")
          .append(bankCode)
          .append("] DATE: ")
          .append(date.substring(0, 2))
          .append("/")
          .append(date.substring(2, 4))
          .append("/")
          .append(date.substring(4, 6))
          .append(" | SEQ: ")
          .append(sequence);

    return result.toString();
}

public static void main(String[] args) {
    System.out.println(
        validateAndFormat(" hdf03022600042 ")
    );

    System.out.println(
        validateAndFormat("12F03022600042")
    );
}


}
