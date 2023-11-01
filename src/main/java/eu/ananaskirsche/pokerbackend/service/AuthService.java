package eu.ananaskirsche.pokerbackend.service;

import eu.ananaskirsche.pokerbackend.Role;
import eu.ananaskirsche.pokerbackend.controller.AuthController;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.security.RouteRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class.getSimpleName());
    private static SecretKey key;

    public static void manage(Handler handler, Context ctx, Set<? extends RouteRole> set) throws Exception {
        if(set.contains(Role.USER)){
            String accessToken = ctx.cookie("access_token");
            if(StringUtil.isBlank(accessToken)){
                ctx.status(HttpStatus.UNAUTHORIZED);
                return;
            }

            if(!isJWTValid(accessToken)){
                ctx.status(HttpStatus.UNAUTHORIZED);
                return;
            }
        }

        handler.handle(ctx);
    }


    public static String createJWT(final String username) throws Exception {
        if (key == null) {
            initKey();
        }

        Instant nowInstant = Instant.now();
        Date now = new Date(nowInstant.toEpochMilli());
        Date expiration = new Date(nowInstant.plus(30, ChronoUnit.MINUTES).toEpochMilli());

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .notBefore(now)
                .expiration(expiration)
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }


    private static boolean isJWTValid(final String jwt) throws Exception{
        if (key == null) {
            initKey();
        }

        try{
            Jwts.parser()
                .verifyWith(key)
                .clockSkewSeconds(3)
                .build()
                .parseSignedClaims(jwt);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    private static void initKey() throws Exception {
        Dotenv config = PropertiesService.getEnv();
        String secret = config.get("AUTH_SECRET");
        if (StringUtil.isBlank(secret)) {
            log.error("Please set environment variable AUTH_SECRET!");
            throw new Exception("Environment variable AUTH_SECRET is not set!");
        }

        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
