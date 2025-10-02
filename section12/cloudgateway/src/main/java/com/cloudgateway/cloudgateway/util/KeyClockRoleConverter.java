package com.cloudgateway.cloudgateway.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyClockRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> claimsMap = (Map<String, Object>) source.getClaims().get("realm_access");
        if(claimsMap == null || claimsMap.isEmpty()){
            return new ArrayList<>();
        }
        List<GrantedAuthority> listOfAuthorities = ((List<String>) claimsMap.get("roles")).stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        return listOfAuthorities;
    }
}
