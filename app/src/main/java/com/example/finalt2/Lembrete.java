package com.example.finalt2;

public class Lembrete {
    private int _id;
    private String title;
    private String description;

    private String date;

    private int valid;

    public Lembrete(int _id, String titulo, String desc, String date, int valid) {
        this._id = _id;
        this.title = titulo;
        this.description = desc;
        this.date = date;
        this.valid = valid;

    }

    public String getTitle() {
        return title;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return  "\n"+ "Titulo :"+title+ "\n"+
                "Descripcion :"+description+"\n"+
                "Data: "+date+"\n";


    }
}
