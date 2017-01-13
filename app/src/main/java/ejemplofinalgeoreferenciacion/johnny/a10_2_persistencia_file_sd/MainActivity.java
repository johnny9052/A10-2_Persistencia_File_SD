package ejemplofinalgeoreferenciacion.johnny.a10_2_persistencia_file_sd;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    //OJO!! COMO OBTENER LA LISTA DE ARCHIVOS DE UNA RUTA EXTERNA
    //http://stackoverflow.com/questions/8638293/how-to-list-all-files-and-folders-locating-on-sd-card

    EditText txtNombreArchivo, txtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombreArchivo = (EditText) findViewById(R.id.txtNombreArchivo);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
    }

    public void grabar(View view) {
        String nombreArchivo = txtNombreArchivo.getText().toString();
        String mensaje = txtMensaje.getText().toString();
        try {
            File microSD = Environment.getExternalStorageDirectory(); // Se
            // accede
            // a la
            // microSD
            File file = new File(microSD.getAbsolutePath(), nombreArchivo);// Se
            // crea
            // un
            // archivo
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(file));// Objeto de escritura en el
            // archivo
            writer.write(mensaje);// Se escribe el contenido
            writer.flush();// Se saca del buffer al archivo
            writer.close();// Se cierra el archivo
            Toast.makeText(this, "Los datos fueron grabados correctamente",
                    Toast.LENGTH_SHORT).show();
            txtNombreArchivo.setText("");txtMensaje.setText("");
        } catch (IOException ioe) {
            Toast.makeText(this,
                    "Error creando el archivo,verifique si existe microSD",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperar(View view) {
        String nombreArchivo = txtNombreArchivo.getText().toString();
        // Se accede a la microSD
        File microSD = Environment.getExternalStorageDirectory();
        // Se crea un archivo
        File file = new File(microSD.getAbsolutePath(), nombreArchivo);
        try {
            // Se asocia el archivo leido
            FileInputStream fIn = new FileInputStream(file);
            // Se asocia una lectura al archivo
            InputStreamReader lectura = new InputStreamReader(fIn);
            // Se genera un buffer para la lectura
            BufferedReader buffer = new BufferedReader(lectura);
            String linea = buffer.readLine();// se le la primera linea
            String todo = "";
            // Mientras la linea no sea null lea la proxima y concatene
            while (linea != null) {
                todo = todo + linea + "";
                linea = buffer.readLine();
            }
            buffer.close();// se cierra el buffer
            lectura.close();// Se cierra la lectura
            txtMensaje.setText(todo);// Se coloca el texto en la caja de texto
        } catch (IOException e) {
            Toast.makeText(this, "No existe el archivo especificado",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void salir(View view) {
        finish();
    }

}
