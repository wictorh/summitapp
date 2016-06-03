package br.com.summitpcm.app.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import br.com.summitpcm.app.R;
import br.com.summitpcm.app.config.AppConfig;
import br.com.summitpcm.app.config.AppController;
import br.com.summitpcm.app.helper.NetworkManager;
import br.com.summitpcm.app.helper.SQLiteHandler;
import br.com.summitpcm.app.helper.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnLogin;
   // private Button btnLinkToRegister;
    private EditText inputLogin;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private  TextView msgErro;
    int retryCount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        msgErro = (TextView)findViewById(R.id.msgErro);
        inputLogin = (EditText) findViewById(R.id.login);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        //btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        if(NetworkManager.getConnectivityStatus(getApplicationContext()) != 0)
            msgErro.setVisibility(View.INVISIBLE);
        else
            msgErro.setVisibility(View.VISIBLE);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        // Verifica se o usuario já esta logado ou não
        if (session.isLoggedIn()) {
            // Usuario já esta logado.. enviar para Home
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
       }
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputLogin.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    if(NetworkManager.getConnectivityStatus(getApplicationContext()) != 0)
                        checkLogin(email, password);
                    else
                        Toast.makeText(getApplicationContext(), "Sem Conexão", Toast.LENGTH_LONG) .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor, insira os dados!", Toast.LENGTH_LONG) .show();
                }
            }

        });
    }

    private void checkLogin(final String email, final String password) {
        Log.d(TAG, "teste ");
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog.setMessage("Logando ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                String.format(AppConfig.URL_LOGIN,email,password), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject((response));
                    boolean error = jObj.getBoolean("isLogged");

                    // Check for error node in json
                    if (error) {
                        // Usuario Logado com sucesso
                        // Criar sessão de usuario
                        session.setLogin(true);

                        // Salva o usuario no SQLite
                        JSONObject usuario = jObj.getJSONObject("usuario");
                        Log.d(TAG, "Login usuario Response: " + usuario);
                        // JSONObject user = jObj.getJSONObject("Login");
                        String name = usuario.getString("Nome");

                        String email = usuario.getString("Email");

                        String uid = String.valueOf((usuario.getInt("UsuarioId")));
                        String created_at =  new Date().toString();

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        // Inicia a Activity Home
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Erro no login. Captura mensagem de erro
                        Toast.makeText(getApplicationContext(),
                                "Usuario e/ou senha incorretos", Toast.LENGTH_LONG).show();
                    }
                }  catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    if(retryCount <3){
                        retryCount++;
                        hideDialog();
                       checkLogin(email,password);
                    }else{
                        hideDialog();
                        msgErro.setText("Conexão Lenta");
                    }
                }else{
                    Log.e(TAG, "Login Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }
        }) {};

        // Adiciona requisicação para "request queue"
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
