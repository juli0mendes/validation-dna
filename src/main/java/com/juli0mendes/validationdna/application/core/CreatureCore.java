package com.juli0mendes.validationdna.application.core;

import com.juli0mendes.validationdna.application.core.exceptions.BusinessException;
import com.juli0mendes.validationdna.application.domain.Rule;
import com.juli0mendes.validationdna.application.ports.in.CreaturePortIn;
import com.juli0mendes.validationdna.application.ports.out.RuleDatabasePortOut;
import com.juli0mendes.validationdna.application.ports.out.ValidationDatabasePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.juli0mendes.validationdna.application.domain.CriteriaStatus.ACTIVE;
import static com.juli0mendes.validationdna.application.domain.RuleStatus.INACTIVE;

@Service
public class CreatureCore implements CreaturePortIn {

    private static final Logger log = LoggerFactory.getLogger(CreatureCore.class);

    private static final String RULE_NAME = "is_simian";

    private final RuleDatabasePortOut ruleDatabasePortOut;

    private final ValidationDatabasePortOut validationDatabasePortOut;

    public CreatureCore(RuleDatabasePortOut ruleDatabasePortOut,
                        ValidationDatabasePortOut validationDatabasePortOut) {
        this.ruleDatabasePortOut = ruleDatabasePortOut;
        this.validationDatabasePortOut = validationDatabasePortOut;
    }

    @Override
    public boolean isSimian(String[] dna) {

        log.info("is-simian; start; system; dna=\"{}\";", dna);

        String[][] myVetor = new String[dna.length][dna[0].length()];

        this.convertArray1dToArray2d(dna, myVetor);

        Rule ruleIsSimian = this.ruleDatabasePortOut.getByName(RULE_NAME);

        if (ruleIsSimian == null || ruleIsSimian.getStatus() == INACTIVE)
            throw new BusinessException("there is no active ruleIsSimian");

        Set<String> words = new HashSet<>();
        ruleIsSimian.getCriterias().stream().forEach(criteria -> {
            if (criteria.getStatus() == ACTIVE)
                words.add(criteria.getCharactersSequence());
        });

        boolean isSimian = this.findWords(myVetor, words).size() > 0;

        this.validationDatabasePortOut.uppsert(dna, isSimian);

        log.info("is-simian; end; system; isSimian=\"{}\";", isSimian);

        return isSimian;
    }

    private void convertArray1dToArray2d(String[] dna, String[][] myVetor) {
        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna[i].length(); j++) {
                myVetor[i][j] = String.valueOf(dna[i].charAt(j));
            }
        }
    }

    public Set<String> findWords(String[][] puzzle, Set<String> words) {

        log.info("find-words; start; system; puzzle=\"{}\"; words=\"{}\";", puzzle, words);

        Set<String> foundWords = new HashSet<>();

        int minimumWordLength = this.findMinimumWordLength(words);

        Set<String> possibleWords = this.findPossibleWords(puzzle, minimumWordLength);

        for (String word : words) {
            for (String possibleWord : possibleWords) {
                if (possibleWord.contains(word) || possibleWord.contains(new StringBuffer(word).reverse())) {
                    foundWords.add(word);
                    break;
                }
            }
        }

        log.info("find-words; end; system; foundWords=\"{}\";", foundWords);

        return foundWords;
    }

    private int findMinimumWordLength(Set<String> words) {
        int minimumLength = Integer.MAX_VALUE;
        for (String word : words) {
            if (word.length() < minimumLength)
                minimumLength = word.length();
        }
        return minimumLength;
    }

    private Set<String> findPossibleWords(String[][] puzzle, int minimumWordLength) {
        Set<String> possibleWords = new LinkedHashSet<>();
        int dimension = puzzle.length; //Assuming puzzle is square
        if (dimension >= minimumWordLength) {
            /* Every row in the puzzle is added as a possible word holder */
            for (int i = 0; i < dimension; i++) {
                if (puzzle[i].length >= minimumWordLength) {
                    possibleWords.add(new String(String.valueOf(puzzle[i])));
                }
            }
            /* Every column in the puzzle is added as a possible word holder */
            for (int i = 0; i < dimension; i++) {
                StringBuffer temp = new StringBuffer();
                for (int j = 0; j < dimension; j++) {
                    temp = temp.append(puzzle[j][i]);
                }
                possibleWords.add(new String(temp));
            }
            /* Adding principle diagonal word holders */
            StringBuffer temp1 = new StringBuffer();
            StringBuffer temp2 = new StringBuffer();
            for (int i = 0; i < dimension; i++) {
                temp1 = temp1.append(puzzle[i][i]);
                temp2 = temp2.append(puzzle[i][dimension - i - 1]);
            }
            possibleWords.add(new String(temp1));
            possibleWords.add(new String(temp2));
            /* Adding non-principle diagonal word holders */
            for (int i = 1; i < dimension - minimumWordLength; i++) {
                temp1 = new StringBuffer();
                temp2 = new StringBuffer();
                StringBuffer temp3 = new StringBuffer();
                StringBuffer temp4 = new StringBuffer();
                for (int j = i, k = 0; j < dimension && k < dimension; j++, k++) {
                    temp1 = temp1.append(puzzle[j][k]);
                    temp2 = temp2.append(puzzle[k][j]);
                    temp3 = temp3.append(puzzle[dimension - j - 1][k]);
                    temp4 = temp4.append(puzzle[dimension - k - 1][j]);
                }
                possibleWords.add(new String(temp1));
                possibleWords.add(new String(temp2));
                possibleWords.add(new String(temp3));
                possibleWords.add(new String(temp4));
            }
        }
        return possibleWords;
    }

}
