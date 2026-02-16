package de.aleksejsch.playwrightjava.utils;

public enum UrlPathsUtil {
    OWNER_NEW("/owners/new"),
    OWNER_EDIT("/owners/%d/edit"),
    OWNER_SHOW("/owners/%d"),
    OWNER_FIND("/owners/find"),
    PET_NEW("/owners/%d/pets/new"),
    VISIT_NEW("/owners/%d/pets/%d/visits/new");

    private final String path;

    UrlPathsUtil(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }

    public String format(Object... args) {
        return String.format(path, args);
    }
}

