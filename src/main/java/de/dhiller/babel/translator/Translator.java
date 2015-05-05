package de.dhiller.babel.translator;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import de.dhiller.babel.translator.yandex.xml.Translation;
import org.apache.http.entity.ContentType;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Translator {

    private static final String apiKey = System.getProperty("translate.api.key");

    List<String> translate(List<String> input) {
        HttpRequest httpRequest = Unirest
                .get("https://translate.yandex.net/api/v1.5/tr/translate")
                .queryString(ImmutableMap.<String, Object>builder()
                                .put("key", apiKey)
                                .put("lang", "en-fr")
                                .build()
                )
                .header("Accept", ContentType.APPLICATION_XML.toString());
        for (String line : input)
            httpRequest = httpRequest.queryString("text", line);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Translation.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            HttpResponse<String> stringHttpResponse = httpRequest.asString();
            Translation translation = (Translation) unmarshaller.unmarshal(new StringReader(stringHttpResponse
                    .getBody()));
            return translation.getText();
        } catch (UnirestException | JAXBException e) {
            throw Throwables.propagate(e);
        }
    }

}
