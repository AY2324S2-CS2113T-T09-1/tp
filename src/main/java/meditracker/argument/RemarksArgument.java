package meditracker.argument;

/**
 * Remarks for medication
 */
public class RemarksArgument extends Argument {
    public RemarksArgument(boolean isOptional) {
        super(
                ArgumentName.REMARKS,
                "-r",
                "Additional remarks on medication",
                isOptional,
                true
        );
    }
}
