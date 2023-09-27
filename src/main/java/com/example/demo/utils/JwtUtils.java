package com.example.demo.utils;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.UnauthorizedException;
import com.example.demo.sevices.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final CustomUserDetailsService customUserDetailsService;
    private static final String key="MIICXAIBAAKBGQCSZFLVMALTGN2YUMXQNR0GKL8SQBW9HLBT7Z8IIDCQGBCJKQDMD61DOJR0KLJTTHBNUXI562VUT58EPPP7VXTSPDZ2N6UVLNQKUJTT61KDETJCMANXAKFMQ0NTEN9O3ZOU5LAUWMILJROFG64JSGIHTDIFHMEM4CXQIDAQABAOGADO3YEPCDLOUZWC6PZMC2CNZ0RDEHUNPHVBOVYR7YKMUXPX9GOBAN5BPW6KOF6JJKA0MUZN4QT79YZZYQMKHCJN10USQ8GRSSAEENQHTEBBQ6D26PYIO3DROUY2AHNEHZJMHIQCKZGC1BOJZJHZJFDYFDED3IEXBHBMGNH00AECQQD9AL1W1T9RKCWKZOPGCA8IX7DK9LIARN7FBGBEX6OV0PKMSSJ1R0EBA9JOHHW0PQ9HWI48REZKWCQW5CPFPFAKEALB9ZEQYEIP5OILO2L0I7CWU2BTCNGOBDGRJ2OKUIJZ9SVPIC6QPUG5OFAIKUANRULB6EVX2G25ERP01OQJBAPMO0QIQBX1QCOEKIM6T63ZQOEZORQCOLFONUXDWF4TS55YUN5MWFEOVOB9BQGTPISDZCWHXMQIUDZPATVFAM0CQB11ZPDWKIQKCP0GAM5WY7ZQCOABECV2O3KZ15SC8V6XM6ODZKGFWLZFOKDFOJEH3OB0IQQHY3NR3M16P6MLTECQBIPT51FS3Q2AYET9K3JFIVFDAHK1CHLEC4YZGBFDFUCAC7RB1IHYE1I2WHJ2HDF8QXKX9G26O8FPWN";
    private final PasswordEncoder passwordEncoder;

    public static String getUsername(@NonNull String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            if (!claims.getExpiration().before(new Date())) {
                throw new UnauthorizedException();
            }
            return claims.getSubject();
        }catch (Exception e){
            throw new UnauthorizedException();
        }
    }
    public String generateToken(@NonNull String username,@NonNull String password){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        String detailsPassword = userDetails.getPassword();
        if (!passwordEncoder.matches(password,detailsPassword)) {
            throw new BadRequestException("Wrong password");
        }
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .setIssuer("Kun Uz")
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    private static Key key(){
        byte[] decode = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(decode);
    }
}
