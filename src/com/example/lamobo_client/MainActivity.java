package com.example.lamobo_client;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kortide.remote.ConnectionType;
import com.kortide.remote.ResultCallback;
import com.kortide.remote.RemoteManager;
import com.kortide.remote.RemoteMessage;
import com.kortide.remote.ResultMsg;

public class MainActivity extends Activity {
	private static final String TAG = "ClientApp";

    public RemoteManager mRemoteManager;
    public RemoteMessage mRemoteMessage;
    public Context mContext;
    
    public Button connectBtn;
    public Button loginBtn;
    public Button sendBtn;
    public EditText mailETx;
    public EditText pwdETx;
    public EditText msgETx;
    
    class CBclazz implements ResultCallback {
        private String mStr;

        CBclazz(String str) {
            mStr = str;
        }

        /** before execute */
        public void onPreExecute() {
            Log.d(TAG, "onPreExecute " + mStr);
            Toast.makeText(mContext, "CBclazz onPreExecute " + mStr, Toast.LENGTH_SHORT).show();
        }

        /** on executing */
        public void doInBackground() {
            Log.d(TAG, mStr + "doInBackground ");
            Toast.makeText(mContext, "CBclazz doInBackground " + mStr, Toast.LENGTH_SHORT).show();
        }

        /** after executed */
        public void onSuccess(String result) {
            Log.d(TAG, mStr + " onSuccess " + result);
            Toast.makeText(mContext, mStr + " CBclazz onSuccess " + result, Toast.LENGTH_SHORT).show();
        }
        public void onFail(ResultMsg result) {
            Log.d(TAG, mStr + " onFail " + result.getResult());
            Toast.makeText(mContext, mStr + " CBclazz onFail " + result.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this.getApplicationContext();

		/** init RemoteManager and RemoteMessage */
		mRemoteManager = new RemoteManager(mContext);
		mRemoteMessage = new RemoteMessage(mContext);
		
		mailETx = (EditText) findViewById(R.id.mailETx);
		pwdETx = (EditText) findViewById(R.id.pwdETx);
		msgETx = (EditText) findViewById(R.id.msgETx);
		connectBtn = (Button) findViewById(R.id.connectBtn);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		sendBtn = (Button) findViewById(R.id.sendBtn);
		
		connectBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				/** connect to Kortide Remote Device */
				mRemoteManager.connect(new CBclazz("connect"));
			}
        });

		loginBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				String mail = mailETx.getText().toString();
				String pwd = pwdETx.getText().toString();
				/** login to Kortide Remote Device */
				mRemoteManager.login(mail, pwd, new CBclazz("login"));
			}
        });

		sendBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {				
				String msg = msgETx.getText().toString();
				/** send remote message to Kortide Remote Device */
				mRemoteMessage.sendRemoteMsg("com.kortide.lamobo_service", msg,
						new CBclazz("Msg"));
			}
        });
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
