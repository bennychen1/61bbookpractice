public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        if (input == null || pattern == null || pattern.length() > input.length()) {
            return -1;
        }
        RollingString patternString = new RollingString(pattern, pattern.length());
        int patternHashCode = patternString.hashCode();
        String inputSubstring = input.substring(0, pattern.length());
        RollingString inputString = new RollingString(inputSubstring, pattern.length());

        int start = 0;

        for (int i = pattern.length(); i <= input.length(); i = i + 1) {

            boolean stringEquals = patternString.toString().equals(inputString.toString());

            if (i == input.length()) {
                if (stringEquals) {
                    return start;
                } else {
                    return -1;
                }
            }

            int x = inputString.hashCode();

            if (patternHashCode == inputString.hashCode()) {
                if (stringEquals) {
                    return start;
                }
            } else {
                inputString.addChar(input.charAt(i));
                start = start + 1;
            }
        }

        return -1;
    }

}
