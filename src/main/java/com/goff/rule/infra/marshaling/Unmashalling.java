package com.goff.rule.infra.marshaling;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goff.rule.infra.exception.ReflectionCreateInstanceException;

public final class Unmashalling {

    private static final Logger LOG = LoggerFactory.getLogger(Unmashalling.class);

    private Unmashalling() {
        super();
    }

    public static <T> T unmarshall(final String xmlString, final Class<T> type) {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(type);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            @SuppressWarnings("unchecked")
            final T objectResult = (T) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
            return objectResult;
        }
        catch (final JAXBException e) {
            final String errorNaConversao = "Não foi possível converter o XML em Objeto." + xmlString;
            LOG.error(errorNaConversao);
            LOG.debug(errorNaConversao, e);
            return nullObject(type);
        }
    }

    private static <T> T nullObject(final Class<T> type) {

        try {
            return type.newInstance();
        }
        catch (final InstantiationException | IllegalAccessException e) {
            throw new ReflectionCreateInstanceException("Não foi possível instanciar o objeto.", e);
        }
    }

}
