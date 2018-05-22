package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest02 extends AppCompatActivity {

    private EditText port = null;
    private Button clientButton = null;
    private Button serverButton = null;
    private EditText partialText = null;


    private ServerThread serverThread = null;
    //private ClientThread clientThread = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02);

        port = (EditText) findViewById(R.id.text_port);
        serverButton = (Button) findViewById(R.id.button_server);
        clientButton = (Button) findViewById(R.id.button_client);
        partialText = (EditText) findViewById(R.id.text_partial);

        ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
        serverButton.setOnClickListener(connectButtonClickListener);
    }


    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = port.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.setData(partialText.getText().toString());
            serverThread.start();
        }
    }
}
