package edu.java.bot.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.URI;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LinkValidatorTest {
    @Test
    @DisplayName("Correct link")
    public void isValid_shouldReturnTrue(){
        URI url = URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");

        boolean actual = LinkValidator.isValid(url);

        assertThat(actual).isTrue();
    }
    @Test
    @DisplayName("Not correct link")
    public void isValid_shouldReturnFalse(){
        URI url = URI.create("https://stacom/questions/1642028/what-is-the-operator-in-c");

        boolean actual = LinkValidator.isValid(url);

        assertThat(actual).isFalse();
    }
    @Test
    @DisplayName("Not correct link")
    public void isValid2_shouldReturnFalse(){
        URI url = URI.create("https://stackoverflow.com");

        boolean actual = LinkValidator.isValid(url);

        assertThat(actual).isFalse();
    }
    @Test
    @DisplayName("Not correct link")
    public void isValid3_shouldReturnFalse(){
        URI url = URI.create("sta.com/questions/1642028/what-is-the-operator-in-c");

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
