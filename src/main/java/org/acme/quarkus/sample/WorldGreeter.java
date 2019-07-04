package org.acme.quarkus.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.v3beta1.TranslationServiceClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.reactivex.Flowable;

/**
 * WorldGreeter
 */
@ApplicationScoped
public class WorldGreeter {

    @ConfigProperty(name = "google.api.projectId")
    String projectId;

    @ConfigProperty(name = "google.api.location")
    String projectLocation;

    @ConfigProperty(name = "google.api.sourceLangCode")
    String sourceLanguageCode;

    List<String> targetLanguageCodes = new ArrayList<>();

    @PostConstruct
    protected void init() {
        targetLanguageCodes.add("fr");
        targetLanguageCodes.add("sv");
        targetLanguageCodes.add("hi");
        targetLanguageCodes.add("ta");
        targetLanguageCodes.add("kn");
        targetLanguageCodes.add("es");
        targetLanguageCodes.add("ja");
        targetLanguageCodes.add("ko");
        targetLanguageCodes.add("de");
        targetLanguageCodes.add("fi");
        targetLanguageCodes.add("zh");
    }


    @Outgoing("translated-greetings")
    public Flowable<String> translateGreetings() {
        return Flowable.interval(5, TimeUnit.SECONDS).map(tick -> {
            Collections.rotate(targetLanguageCodes, 1);
            final String targetLanguageCode = targetLanguageCodes.get(0);
            return String.format("%s(%s)", translateText(targetLanguageCode), targetLanguageCode);
        });
    }

    private String translateText(String targetLanguageCode) {
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translation translation = translate.translate("Welcome to Reactive Messaging",
                TranslateOption.sourceLanguage(sourceLanguageCode),
                TranslateOption.targetLanguage(targetLanguageCode));
        return translation.getTranslatedText();
    }

}
