package org.acme.quarkus.sample;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.reactivex.Flowable;

/**
 * LinguaGreeterService
 */
@ApplicationScoped
public class LinguaGreeterService {

    @ConfigProperty(name="google.api.translate.srcLangCode")
    String srcLangCode;

    @ConfigProperty(name="google.api.translate.targetLangCodes")
    List<String> targetLangCodes;

    @Outgoing("translated-greetings")
    public Flowable<String> greetings(){
        return Flowable.interval(5, TimeUnit.SECONDS)
        .map(tick -> {
            String targetLangCode = targetLangCodes.get(0);
            Collections.rotate(targetLangCodes, 1);
            return String.format("%s(%s)",
            translateText(targetLangCode),targetLangCode);
        });
    }

    private String translateText(String targetLangCode){
        TranslateOptions translateOptions = TranslateOptions.getDefaultInstance();

        Translation translator =  translateOptions
        .getService()
        .translate("Welcome to Reactive Programming",
        TranslateOption.sourceLanguage(srcLangCode),
        TranslateOption.targetLanguage(targetLangCode));
        return translator.getTranslatedText();
    }
}