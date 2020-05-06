package com.example.user.bs_calc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    EditText etNumber1;
    EditText etNumber2;
    EditText etNumber3;
    EditText etNumber4;

    Button btnFind;


    TextView tvCalcEqu;

//    String srtEquipment[] = {"BSCU1", "BSCU2", "Undefined Equipment", "Undefined Equipment", "Undefined Equipment", "Undefined Equipment", "Undefined Equipment", "Undefined Equipment", "Undefined Equipment", "Undefined Equipment", "CHU1", "CHU2", "CHU3", "CHU4", "CHU5", "CHU6", "CHU7", "CHU8"};

    Button btCalcIP;

    EditText etFirstIPNum;
    EditText etSecondIPNum;
    EditText etBSNum;

    Spinner spinnerEqu;
    Spinner spinnerBS_IPN_selector;

//    TextView tvCalcIPAddr;

    int cats[] = {225, 1, 33, 65, 97, 129, 161, 193};
    int equipment[] = {0, 1, 10, 11, 12, 13, 14, 15, 16, 17, 0};

    String[] choose;
    String selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        etFirstIPNum = (EditText) findViewById(R.id.etFirstIPNum);
        etSecondIPNum = (EditText) findViewById(R.id.etSecondIPNum);
        etBSNum = (EditText) findViewById(R.id.etBSNum);

        spinnerEqu = (Spinner) findViewById(R.id.spinnerEqu);
        spinnerBS_IPN_selector = (Spinner) findViewById(R.id.spinnerBS_IPN_selector);

        btCalcIP = (Button) findViewById(R.id.btCalcIP);
        btCalcIP.setOnClickListener(this);

//        tvCalcIPAddr = (TextView) findViewById(R.id.tvCalcIPAddr);

        etNumber1 = (EditText) findViewById(R.id.etNumber1);
        etNumber3 = (EditText) findViewById(R.id.etNumber3);
        etNumber2 = (EditText) findViewById(R.id.etNumber2);
        etNumber4 = (EditText) findViewById(R.id.etNumber4);

        btnFind = (Button) findViewById(R.id.btnFind);
        btnFind.setOnClickListener(this);


        tvCalcEqu = (TextView) findViewById(R.id.tvCalcEqu);

        spinnerBS_IPN_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                choose = getResources().getStringArray(R.array.ipn_bs);
                selectedItem = choose[selectedItemPosition];
                ArrayAdapter<?> adapter = null;
                switch (selectedItem) {
                    case "BS Number":
                        // Настраиваем адаптер
                        adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.equList, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "IPN Number":
                        // Настраиваем адаптер
                        adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.ipnList, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                }
                // Вызываем адаптер
                spinnerEqu.setAdapter(adapter);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick start");
        switch (view.getId()) {
            case (R.id.btCalcIP):
                findIP ();
                break;
            case (R.id.btnFind):
                findEqu ();
                break;
        }
    }

    void findIP () {
        String resultString;
        int bs = Integer.parseInt(etBSNum.getText().toString());
        int num3 = 0;
        int num4 = 0;
        switch (selectedItem) {
            case "BS Number":
                if (bs > 1233) {
                    Toast.makeText(getApplicationContext(), "1 <= BS <= 1233", Toast.LENGTH_SHORT).show();
                    etNumber3.setText("");
                    etNumber4.setText("");
                    return;
                }
                num3 = 101 + (bs - 1) / 8;
                int equ = equipment[spinnerEqu.getSelectedItemPosition()];
                num4 = equ + cats[(bs % 8)];
                break;
            case "IPN Number":
                if (bs > 22) {
                    Toast.makeText(getApplicationContext(), "1 <= IPN <= 22", Toast.LENGTH_SHORT).show();
                    etNumber3.setText("");
                    etNumber4.setText("");
                    return;
                }
                num3 = bs;
                String selectedIPN = spinnerEqu.getSelectedItem().toString();
                switch (selectedIPN) {
                    case "SCN":
                        num4 = 1;
                        break;
                    case "DVRS":
                        num4 = 31;
                        break;
                    case "DWS":
                        num4 = 32;
                        break;
                    case "NDB":
                        num4 = 62;
                        break;
                    case "NMS-Client1":
                        num4 = 101;
                        break;
                    case "NMS-Client2":
                        num4 = 102;
                        break;
//                    case "NMS-Client3":
//                        num4 = 225;
//                        break;
                    case "DVRS-Client":
                        num4 = 111;
                        break;
                    case "DWS-Client":
                        num4 = 112;
                        break;
                    case "IP Switch":
                        num4 = 253;
                        break;
                }
                break;
        }
        etNumber1.setText(etFirstIPNum.getText().toString());
        etNumber2.setText(etSecondIPNum.getText().toString());
        etNumber3.setText(num3 + "");
        etNumber4.setText(num4 + "");


    }

    void findEqu () {
        Log.d(TAG, "findEqu start");
        int ipNum3 = Integer.parseInt(etNumber3.getText().toString());
        Log.d(TAG, "int Number3 = " + ipNum3);
        int ipNum4 = Integer.parseInt(etNumber4.getText().toString());
        Log.d(TAG, "int Number4 = " + ipNum4);
        int equNum;
        if (spinnerBS_IPN_selector.getSelectedItem().toString().equals("IPN Number")) {
            StringBuilder ipn = new StringBuilder("");
            if (ipNum3%2 == 0) {
                ipn.append("IPN-2 ");
            } else {
                ipn.append("IPN-1 ");
            }
            switch (ipNum4) {
                case 1:
                    ipn.append("SCN");
                    break;
                case 31:
                    ipn.append("DVRS");
                    break;
                case 32:
                    ipn.append("DWS");
                    break;
                case 62:
                    ipn.append("NDB");
                    break;
                case 101:
                    ipn.append("NMS-Client1");
                    break;
                case 102:
                    ipn.append("NMS-Client2");
                    break;
                case 225:
                    ipn.append("NMS-Client3");
                    break;
                case 111:
                    ipn.append("DVRS-Client");
                    break;
                case 112:
                    ipn.append("DWS-Client");
                    break;
                case 253:
                    ipn.append("IP Switch");
                    break;
            }
            tvCalcEqu.setText(ipn);

            return;
        } else {
            int bs = (ipNum3 - 101) * 8 +1;

            if (ipNum4 > 224) {
                equNum = ipNum4 - 225;
                bs += 7;
            } else if (ipNum4 > 192) {
                equNum = ipNum4 - 193;
                bs += 6;
            }else if (ipNum4 > 160) {
                equNum = ipNum4 - 161;
                bs += 5;
            }else if (ipNum4 > 128) {
                equNum = ipNum4 - 129;
                bs += 4;
            }else if (ipNum4 > 96) {
                equNum = ipNum4 - 97;
                bs += 3;
            }else if (ipNum4 > 64) {
                equNum = ipNum4 - 65;
                bs += 2;
            }else if (ipNum4 > 32) {
                equNum = ipNum4 - 33;
                bs += 1;
            }else  {
                equNum = ipNum4 - 1;
            }
            if (equNum > 17 || equNum < 0) {
                equNum = 2;
            }
            Log.d(TAG, "equNum = " + equNum);
            Log.d(TAG, "bs = " + bs);
            String[] arrEquipment = getResources().getStringArray(R.array.equList);
            String strEquipment = "Undefined Equipment";
            switch (equNum) {
                case 0:
                    strEquipment = arrEquipment[0];
                    break;
                case 1:
                    strEquipment = arrEquipment[1];
                    break;
                case 10:
                    strEquipment = arrEquipment[2];
                    break;
                case 11:
                    strEquipment = arrEquipment[3];
                    break;
                case 12:
                    strEquipment = arrEquipment[4];
                    break;
                case 13:
                    strEquipment = arrEquipment[5];
                    break;
                case 14:
                    strEquipment = arrEquipment[6];
                    break;
                case 15:
                    strEquipment = arrEquipment[7];
                    break;
                case 16:
                    strEquipment = arrEquipment[8];
                    break;
                case 17:
                    strEquipment = arrEquipment[9];
                    break;

            }
            tvCalcEqu.setText("BS" + bs + " " + strEquipment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
