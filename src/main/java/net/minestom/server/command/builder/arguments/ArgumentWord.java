package net.minestom.server.command.builder.arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Represents a single word in the command.
 * <p>
 * You can specify the only correct words with {@link #from(String...)}.
 * <p>
 * Example: hey
 */
public class ArgumentWord extends Argument<String> {

    public static final int SPACE_ERROR = 1;
    public static final int RESTRICTION_ERROR = 2;

    protected String[] restrictions;

    public ArgumentWord(String id) {
        super(id, false);
    }

    /**
     * Used to force the use of a few precise words instead of complete freedom.
     *
     * @param restrictions the accepted words
     * @return 'this'
     */
    @NotNull
    public ArgumentWord from(@Nullable String... restrictions) {
        this.restrictions = restrictions;
        return this;
    }

    @Override
    public int getCorrectionResult(@NotNull String value) {
        if (value.contains(" "))
            return SPACE_ERROR;

        return SUCCESS;
    }

    @NotNull
    @Override
    public String parse(@NotNull String value) {
        return value;
    }

    @Override
    public int getConditionResult(@NotNull String value) {
        // Check restrictions
        if (hasRestrictions()) {
            for (String r : restrictions) {
                if (value.equalsIgnoreCase(r))
                    return SUCCESS;
            }
            return RESTRICTION_ERROR;
        }

        return SUCCESS;
    }

    /**
     * Gets if this argument allow complete freedom in the word choice or if a list has been defined.
     *
     * @return true if the word selection is restricted
     */
    public boolean hasRestrictions() {
        return restrictions != null && restrictions.length > 0;
    }

    /**
     * Gets all the word restrictions
     *
     * @return the word restrictions, can be null
     */
    @Nullable
    public String[] getRestrictions() {
        return restrictions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArgumentWord that = (ArgumentWord) o;
        return Arrays.equals(restrictions, that.restrictions);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(restrictions);
    }
}
