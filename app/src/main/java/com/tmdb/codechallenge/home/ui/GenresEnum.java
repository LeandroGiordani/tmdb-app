package com.tmdb.codechallenge.home.ui;

public enum GenresEnum {
    ACTION(28),
    ANIMATION(16),
    COMEDY(35),
    DOCUMENTARY(99),
    DRAMA(18),
    HORROR(27),
    ROMANCE(10749),
    THRILLER(53);

    public final int id;

    GenresEnum(int id) {
        this.id = id;
    }
}
