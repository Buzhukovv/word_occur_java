
package word_occurrences;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Occurrences {

    // Maps words -> filename -> sets of their Positions in the file.
    private final TreeMap<String, TreeMap<String, TreeSet<FilePosition>>> occMap;

    public Occurrences(String rootDirPath) throws FileNotFoundException {
        occMap = new TreeMap<>();
        FileWalker walker = new FileWalker(rootDirPath, this);
        walker.populateOccurrenceMap();
    }

    /*
        Called by FileWalker to add an occurrence to the occMap
     */
    void put(String word, String filePath, FilePosition pos) {

        // TODO: Implement me!!!
        if(!occMap.containsKey(word))
            occMap.put(word, new TreeMap<>());

        if(!occMap.get(word).containsKey(filePath)){
            occMap.get(word).put(filePath, new TreeSet<>());
        }

        occMap.get(word).get(filePath).add(pos);
    }


    /**
     * @return the number of distinct words found in the files
     */
    public int distinctWords() {

        return occMap.size();
    }

    /**
     * @return the number of total word occurrences across all files
     */
    public int totalOccurrences() {

        // TODO: Implement me!!!
        int cnt = 0;
        for (String word : occMap.keySet()){
            cnt += totalOccurrencesOfWord(word);
        }
        return cnt;
    }

    /**
     * Finds the total number of occurrences of a given word across
     * all files.  If the word is not found among the occurrences,
     * simply return 0.
     *
     * @param word whose occurrences we are counting
     * @return the number of occurrences
     */
    public int totalOccurrencesOfWord(String word) {

        // TODO: Implement me!!!
        if(occMap.containsKey(word)){
            int cnt = 0;
            for(String path: occMap.get(word).keySet()){
                cnt += totalOccurrencesOfWordInFile(word, path);
            }
            return cnt;
        }
        return 0;
    }

    /**
     * Finds the total number of occurrences of a given word in the given file.
     * If the file is not found in Occurrences, or if the word does not occur
     * in the file, simply return 0.
     *
     * @param word whose occurrences we are counting
     * @param filepath of the file
     * @return the number of occurrences
     */
    public int totalOccurrencesOfWordInFile(String word, String filepath) {

        // TODO: Implement me!!!
        if(occMap.containsKey(word) && occMap.get(word).containsKey(filepath))
            return occMap.get(word).get(filepath).size();
        return 0;
    }

    public String toString() {

        // TODO: Implement me!!!
        StringBuilder sb = new StringBuilder();
        for (String word : occMap.keySet())
        {
            sb.append("\""+ word + "\" has " + totalOccurrencesOfWord(word) + " occurrence(s):\n");
            for (Map.Entry<String, TreeSet<FilePosition>> filepath : occMap.get(word).entrySet())
            {
                for(FilePosition pos : filepath.getValue()){
                    sb.append("   ( file: \"" + filepath.getKey() + "\"; " + pos + " )\n");
                }
            }
        }

        return sb.toString();
    }
}


