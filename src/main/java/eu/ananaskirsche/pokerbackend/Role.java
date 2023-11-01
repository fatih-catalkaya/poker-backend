package eu.ananaskirsche.pokerbackend;

import io.javalin.security.RouteRole;

public enum Role implements RouteRole {
    ANONYMOUS, USER
}
