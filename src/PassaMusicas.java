import org.apache.commons.lang3.StringEscapeUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PassaMusicas {
    public static void main(String[] args){
        //Tem que ajeitar o arquivo em que é feito o download para poder reconhecer as colunas corretamente
        File file = new File("arquivo/ArquivoMusicas.ods");
        ArrayList<Musica> musicas = new ArrayList<>();
        final String COLUNA_CANTOR = "A";
        final String COLUNA_CODIGO = "B";
        final String COLUNA_NOME = "C";
        final String COLUNA_LETRA = "D";
        final String COLUNA_IDIOMA = "E";

        try {
            final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);
            final int numeroLinhas = sheet.getRowCount();

            for(int i = 2 ; i <= numeroLinhas ; i++){
                musicas.add(new Musica(
                        Integer.parseInt(sheet.getCellAt(COLUNA_CODIGO + i).getTextValue()),
                        sheet.getCellAt(COLUNA_NOME + i).getTextValue(),
                        sheet.getCellAt(COLUNA_LETRA + i).getTextValue(),
                        sheet.getCellAt(COLUNA_CANTOR + i).getTextValue(),
                        false
                ));
            }
        }catch (IOException e){
            System.out.println("Erro ao buscar planilha");
            e.printStackTrace();
        }


        Collections.sort(musicas, new Comparator<Musica>() {
            @Override
            public int compare(Musica musica1, Musica musica2) {
                return removerAcentos(musica1.getNome())
                        .compareToIgnoreCase(removerAcentos(musica2.getNome()));
            }
        });


        File jsonFile = new File("arquivo/kinkake.json");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));

            writer.write("{");
            writer.write("\"musica\" : [ null,");

            for(int i = 0 ; i < musicas.size() ; i++){
                writer.write("{");
                writer.write("\"codigo\" : " + musicas.get(i).getCodigo() + ",");
                writer.write("\"nome\" : \"" + StringEscapeUtils.escapeJson(musicas.get(i).getNome()) + "\",");
                writer.write("\"letra\" : \"" + StringEscapeUtils.escapeJson(musicas.get(i).getLetra()) + "\",");
                writer.write("\"cantor\" : \"" + musicas.get(i).getCantor() + "\",");
                writer.write("\"favorito\" : " + musicas.get(i).isFavorito());
                writer.write("}");

                if(i != musicas.size()-1)
                    writer.write(",");
            }

            writer.write("]");
            writer.write("}");

            writer.close();
        }catch (IOException e){
            System.out.println("Erro ao cirar writer");
            e.printStackTrace();
        }

    }

    private static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
