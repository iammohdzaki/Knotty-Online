package com.zaphlabs.knotty_online.utils.extensions;

public class SafeString {

    private static final String EMPTY_STRING = "";

    /**
     * Method to assign Strings safely
     *
     * @param assignable
     * @param alternative
     * @return
     */
    public static String assign(String assignable, String alternative) {

        return assignable == null ? (alternative == null ? EMPTY_STRING : alternative) : (assignable.equals("null") ? assign(alternative) : assignable);
    }

    /**
     * Method to assign Strings safely
     *
     * @param assignable
     * @param filterAssignable
     * @param alternative
     * @return
     */
    public static String assign(String assignable, String filterAssignable, String alternative) {

        return assignable == null ? (alternative == null ? EMPTY_STRING : alternative) : (assignable.equals(filterAssignable) ? assign(alternative) : assignable);
    }


    /**
     * Method to assign strings Safely
     *
     * @param assignable
     * @return
     */
    public static String assign(String assignable) {

        return assignable == null || assignable.equalsIgnoreCase("[]") ? EMPTY_STRING : assignable;
    }

}
