package com.home.bakery.filters;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import com.home.bakery.data.objects.WhitelistedUri;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.ForbiddenException;
import com.home.bakery.services.authen.SecurityContextService;
import com.home.bakery.utils.EnvironmentVariable;
import com.home.bakery.utils.JwtTokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class AuthenticationFilter extends OncePerRequestFilter{

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String SERVICE = "Service ";
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private EnvironmentVariable environmentVariables;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isUriWhitelisted(request)) {
            filterChain.doFilter(request, response);
        } else {
            processAuthentication(request, response, filterChain);
        }
    }

    private boolean isUriWhitelisted(HttpServletRequest request) {
        List<String> whitelistedUris = environmentVariables.getWhiteListUrls();
        for (String whitelistedUri : whitelistedUris) {
            WhitelistedUri whitelistedUriObject = new WhitelistedUri();
            whitelistedUriObject = whitelistedUriObject.parseWhitelistedUri(whitelistedUri);
            if (whitelistedUriObject.matches(request)) {
                return true;
            }
        }
        return false;
    }

    private void processAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) {
        final Optional<String> requestTokenHeaderOpt = getJwtFromRequest(request);
        if (requestTokenHeaderOpt.isPresent()) {
            try {
                String accessToken = requestTokenHeaderOpt.get();
                Jws<Claims> jwtClaims = jwtTokenUtil.getJwsClaims(accessToken, getJwtPrefix(request));
                String email = jwtClaims.getBody().getSubject();
                securityContextService.setSecurityContext(email);
                filterChain.doFilter(request, response);
            } catch (Exception ex) {
                throw new BadRequestException(ex.getMessage(), ex);
            }
        } else {
            throw new ForbiddenException("JWT Access Token does not start with 'Bearer '.");
        }
    }

    private Optional<String> getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && (bearerToken.startsWith(BEARER) || bearerToken.startsWith(SERVICE))) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    private String getJwtPrefix(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith(BEARER)) {
                return BEARER;
            }
            if (bearerToken.startsWith(SERVICE)) {
                return SERVICE;
            }
        }
        return null;
    }
}
