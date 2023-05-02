
package word_occurrences;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


final class FileWalker {

    private final Occurrences occ;
    private final File rootDir;

    FileWalker(String rootDirPath, Occurrences occ) throws FileNotFoundException {
        this.occ = occ;
        this.rootDir = new File(rootDirPath);

        if (!this.rootDir.isDirectory()) {
            throw new FileNotFoundException(rootDirPath + " does not exist, " +
                    "or is not a directory.");
        }
    }

    void populateOccurrenceMap() {
        try {
            populateOccurrenceMap(rootDir);
        } catch (IOException ex) {
            // We should never really get to this point, but just in case...
            System.out.println(ex);
        }
    }
    private void add(BufferedReader bfrd, File file) throws IOException {
        int p;
        String temp = "";
        int line = 1, column = 1, str_col = 0;
        do{
            p = bfrd.read();
            str_col++;
            if (Syntax.isInWord((char)p)) {
                if(temp == "") column = str_col;
                temp = temp + (char) p;

            }
            else {
                if (temp.length() > 0) {
                    FilePosition pos = new FilePosition(line, column );
                    occ.put(temp.toLowerCase(), file.toString(), pos);
                    temp = "";
                }
            }
            if (Syntax.isNewLine((char)p)) {
                line++;
                str_col = 0;
            }
        } while(p != -1);
    }
    private void populateOccurrenceMap(File fileOrDir) throws IOException {

        // TODO: Implement me!!!
        if(fileOrDir.isFile()){
            add(new BufferedReader(new FileReader(fileOrDir)), fileOrDir);
        }
        else {
            for (int i = 0; i < fileOrDir.listFiles().length; i++) {
                populateOccurrenceMap(fileOrDir.listFiles()[i]);
            }
        }
    }
}


