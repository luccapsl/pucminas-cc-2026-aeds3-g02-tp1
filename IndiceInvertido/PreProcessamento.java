package IndiceInvertido;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class PreProcessamento {

    private static final Set<String> STOP_WORDS;

    static {
        Set<String> s = new HashSet<>();

        // Artigos
        Collections.addAll(s,
            "o", "a", "os", "as", "um", "uma", "uns", "umas"
        );

        // Preposicoes simples e combinadas
        Collections.addAll(s,
            "de", "do", "da", "dos", "das",
            "em", "no", "na", "nos", "nas",
            "por", "pelo", "pela", "pelos", "pelas",
            "para", "com", "sem", "sob", "sobre",
            "entre", "ate", "apos", "desde", "ante"
        );

        // Conjuncoes
        Collections.addAll(s,
            "e", "ou", "mas", "porem", "contudo", "todavia", "entretanto",
            "portanto", "logo", "pois", "porque", "que", "se", "como",
            "quando", "embora", "enquanto", "nem", "seja"
        );

        // Pronomes frequentes
        Collections.addAll(s,
            "eu", "tu", "ele", "ela", "nos", "vos", "eles", "elas",
            "me", "te", "se", "nos", "vos", "lhe", "lhes",
            "isso", "este", "esta", "esse", "essa", "aquele", "aquela",
            "meu", "minha", "seu", "sua"
        );

        STOP_WORDS = Collections.unmodifiableSet(s);
    }

    public static boolean isStrEmpty(String input, boolean generateLog) {
        if (input != null && (! input.trim().isEmpty()) ) {
            return false;
        } else {
            if (generateLog) {
                System.out.println("[ERRO] - String passada eh vazia ou nula");
            }
            return true;
        }
    }

    private String normalizeString(String input) {
        String output = "";

        if (! isStrEmpty(input, false) ) {
            output = input;

            //Decompoe a string
            output = Normalizer.normalize(output, Normalizer.Form.NFD);
            //Define regex com base no Unicode
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            //Altera input conforme os padroes do regex
            output = pattern.matcher(output).replaceAll("");
        }

        return output;
    }

    private List<String> tokenizateString(String input) {
        List<String> output = null;
        String[] processedStrings = null;

        String inputLowerCase = "";

        if (! isStrEmpty(input, false) ) {
            output = new ArrayList<>();

            inputLowerCase = input.toLowerCase();
            processedStrings = inputLowerCase.split("[^a-z]+");

            for (String s : processedStrings) {
                if (s.length() >= 2 && ! STOP_WORDS.contains(s) ) {
                    output.add(s);
                }
            }


        }
        return output;
    }

    public List<String> preProccessString(String input) {
        List<String> output = new ArrayList<>();

        if (! isStrEmpty(input, true) ) {
            String normalizedInput = this.normalizeString(input);
            output = this.tokenizateString(normalizedInput);
        } else {
            System.out.println("[ERRO] - String de entrada para preprocessamento eh vazia ou nula");
        }

        return output;
    }
    


}
