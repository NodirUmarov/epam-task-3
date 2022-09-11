package com.epam.business.service.provider;

import com.epam.business.model.request.TagRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/24/2022
 */
public class CreateTagProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return entities().stream()
                .map(Arguments::of);
    }

    public static List<List<TagRequest>> entities() {
        List<List<TagRequest>> listOfRequest = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            List<TagRequest> requests = new LinkedList<>();
            for (int j = 1; j <= 50; j++) {
                TagRequest request = new TagRequest();
                request.setTagName("Tag #" + i + "." + j);
                requests.add(request);
            }
            listOfRequest.add(requests);
        }
        return listOfRequest;
    }
}


