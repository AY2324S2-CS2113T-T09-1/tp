package meditracker.argument;

/**
 * ArgumentName enum to standardise the enum values for use
 * by Command classes and ArgumentParser
 */
public enum ArgumentName {
    DOSAGE_MORNING("dosageMorning"),
    DOSAGE_AFTERNOON("dosageAfternoon"),
    DOSAGE_EVENING("dosageEvening"),
    EXPIRATION_DATE("expirationDate"),
    REPEAT("repeat"),
    DAY_ADDED("dayAdded"),
    LIST_INDEX("listIndex"),
    NAME("name"),
    QUANTITY("quantity"),
    REMARKS("remarks"),
    LIST_TYPE("listType"),
    HELP("help"),
    MORNING("morning"),
    AFTERNOON("afternoon"),
    EVENING("evening"),
    ILLNESS("illness"),
    SIDE_EFFECTS("sideEffects"),
    ALL_FIELDS("allFields"),
    SAVE_FILE("saveFile"),
    LOAD_FILE("loadFile");

    public final String value;

    ArgumentName(String value) {
        this.value = value;
    }

    /**
     * Performs a reverse search to get the enum value from the associated string.
     *
     * @param valueToCompare The String value to compare to get the enum.
     * @return The corresponding enum if it matches the value compared. Null otherwise.
     * @see meditracker.command.CommandName for a similar implementation (adapted from there)
     */
    public static ArgumentName getEnumOfArgumentValue(String valueToCompare) {
        // Original implementation: https://www.baeldung.com/java-enum-values
        for (ArgumentName argName : values()) {
            if (argName.value.equals(valueToCompare)) {
                return argName;
            }
        }
        return null;
    }
}
