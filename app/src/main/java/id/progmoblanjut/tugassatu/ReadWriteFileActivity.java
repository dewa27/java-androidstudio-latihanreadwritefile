package id.progmoblanjut.tugassatu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ReadWriteFileActivity extends AppCompatActivity {
    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText editContent;
    EditText editTitle;
    File path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write_file);
        btnNew=(Button) findViewById(R.id.new_file);
        btnOpen=(Button) findViewById(R.id.open_file);
        btnSave=(Button) findViewById(R.id.save_file);
        editTitle=(EditText) findViewById(R.id.edit_title) ;
        editContent=(EditText) findViewById(R.id.edit_content) ;
        btnNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newFile();
            }
        });
        btnOpen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFile();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
        path = getFilesDir();
    }
    public void newFile() {

        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this, "Clearing file" + ReadWriteFileActivity.this.getFilesDir().getAbsolutePath(), Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        editTitle.setText(title);
        editContent.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + editTitle.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }
}