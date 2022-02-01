package com.jger.groupe4.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jger.groupe4.exception.DivisionException;
import com.jger.groupe4.R;
import com.jger.groupe4.model.TypeOperationEnum;

public class CalculActivity extends AppCompatActivity {
    private Long premierElement =0L;
    private Long deuxiemeElement=0L;
    private TypeOperationEnum typeOperationEnum = null;
    private TextView textViewCalcul;
    private Long BORNE_HAUTE = 9999L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);
        textViewCalcul = findViewById(R.id.textview_calcul);
        Button bouton1 = findViewById(R.id.button_1);
        bouton1.setOnClickListener(view -> ajouterNombre(1));
        Button bouton2 = findViewById(R.id.button_2);
        bouton2.setOnClickListener(view -> ajouterNombre(2));
        Button bouton3 = findViewById(R.id.button_3);
        bouton3.setOnClickListener(view -> ajouterNombre(3));
        Button bouton4 = findViewById(R.id.button_4);
        bouton4.setOnClickListener(view -> ajouterNombre(4));
        Button bouton5 = findViewById(R.id.button_5);
        bouton5.setOnClickListener(view -> ajouterNombre(5));
        Button bouton6 = findViewById(R.id.button_6);
        bouton6.setOnClickListener(view -> ajouterNombre(6));
        Button bouton7 = findViewById(R.id.button_7);
        bouton7.setOnClickListener(view -> ajouterNombre(7));
        Button bouton8 = findViewById(R.id.button_8);
        bouton8.setOnClickListener(view -> ajouterNombre(8));
        Button bouton9 = findViewById(R.id.button_9);
        bouton9.setOnClickListener(view -> ajouterNombre(9));
        Button bouton0 = findViewById(R.id.button_0);
        bouton0.setOnClickListener(view -> ajouterNombre(0));
        Button boutonAdd = findViewById(R.id.button_add);
        boutonAdd.setOnClickListener(view -> ajouteTypeOperation(TypeOperationEnum.ADD));
        Button boutonSubstract = findViewById(R.id.button_substract);
        boutonSubstract.setOnClickListener(view -> ajouteTypeOperation(TypeOperationEnum.SUBSTRACT));
        Button boutonDivide = findViewById(R.id.button_divide);
        boutonDivide.setOnClickListener(view -> ajouteTypeOperation(TypeOperationEnum.DIVIDE));
        Button boutonMultiply = findViewById(R.id.button_multiply);
        boutonMultiply.setOnClickListener(view -> ajouteTypeOperation(TypeOperationEnum.MULTIPLY));



    }

    private void ajouteTypeOperation(TypeOperationEnum typeOperation) {
        switch (typeOperation){
            case ADD:
                this.typeOperationEnum = TypeOperationEnum.ADD;
                break;
            case DIVIDE:
                this.typeOperationEnum = TypeOperationEnum.DIVIDE;
                break;
            case MULTIPLY:
                this.typeOperationEnum=TypeOperationEnum.MULTIPLY;
                break;
            case SUBSTRACT:
                this.typeOperationEnum=TypeOperationEnum.SUBSTRACT;
                break;
        }
        majTextView();
    }

    private void ajouterNombre(Integer valeur){
        if(typeOperationEnum == null){
            if(10*premierElement+valeur > BORNE_HAUTE){
                Toast.makeText(this,getString(R.string.message_valeur_trop_grande),Toast.LENGTH_LONG).show();
            }else{
                premierElement = 10 * premierElement+valeur;
            }
        }else{
            if(10*deuxiemeElement+valeur > BORNE_HAUTE){
                Toast.makeText(this,getString(R.string.message_valeur_trop_grande),Toast.LENGTH_LONG).show();
            }else{
                deuxiemeElement = 10 * deuxiemeElement+valeur;
            }
        }
        majTextView();
    }

    private void majTextView() {
        String valeurAAfficher = "";
        if(typeOperationEnum == null){
            valeurAAfficher = premierElement.toString();
        }else{
            valeurAAfficher = premierElement + " "+ typeOperationEnum.getSymbol()+" "+ deuxiemeElement;
        }
        textViewCalcul.setText(valeurAAfficher);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);

        MenuItem itemVider = menu.findItem(R.id.bouton_vider);
        itemVider.setOnMenuItemClickListener(menuItem -> videTextViewCalcul());
        MenuItem itemCalculer = menu.findItem(R.id.toolbarCalculer);
        itemCalculer.setOnMenuItemClickListener(menuItem -> calcul());
        return super.onCreateOptionsMenu(menu);
    }

    private boolean calcul()  {
        try {
            Long resultat = 0L;
            switch (typeOperationEnum) {
                case MULTIPLY:
                    resultat = premierElement * deuxiemeElement;
                    break;
                case ADD:
                    resultat = premierElement + deuxiemeElement;
                    break;
                case DIVIDE:
                    if(deuxiemeElement == 0){
                        throw new DivisionException();
                    }else{
                        resultat = premierElement / deuxiemeElement;
                    }
                    break;
                case SUBSTRACT:
                    resultat = premierElement - deuxiemeElement;
                    break;
            }
            ouvreLastComputeActivity(resultat);
        }catch (DivisionException exception){
            Toast.makeText(this,getString(R.string.message_division_par_zero),Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void ouvreLastComputeActivity(Long resultat) {
        Intent intent = new Intent(this, LastComputeActivity.class);
        intent.putExtra("premierElement",premierElement);
        intent.putExtra("deuxiemeElement",deuxiemeElement);
        intent.putExtra("operationSymbol",typeOperationEnum.getSymbol());
        intent.putExtra("resultat",resultat);
        startActivity(intent);
    }

    private boolean videTextViewCalcul() {
        textViewCalcul.setText("");
        premierElement = 0L;
        deuxiemeElement = 0L;
        typeOperationEnum = null;
        return true;
    }
}