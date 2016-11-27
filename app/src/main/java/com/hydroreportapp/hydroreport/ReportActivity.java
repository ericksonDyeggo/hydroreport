package com.hydroreportapp.hydroreport;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hydroreportapp.hydroreport.models.Report;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by erickson on 27/11/16.
 */
@EActivity(R.layout.activity_report)
public class ReportActivity extends AppCompatActivity {

    DatabaseReference mDatabase;

    @ViewById
    CheckBox chkBoxOdor;
    @ViewById
    CheckBox chkBoxCor;
    @ViewById
    CheckBox chkBoxVazamento;
    @ViewById
    CheckBox chkBoxGosto;
    @ViewById
    CheckBox chkBoxFalta;
    @ViewById
    EditText edtDescricao;
    @ViewById
    Button btnReportar;

    @Extra(Constants.COORD_X)
    double cord_x;
    @Extra(Constants.COORD_Y)
    double cord_y;

    @AfterViews
    void afterViews() {
        initializeFirebase();
    }


    private void initializeFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Click
    void btnReportar() {
        submitReport();
        clearData();
    }

    private void clearData() {
        edtDescricao.setText("");
        chkBoxOdor.setChecked(false);
        chkBoxCor.setChecked(false);
        chkBoxVazamento.setChecked(false);
        chkBoxGosto.setChecked(false);
        chkBoxFalta.setChecked(false);
    }

    private void submitReport() {

        String descricao = edtDescricao.getText().toString();

        Report report = new Report(cord_x, cord_y, descricao, chkBoxOdor.isChecked(),
                chkBoxCor.isChecked(), chkBoxVazamento.isChecked(), chkBoxGosto.isChecked()
                , chkBoxFalta.isChecked());

        mDatabase.push().setValue(report);
    }

}
