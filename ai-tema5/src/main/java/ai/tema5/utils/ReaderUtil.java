package ai.tema5.utils;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.SneakyThrows;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReaderUtil {
    @SneakyThrows
    public static List<Statement> readOntologyFromFile(String path) {
        RDFFormat format = RDFFormat.TURTLE;
        List<Statement> statementList = new ArrayList<>();
        File documentUrl = new File(path);
        InputStream inputStream = new FileInputStream(documentUrl);
        RDFParser rdfParser = Rio.createParser(format);
        Model model = new LinkedHashModel();
        rdfParser.setRDFHandler(new StatementCollector(model));
        String baseURI = documentUrl.toString();
        GraphQueryResult res = QueryResults.parseGraphBackground(inputStream, baseURI, format);
        while (res.hasNext()) {
            Statement st = res.next();
            statementList.add(st);
        }
        inputStream.close();
        return statementList;
    }

    @SneakyThrows
    public static CoreDocument readNLPDocumentFromFile(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line.length() != 0 ? line + "\n" : "");
        }
        String text = stringBuilder.toString();
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse");
        props.setProperty("coref.algorithm", "neural");
        props.setProperty("ssplit.newlineIsSentenceBreak", "always");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);
        reader.close();
        return document;
    }

    @SneakyThrows
    public static List<String> readSentencesFromFile(String path) {
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        List<String> sentences = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            sentences.add(line);
        }
        bufferedReader.close();
        return sentences;
    }

}
