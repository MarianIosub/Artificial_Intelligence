import ai.tema5.utils.ParserUtils;
import ai.tema5.utils.ReaderUtil;
import ai.tema5.utils.StringUtils;
import org.eclipse.rdf4j.model.Statement;

import java.util.List;
import java.util.Scanner;

import static ai.tema5.utils.ReaderUtil.readSentencesFromFile;
import static ai.tema5.utils.StringUtils.*;

public class Main {
    public static void main(String[] args) {
        //EX 2.
        List<Statement> ontologies = ReaderUtil.readOntologyFromFile(ONTOLOGY_PATH);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti conceptul cautat:");
        System.out.print("    >>");
        ParserUtils.findByEntityAndRelation(
                ontologies, "superTopicOf", scanner.nextLine());
        //EX. 3
        ParserUtils.findTwoNounsWithVerbBetween(ReaderUtil.readNLPDocumentFromFile(DOCUMENT_PATH));

        //EX. 4
        ParserUtils.findSentencesWithOntology(ontologies,readSentencesFromFile(OUTPUT_DOCUMENT));
    }
}
