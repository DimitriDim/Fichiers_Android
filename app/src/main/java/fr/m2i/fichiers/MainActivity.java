package fr.m2i.fichiers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText edTextSave;
    EditText edTextLoad;

    EditText edTextSaveExt;
    EditText edTextLoadExt;

    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // question demandé à l'utilisateur, ici aura elle aura le code 1
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


    @Override
    //méthode evenement d'attente de la résponse de l'utilisateur confirmant ou non son accord
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) { // tester à quelle question on répond
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Autorisation OK", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Autorisation refusée", Toast.LENGTH_LONG).show();
            }
        }

    }


    public void btnSave(View v) {

        edTextSave = findViewById(R.id.editTextSave);
        text = edTextSave.getText().toString();

        byte bytes[] = text.getBytes();
        try {
            FileOutputStream fos = this.openFileOutput("data.txt", MODE_PRIVATE);
            fos.write(bytes);
            fos.close();
        } catch (Exception ex) {
        }

    }

    public void btnLoad(View v) {
        String texte;
        edTextLoad = findViewById(R.id.editTextLoad);
        byte bytes[] = new byte[100];
        try {
            FileInputStream fis = this.openFileInput("data.txt");
            fis.read(bytes);
            fis.close();
        } catch (Exception ex) {

        }
        texte = new String(bytes);
        //trim pour éliminer les bytes restant aprés
        edTextLoad.setText(texte.trim());
    }

    public void saveExternal(View v) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, "dataExt.txt");

        edTextSaveExt = findViewById(R.id.editTextSave);
        text = edTextSaveExt.getText().toString();

        byte bytes[] = text.getBytes();
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(bytes);
            stream.close();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    public void LoadExternal(View v) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, "dataExt.txt");

        String texte;
        edTextLoadExt = findViewById(R.id.editTextLoad);
        byte bytes[] = new byte[100];
        try {
            FileInputStream stream = new FileInputStream(file);
            stream.read(bytes);
            stream.close();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        texte = new String(bytes);
        //trim pour éliminer les bytes restant aprés
        edTextLoadExt.setText(texte.trim());

    }

    public void refresh(View v) {


        edTextLoadExt = findViewById(R.id.editTextLoad);
        edTextLoad = findViewById(R.id.editTextLoad);

        edTextLoadExt.setText("");
        edTextLoad.setText("");


    }


}
