package com.cookandroid.catchnoteproject.db.model;

public enum NoteBookPurpose {

    NOTE("문서작업용", "i3"),
    DESIGN("영상편집/디자인용", "i5"),
    GAME("게임용", "i7");

    private String name;

    private String code;

    private NoteBookPurpose(String name, String code){
        this.code = code;
        this.name = name;
    }

    public String getCode(){
        return this.code;
    }

    public String getName(){
        return this.name;
    }

    public static NoteBookPurpose getNoteBookPurposeByName(String name){
        for(NoteBookPurpose noteBookPurpose : values()){
            if(noteBookPurpose.getName().equals(name)){
                return  noteBookPurpose;
            }
        }
        return null;
    }

}
