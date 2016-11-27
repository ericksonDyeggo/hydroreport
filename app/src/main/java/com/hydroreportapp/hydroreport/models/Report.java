package com.hydroreportapp.hydroreport.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by erickson on 27/11/16.
 */
@IgnoreExtraProperties
public class Report {

    public String usuario;

    public double cord_x;
    public double cord_y;

    public String descricao;
    public boolean odor;
    public boolean cor;
    public boolean vazamento;
    public boolean falta;
    public boolean gosto;
    public String foto;

    public Report(double cord_x, double cord_y, String descricao, boolean odor, boolean cor, boolean vazamento, boolean falta, boolean gosto) {
        this.cord_x = cord_x;
        this.cord_y = cord_y;
        this.descricao = descricao;
        this.odor = odor;
        this.cor = cor;
        this.vazamento = vazamento;
        this.falta = falta;
        this.gosto = gosto;
    }
}
