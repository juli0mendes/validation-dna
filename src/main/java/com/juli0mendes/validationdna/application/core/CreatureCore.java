package com.juli0mendes.validationdna.application.core;

import com.juli0mendes.validationdna.application.domain.Rule;
import com.juli0mendes.validationdna.application.ports.in.CreaturePortIn;
import com.juli0mendes.validationdna.application.ports.out.RuleDatabasePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CreatureCore implements CreaturePortIn {

    // TODO - adicionar logs
    // TODO - adicionar tratamento de erros

    private static final Logger logger = LoggerFactory.getLogger(CreatureCore.class);

    private final RuleDatabasePortOut ruleDatabasePortOut;

    public CreatureCore(RuleDatabasePortOut ruleDatabasePortOut) {
        this.ruleDatabasePortOut = ruleDatabasePortOut;
    }


    @Override
    public boolean isSimian(String[] dna) {

        String[][] myVetor = new String[dna.length][dna[0].length()];

        this.convertArray1dToArray2d(dna, myVetor);

        for (int line = 0; line < myVetor.length; line++) {
            for (int col = 0; col < myVetor.length; col++) {
                System.out.println(myVetor[line][col]);
            }
        }

        // get criterias exists
        Rule isSimian = this.ruleDatabasePortOut.getByName("is_simian");

        // convert array string to multidimensional array

//        String[][] myVetor = new String[dna.length][dna[0].length()];
//
//        for (int lines = 0; lines < dna.length; lines++) {
//            for (int cols = 0; cols < dna.length; cols++) {
//                myVetor[lines][cols] = String.valueOf(dna[lines].charAt(lines));
//            }
//        }

        return true;
    }

    private void convertArray1dToArray2d(String[] dna, String[][] myVetor) {
        for (int i = 0; i < dna.length; i ++) {
            for (int j = 0; j < dna[i].length(); j++) {
//                System.out.println(dna[i].charAt(j));
                myVetor[i][j] = String.valueOf(dna[i].charAt(j));
            }
        }
    }

//    public static void main(String[] args) {
//        String time = "flamengo";
//
//        for (int i = 0; i < time.length(); i++) {
//            System.out.println(time.charAt(i));
//        }
//    }
}
