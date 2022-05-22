package helpers;

import org.assertj.core.api.Assertions;

public class Verifications {
    
    public static void assertTrue(boolean condition, String errorMessage) {
        Assertions.assertThat(condition)
                .overridingErrorMessage(errorMessage)
                .isTrue();
    }
    
    public static void assertFalse(boolean condition, String errorMessage) {
        Assertions.assertThat(condition)
                .overridingErrorMessage(errorMessage)
                .isFalse();
    }
    
    public static void assertEquals(Object actual, Object expected, String errorMessage) {
        Assertions.assertThat(actual).isEqualTo(expected)
                .overridingErrorMessage(errorMessage);
    }
    
    public static void assertContains(String object, String pattern, String errorMessage) {
        Assertions.assertThat(object).contains(pattern)
                .overridingErrorMessage(errorMessage);
    }
    
}
