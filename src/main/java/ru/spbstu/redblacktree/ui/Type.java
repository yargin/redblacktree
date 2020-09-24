package ru.spbstu.redblacktree.ui;

import ru.spbstu.redblacktree.utils.Transformer;

public enum Type {
    INTEGER(Integer::valueOf, "Integer"), DOUBLE(Double::valueOf, "Double"), STRING(s -> s, "String");

    private final Transformer<String, ?> parser;
    private final String typeName;

    Type(Transformer<String, ?> parser, String typeName) {
        this.parser = parser;
        this.typeName = typeName;
    }

    public Transformer<String, ?> getParser() {
        return parser;
    }

    public String getTypeName() {
        return typeName;
    }
}
