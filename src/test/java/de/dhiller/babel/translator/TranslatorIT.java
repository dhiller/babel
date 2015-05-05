package de.dhiller.babel.translator;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TranslatorIT {

    private Translator underTest = new Translator();

    @Test
    public void translateEnglish() {
        List<String> translate = underTest.translate(asList("This is a test"));
        assertThat(translate, Matchers.<List<String>>both(notNullValue()).and(not(empty())));
    }

}