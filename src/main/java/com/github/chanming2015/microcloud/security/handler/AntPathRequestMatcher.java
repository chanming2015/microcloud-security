package com.github.chanming2015.microcloud.security.handler;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class AntPathRequestMatcher implements PathRequestMatcher
{
    private static final Log logger = LogFactory.getLog(AntPathRequestMatcher.class);
    private static final String MATCH_ALL = "/**";

    private final Matcher matcher;
    private final String pattern;
    private final HttpMethod httpMethod;
    private final boolean caseSensitive;

    public AntPathRequestMatcher(String pattern)
    {
        this(pattern, null);
    }

    public AntPathRequestMatcher(String pattern, String httpMethod)
    {
        this(pattern, httpMethod, true);
    }

    public AntPathRequestMatcher(String pattern, String httpMethod, boolean caseSensitive)
    {
        Assert.hasText(pattern, "Pattern cannot be null or empty");
        this.caseSensitive = caseSensitive;

        if (pattern.equals(MATCH_ALL) || pattern.equals("**"))
        {
            pattern = MATCH_ALL;
            this.matcher = null;
        }
        else
        {
            // If the pattern ends with {@code /**} and has no other wildcards or path
            // variables, then optimize to a sub-path match
            if (pattern.endsWith(MATCH_ALL) && (pattern.indexOf('?') == -1 && pattern.indexOf('{') == -1 && pattern.indexOf('}') == -1) && pattern.indexOf("*") == pattern.length() - 2)
            {
                this.matcher = new SubpathMatcher(pattern.substring(0, pattern.length() - 3), caseSensitive);
            }
            else
            {
                this.matcher = new SpringAntMatcher(pattern, caseSensitive);
            }
        }

        this.pattern = pattern;
        this.httpMethod = StringUtils.hasText(httpMethod) ? HttpMethod.valueOf(httpMethod) : null;
    }

    @Override
    public boolean matches(ServerHttpRequest request)
    {
        if (this.httpMethod != null && StringUtils.hasText(request.getMethod().name()) && this.httpMethod != request.getMethod())
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Request '" + request.getMethod() + " " + getRequestPath(request) + "'" + " doesn't match '" + this.httpMethod + " " + this.pattern + "'");
            }

            return false;
        }

        if (this.pattern.equals(MATCH_ALL))
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Request '" + getRequestPath(request) + "' matched by universal pattern '/**'");
            }

            return true;
        }

        String url = getRequestPath(request);

        if (logger.isDebugEnabled())
        {
            logger.debug("Checking match of request : '" + url + "'; against '" + this.pattern + "'");
        }

        return this.matcher.matches(url);
    }

    private String getRequestPath(ServerHttpRequest request)
    {
        return request.getPath().value();
    }

    public String getPattern()
    {
        return this.pattern;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof AntPathRequestMatcher))
        {
            return false;
        }
        AntPathRequestMatcher other = (AntPathRequestMatcher) obj;
        return this.pattern.equals(other.pattern) && this.httpMethod == other.httpMethod && this.caseSensitive == other.caseSensitive;
    }

    @Override
    public int hashCode()
    {
        int result = this.pattern != null ? this.pattern.hashCode() : 0;
        result = 31 * result + (this.httpMethod != null ? this.httpMethod.hashCode() : 0);
        result = 31 * result + (this.caseSensitive ? 1231 : 1237);
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Ant [pattern='").append(this.pattern).append("'");

        if (this.httpMethod != null)
        {
            sb.append(", ").append(this.httpMethod);
        }

        sb.append("]");

        return sb.toString();
    }

    private interface Matcher
    {
        boolean matches(String path);

        Map<String, String> extractUriTemplateVariables(String path);
    }

    /**
     * Optimized matcher for trailing wildcards
     */
    private static class SubpathMatcher implements Matcher
    {
        private final String subpath;
        private final int length;
        private final boolean caseSensitive;

        private SubpathMatcher(String subpath, boolean caseSensitive)
        {
            assert !subpath.contains("*");
            this.subpath = caseSensitive ? subpath : subpath.toLowerCase();
            this.length = subpath.length();
            this.caseSensitive = caseSensitive;
        }

        @Override
        public boolean matches(String path)
        {
            if (!this.caseSensitive)
            {
                path = path.toLowerCase();
            }
            return path.startsWith(this.subpath) && (path.length() == this.length || path.charAt(this.length) == '/');
        }

        @Override
        public Map<String, String> extractUriTemplateVariables(String path)
        {
            return Collections.emptyMap();
        }
    }

    private static class SpringAntMatcher implements Matcher
    {
        private final AntPathMatcher antMatcher;

        private final String pattern;

        private SpringAntMatcher(String pattern, boolean caseSensitive)
        {
            this.pattern = pattern;
            this.antMatcher = createMatcher(caseSensitive);
        }

        @Override
        public boolean matches(String path)
        {
            return this.antMatcher.match(this.pattern, path);
        }

        @Override
        public Map<String, String> extractUriTemplateVariables(String path)
        {
            return this.antMatcher.extractUriTemplateVariables(this.pattern, path);
        }

        private static AntPathMatcher createMatcher(boolean caseSensitive)
        {
            AntPathMatcher matcher = new AntPathMatcher();
            matcher.setTrimTokens(false);
            matcher.setCaseSensitive(caseSensitive);
            return matcher;
        }
    }
}
