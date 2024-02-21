package edu.java.bot.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.URI;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LinkValidatorTest {
    @Test
    @DisplayName("Correct link (with https)")
    public void isValid_whenWithHttps_shouldReturnTrue(){
        URI url = URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");

        boolean actual = LinkValidator.isValid(url);

        assertThat(actual).isTrue();
    }
    @Test
    @DisplayName("Correct link (no https)")
    public void isValid_whenNoHttps_shouldReturnTrue(){
        URI url = URI.create("stackoverflow.com/questions/1642028/what-is-the-operator-in-c");

        boolean actual = LinkValidator.isValid(url);

        assertThat(actual).isTrue();
    }
    @Test
    @DisplayName("Not correct link (no .com)")
    public void isValid_whenNoDot_shouldReturnFalse(){
        URI url = URI.create("https://stacom/questions/1642028/what-is-the-operator-in-c");

        boolean actual = LinkValidator.isValid(url);

        assertThat(actual).isFalse();
    }
    @Test
    @DisplayName("Empty string")
    public void isValid_whenEmptyString_shouldReturnFalse(){
        URI url = URI.create("");

        boolean actual = LinkValidator.isValid(url);

        assertThat(actual).isFalse();
    }
}
