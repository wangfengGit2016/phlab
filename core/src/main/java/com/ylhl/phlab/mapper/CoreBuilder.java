package com.ylhl.phlab.mapper;

public class CoreBuilder {

    public static InsertBuilder insert(){

        return new InsertBuilder();
    }

    public static DeleteBuilder delete(){

        return new DeleteBuilder();
    }

    public static UpdateBuilder update(){

        return new UpdateBuilder();
    }

    public static SelectBuilder select(){

        return new SelectBuilder();
    }


}
