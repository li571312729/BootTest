package com.hefu.component;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

/**
 * @author mengkai
 */
@Component
public class NonStaticResourceHttpRequestHandler extends ResourceHttpRequestHandler {
    public final static String ATTR_FILE = "NON-STATIC-FILE";

    @Override
    protected Resource getResource(HttpServletRequest request) throws MalformedURLException {
        final String filePath = (String) request.getAttribute(ATTR_FILE);
        return new UrlResource(filePath);
    }
}
